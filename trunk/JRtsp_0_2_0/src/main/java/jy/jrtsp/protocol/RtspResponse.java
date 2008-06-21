package jy.jrtsp.protocol;


import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import org.apache.mina.common.ByteBuffer;

public class RtspResponse implements RtspMessage {
	
	private final byte[] CRLF = {0x0D,0x0A};
	private final byte SP = 0x20;
	
	private Hashtable<String, String> headers = new Hashtable<String, String>();
	private String version;
	private String status;
	private String description;
	private byte[] body = new byte[] {};

	public RtspResponse() {
		this.version = RtspMessage.VERSION_RTSP_1_0;
	}
	
	public RtspResponse(ByteBuffer buf) throws RtspMessageException {
		
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
		
		this.setVersion(token[0]);
		this.setStatus(token[1]);
		this.setDescription(token[2]);

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
	
	public RtspResponse(String version, String status, String describe) {
		this.version = version;
		this.status = status;
		this.description = describe;
	}
	
	public void setDescription(String desc) {
		this.description = desc;
	}
	
	public void setStatus(String status) {
		this.status = status;
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
	
	public String getDescription() {
		return description;
	}
	
	public String getStatus() {
		return status;
	}
	
	public int getStatusCode() {
		return Integer.parseInt(status);
	}
	
	public String getHeader(String name) {
		return headers.get(name);
	}
	
	public String[] getAllHeaderNames() {
		String[] names = new String[headers.size()];
		int i=0;
		for (String key : headers.keySet()) {
			names[i++] = key;
		}
		return names;
	}

	public String getCSeq() {
		return headers.get("CSeq");
	}
	
	public ByteBuffer toByteBuffer() {
		ByteBuffer buf = ByteBuffer.allocate(512).setAutoExpand(true);
		buf.put(version.getBytes());
		buf.put(SP);
		buf.put(status.getBytes());
		buf.put(SP);
		buf.put(description.getBytes());
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
				buf.put(": ".getBytes());
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

		return line.toString();
	}
	
}
