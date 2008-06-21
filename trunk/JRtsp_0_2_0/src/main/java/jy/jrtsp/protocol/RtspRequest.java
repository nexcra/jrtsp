package jy.jrtsp.protocol;


import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import org.apache.mina.common.ByteBuffer;

public class RtspRequest implements RtspMessage {

	
	private final byte[] CRLF = {0x0D,0x0A};
	private final byte SP = 0x20;
	
	private Hashtable<String, String> headers = new Hashtable<String, String>();
	private String method;
	private String context;
	private String version;
	private byte[] body = new byte[] {};

	public RtspRequest() {
		this.version = RtspMessage.VERSION_RTSP_1_0;
	}
	
	public RtspRequest(ByteBuffer buf) throws RtspMessageException {
		
		/* begining patch for empty line
		 * 2007.09.28 Jiang Ying
		 * 解决在RTSP Message第一行只有CRLF的空行。
		 */
		String line = "";
		while ( (line.trim().equals("")) && buf.hasRemaining()) {
			line = getLine(buf);
		}
		if (line.trim().length() ==  0) {
			throw new RtspMessageException("Empty RTSP Message");
		}
		// end patch

		String token[] = line.split(" ", 3);
		if (token.length!=3) {
			throw new RtspMessageException("RTSP Message error");
		}
		if ((token[2].length()!=8)&&(! token[2].toUpperCase().startsWith("RTSP"))) {
			throw new RtspMessageException("RTSP Message error");
		}
		this.setMethod(token[0]);
		this.setContext(token[1]);
		this.version = token[2];

		while ( buf.hasRemaining() && (!(line.trim().length() == 0)) ) {
			line = getLine(buf);
			if (line.length()>0) {
				token = line.split(":");
				if (token.length == 2) {
					this.setHeader(token[0].trim(), token[1].trim());
				} else if (token.length == 1) {
					this.setHeader(token[0].trim(), "");
				} else {
					throw new RtspMessageException("RTSP Message error");
				}
			}
		}
		
		int len = Integer.parseInt(this.getHeader("Content-Length")); 
		if ( buf.hasRemaining() && (len > 0) ) {
			body = new byte[len];
			buf.get(body);
		}
	}
	
	public RtspRequest(String method, String context, String version) {
		this.method = method;
		this.context = context;
		this.version = version;
	}
	
	public void setMethod(String method) {
		this.method = method;
	}
	
	public void setContext(String context) {
		this.context = context;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	
	public void setHeader(String name, String value) {
		headers.put(name, value);
	}
	
	public void setBody(byte[] body) {
		this.body = body;
	}
	
	public String getMethod() {
		return method;
	}
	
	public String getContext() {
		return context;
	}
	
	public String getHeader(String name) {
		return (String)headers.get(name);
	}

	public String getCSeq() {
		return headers.get("CSeq");
	}

	public ByteBuffer toByteBuffer() {
		ByteBuffer buf = ByteBuffer.allocate(512).setAutoExpand(true);
		buf.put(method.getBytes());
		buf.put(SP);
		buf.put(context.getBytes());
		buf.put(SP);
		buf.put(version.getBytes());
		buf.put(CRLF);
		
		if (body.length > 0) {
			headers.put("Content-Length", String.valueOf(body.length));
		} else {
			headers.put("Content-Length", "0");
		}
		
		Iterator it = headers.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry obj = (Map.Entry)it.next();
			String name = (String) obj.getKey();
			String value = (String) obj.getValue();
			if ((name != null) && (value!=null)) {
				buf.put(name.getBytes());
				
				// begning patch for OVS6
				// 2007.09.29 Jiang Ying @ 亿成
				// 发送给OVS的RTSP Message中Session参数必须使用格式 "Session: xxxxx"
				// 在":"后必须有个空格，否则OVS会返回"454 Session Not Found"错误。
				buf.put(": ".getBytes());
				// end patch
				
				buf.put(value.getBytes());
				buf.put(CRLF);
			}
		}
		
		buf.put(CRLF);		
		if (body.length > 0) {
			buf.put(body);
			buf.put(CRLF);
			buf.put(CRLF);
		}
		
		buf.flip();
		return buf;
	}
	
	private String getLine(ByteBuffer buf) {
		/*
		 * 2007.09.30 Jiang Ying @ 大连机场
		 * 使用StringBuffer代替String
		 */
		StringBuffer line = new StringBuffer(512);
		while (buf.position()<buf.limit()) {
			char ch = (char)buf.get();
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
