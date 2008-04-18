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

import heye.rtsp.protocol.RtspMessage;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;

/**
 * @author Jiang Ying
 *
 */
public class RtspRequestEncoder implements MessageEncoder {

    private static final Set TYPES;

    static {
        HashSet types = new HashSet();
        types.add(RtspMessage.class);
        TYPES = Collections.unmodifiableSet(types);
    }

    
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public void encode(IoSession in, Object message, ProtocolEncoderOutput out) throws Exception {
		// TODO Auto-generated method stub
		RtspMessage msg = (RtspMessage)message;
		out.write(msg.toByteBuffer());
		/*ByteBuffer buf = msg.toByteBuffer();
		for (int i=0;i<buf.limit();i++) {
			System.out.print((char)buf.get());
		}*/
	}

	public Set getMessageTypes() {
		// TODO Auto-generated method stub
		return TYPES;
	}

}
