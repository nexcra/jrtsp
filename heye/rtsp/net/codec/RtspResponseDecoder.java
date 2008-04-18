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
package heye.rtsp.net.codec;

import heye.rtsp.protocol.RtspException;
import heye.rtsp.protocol.RtspMessage;
//import heye.rtsp.net.protocol.rtsp.impl.RTSPResponseFactory;

import java.nio.charset.CharsetDecoder;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;
import org.apache.mina.util.CharsetUtil;

/**
 * @author Jiang Ying
 *
 */
public class RtspResponseDecoder implements MessageDecoder {

	private final String CONTENT_LENGTH = "Content-Length:";
	private CharsetDecoder decoder = CharsetUtil.getDefaultCharset().newDecoder();
	
	public RtspResponseDecoder() {	
		
	}
	
	/* (non-Javadoc)
	 * @see org.apache.mina.filter.codec.demux.MessageDecoder#decodable(org.apache.mina.common.IoSession, org.apache.mina.common.ByteBuffer)
	 */
	public MessageDecoderResult decodable(IoSession session, ByteBuffer in) {
		// TODO Auto-generated method stub
		
		
		try {
			String line = in.getString(decoder);
			
			in.position(0);

			int tokenIdx = line.indexOf("\r\n\r\n");
			
			if (tokenIdx == -1) return MessageDecoderResult.NEED_DATA;
			int idx = line.indexOf(this.CONTENT_LENGTH);
			if (idx != -1) {
				idx += CONTENT_LENGTH.length();
				String contentLength = "";
				
				for (int i = idx; i<line.length(); i++) {
					if (line.charAt(i) == '\r') break;
					contentLength += line.charAt(i);
				}
				
				if (contentLength.length()>0) {
					int length = Integer.parseInt(contentLength.trim()); 
					
					if (length<=(line.length() - tokenIdx)) {

						return MessageDecoderResult.OK;
					} else {

						return MessageDecoderResult.NEED_DATA;
					}
				} else {

					return MessageDecoderResult.OK;
				}
			} else {
				return MessageDecoderResult.OK;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("nok");
		
		return MessageDecoderResult.NOT_OK;
	}

	/* (non-Javadoc)
	 * @see org.apache.mina.filter.codec.demux.MessageDecoder#decode(org.apache.mina.common.IoSession, org.apache.mina.common.ByteBuffer, org.apache.mina.filter.codec.ProtocolDecoderOutput)
	 */
	public MessageDecoderResult decode(IoSession session, ByteBuffer in,
			ProtocolDecoderOutput out) throws Exception {
		// TODO Auto-generated method stub
			
		try {
			RtspMessage rtsp = RtspMessageFactory.constructor(in);
			out.write(rtsp);
			return MessageDecoderResult.OK;
		} catch (RtspException e) {
			return MessageDecoderResult.NOT_OK;
		}
		
	}

	
	/* (non-Javadoc)
	 * @see org.apache.mina.filter.codec.demux.MessageDecoder#finishDecode(org.apache.mina.common.IoSession, org.apache.mina.filter.codec.ProtocolDecoderOutput)
	 */
	public void finishDecode(IoSession session, ProtocolDecoderOutput out)
			throws Exception {
		// TODO Auto-generated method stub

	}


}
