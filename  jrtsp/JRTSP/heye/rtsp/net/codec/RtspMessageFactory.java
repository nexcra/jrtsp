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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.CharsetDecoder;

import heye.rtsp.protocol.RtspException;
import heye.rtsp.protocol.RtspMessage;
import heye.rtsp.protocol.RtspRequest;
import heye.rtsp.protocol.RtspResponse;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.util.CharsetUtil;

/**
 * @author Jiang Ying
 *
 */
public class RtspMessageFactory {

	/**
	 * 
	 */
	public RtspMessageFactory() {
		// TODO Auto-generated constructor stub
	}

	public static RtspMessage constructor(ByteBuffer in) throws RtspException {
		
		try {
			CharsetDecoder decoder = CharsetUtil.getDefaultCharset().newDecoder();
			BufferedReader rdr = new BufferedReader(new StringReader(in.getString(decoder)));

			String line="";
			while (line.trim().equals("")) {
				line = rdr.readLine();
				if (line == null) throw new RtspException("Rtsp message error");
			}
			in.position(0);
			String token[] = line.split(" ", 3);
			if (token.length !=3) {
				throw new RtspException("Rtsp message error");
			}
			if (token[0].startsWith("RTSP")) {
				return new RtspResponse(in);
			} else {
				return new RtspRequest(in);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RtspException("Rtsp message error");
		}
	}

}
