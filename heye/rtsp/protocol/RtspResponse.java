/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *  
 *    http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License. 
 *  
 */
package heye.rtsp.protocol;

import java.nio.charset.CharacterCodingException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.util.CharsetUtil;


/**
 * @author Jiang Ying
 *
 */
public class RtspResponse implements RtspMessage{
	
	private final byte[] CRLF = {0x0D,0x0A};
	private final byte SP = 0x20;
	
	private Hashtable<String, String> headers = new Hashtable<String, String>();
	private String majorVersion;
	private String minorVersion;
	private String status;
	private String describe;
	private String body = "";

	public RtspResponse() {
		this.SetMajorVersion("1");
		this.SetMinorVersion("0");
	}
	
	public RtspResponse(ByteBuffer buf) throws RtspException {
		
		/*
		 *  String line = GetLine(buf);
		 */
		
		/* begining patch for empty line
		 * 2007.09.29 Jiang Ying @ 亿成
		 * 解决在RTSP Message第一行只有CRLF的空行。
		 */
		String line = "";
		while ((line.trim().equals(""))&&(buf.position()<buf.limit())) {
			line = GetLine(buf);
		}
		// end patch
		
		
		String token[] = line.split(" ", 3);
		if (token.length!=3) {
			throw new RtspException("RTSP Message error");
		}
		if ((token[0].length()!=8)&&(! token[0].toUpperCase().startsWith("RTSP"))) {
			throw new RtspException("RTSP Message error");
		}
		this.SetMajorVersion(token[0].substring(5,6));
		this.SetMinorVersion(token[0].substring(7,8));
		this.SetStatus(token[1]);
		this.SetDescribe(token[2]);

		while ((buf.position() < buf.limit())&&(!line.equals(""))) {
			line = GetLine(buf);
			if (line.length()>0) {
				token = line.split(":",2);
				if (token.length == 2) {
					this.SetHeader(token[0].trim(), token[1].trim());
				} else if (token.length == 1) {
					this.SetHeader(token[0].trim(), "");
				} else {
					throw new RtspException("RTSP Message error");
				}
			}

		}
		if (buf.position()<buf.limit()) {
			try {
				this.SetBody(buf.getString(CharsetUtil.getDefaultCharset().newDecoder()));
			} catch (CharacterCodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	public void SetStatus(String code) {
		this.status = code;
	}
	
	public void SetDescribe(String describe) {
		this.describe = describe;
	}
	
	public void SetMajorVersion(String version) {
		this.majorVersion = version;
	}
	
	public void SetMinorVersion(String version) {
		this.minorVersion = version;
	}
	
	
	public void SetHeader(String name, String value) {
		headers.put(name, value);
	}
	
	public void SetBody(String body) {
		this.body = body;
	}
	
	public String GetSession() {
		return (String) headers.get("Session");
	}
	
	public String GetHeader(String name) {
		return (String)headers.get(name);
	}
	
	public String GetBody() {
		return (String)body;
	}
	
	public String GetStatus() {
		return status;
	}
	
	public String GetDescribe() {
		return describe;
	}
	
	private String MakeVersion() {
		return ("RTSP/"+majorVersion+"."+minorVersion);
	}
	
	private String GetLine(ByteBuffer buf) {
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
	
	public ByteBuffer toByteBuffer() {
		ByteBuffer buf = ByteBuffer.allocate(512);
		buf.put(MakeVersion().getBytes());
		buf.put(SP);
		buf.put(status.getBytes());
		buf.put(SP);
		buf.put(describe.getBytes());
		buf.put(CRLF);
		
		if (this.body.length()>0) {
			headers.put("Content-Length", String.valueOf(body.length()));
		} else if (this.body.length()==0){
			headers.put("Content-Length", "0");
		}
		
		Iterator it = headers.entrySet().iterator();
		
		while (it.hasNext()) {
			Map.Entry obj = (Map.Entry)it.next();
			String name = (String) obj.getKey();
			String value = (String) obj.getValue();
			if ((name != null) && (value!=null)) {
				buf.put(name.getBytes());
				buf.put(":".getBytes());
				buf.put(value.getBytes());
				buf.put(CRLF);
			}
		}
		

		if (this.body.length() > 0) {
			buf.put(CRLF);
			buf.put(body.getBytes());
			buf.put(CRLF);
			buf.put(CRLF);
		} else {
			buf.put(CRLF);
		}
		
		buf.flip();
		return buf;
	}
	

	/**
	 * @param args
	 * @throws RtspException 
	 */
	public static void main(String[] args) throws RtspException {
		// TODO Auto-generated method stub
		
		// for test
		/*
		RTSPStatus rtsp = new RTSPStatus();
		rtsp.SetBody("Abc");
		rtsp.SetContext("rtsp://123");
		rtsp.SetStatus("200");
		rtsp.SetHeader("Abc", "");
		rtsp.SetHeader("aaa", "bbb");
		ByteBuffer bb = rtsp.toByteBuffer();
		RTSPStatus rtsp1 = new RTSPStatus(bb);
		ByteBuffer bb1 = rtsp1.toByteBuffer();
		for (int i=0; i<bb1.limit();i++) {
			System.out.print((char)bb1.get());
		}
		*/
	}


}
