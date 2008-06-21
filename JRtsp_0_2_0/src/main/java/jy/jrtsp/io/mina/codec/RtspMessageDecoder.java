package jy.jrtsp.io.mina.codec;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import jy.jrtsp.protocol.RtspMessage;
import jy.jrtsp.protocol.RtspRequest;
import jy.jrtsp.protocol.RtspResponse;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class RtspMessageDecoder extends CumulativeProtocolDecoder {
	
	private final CharsetDecoder decoder = Charset.forName("iso8859-1").newDecoder();
	private final String CONTENT_LENGTH = "Content-Length:";
	
	public RtspMessageDecoder() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean doDecode(IoSession session, ByteBuffer in,
			ProtocolDecoderOutput out) throws Exception {
		// TODO Auto-generated method stub

		int start = in.position();
		
		String line = in.getString(decoder);
		
		in.position(start);
		
		int tokenIdx = line.indexOf("\r\n\r\n") + start;

		if (tokenIdx >= start) {
			tokenIdx += 4;
			int lenIdx = line.indexOf(CONTENT_LENGTH);

			int len = 0;
			
			// 计算Content-Length长度
			if ((lenIdx > 0) && (lenIdx < tokenIdx)) {
				lenIdx += this.CONTENT_LENGTH.length();
				String contentLength = "";

				for (int i = lenIdx; i<line.length(); i++) {
					if (line.charAt(i) == '\r') break;
					contentLength += line.charAt(i);
				}
							
				if (contentLength.length() > 0) {
					len = Integer.parseInt(contentLength.trim());
				}
			}
			
			// 如果有足够的数据，则进行decode
			if ((in.limit() - tokenIdx) >= len) {
				RtspMessage msg  = buildRtspMessage(in, tokenIdx, len);
				if  (msg == null) {
					return true;
				} else {
					out.write(msg);
					if (in.hasRemaining()) {
						return true;
					}
				}
			}
		}

		return false;
	}

	private RtspMessage buildRtspMessage(ByteBuffer in, int tokenIdx, int contentLength) {
		// TODO Auto-generated method stub
		
		boolean firstLine = true;

		RtspMessage msg = null;

		while (in.position()<tokenIdx) {

			String line = getLine(in, tokenIdx).trim();
			if (line.length() == 0) continue;
			if (firstLine) {
				String[] token = line.split(" ", 3);
				if (token.length != 3) {
					return null;
				} else {
					if (token[0].startsWith("RTSP")) {
						msg = new RtspResponse(token[0], token[1], token[2]);
					} else {
						msg = new RtspRequest(token[0], token[1], token[2]);
					}
					firstLine = false;
				}
			} else {
				String[] token = line.split(":", 2);
				if (token.length == 1) {
					msg.setHeader(token[0], "");
				} else {
					msg.setHeader(token[0], token[1].trim());
				}
			}
		}
		
		if ((contentLength > 0) && (msg != null)) {
			byte[] buf = new byte[contentLength];
			in.get(buf);
			msg.setBody(buf);
		}

		return msg;
	}
	
	private String getLine(ByteBuffer in, int pos) {

		StringBuffer line = new StringBuffer(256);
		while (in.position()<pos) {
			char ch = (char)in.get();
			if (ch == '\r') {
				continue;
			}
			if (ch == '\n') {
				break;
			}
			line.append(ch);
		}
		//System.out.println(line);
		return line.toString();
	}

}
