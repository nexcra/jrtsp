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
package heye.rtsp.api.impl;

import heye.rtsp.action.RtspActionHandler;
import heye.rtsp.net.RtspMessageHandler;
import heye.rtsp.protocol.RtspRequest;
import heye.rtsp.protocol.RtspResponse;

/**
 * @author Jiang Ying
 *
 */
public class MessageHandler implements RtspMessageHandler {

	RtspActionHandler actionHandler;
	/**
	 * 
	 */
	public MessageHandler(RtspActionHandler actionHandler) {
		// TODO Auto-generated constructor stub
		this.actionHandler = actionHandler;
	}

	/* (non-Javadoc)
	 * @see heye.rtsp.net.RtspMessageHandler#RtspRequestReceived(heye.rtsp.protocol.RtspRequest)
	 */
	public void RtspRequestReceived(RtspRequest msg) {
		// TODO Auto-generated method stub
		actionHandler.execute(msg);
	}

	/* (non-Javadoc)
	 * @see heye.rtsp.net.RtspMessageHandler#RtspRequestSent(heye.rtsp.protocol.RtspRequest)
	 */
	public void RtspRequestSent(RtspRequest msg) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see heye.rtsp.net.RtspMessageHandler#RtspResponseReceived(heye.rtsp.protocol.RtspResponse)
	 */
	public void RtspResponseReceived(RtspResponse msg) {
		// TODO Auto-generated method stub
		actionHandler.execute(msg);
	}

	/* (non-Javadoc)
	 * @see heye.rtsp.net.RtspMessageHandler#RtspResponseSent(heye.rtsp.protocol.RtspResponse)
	 */
	public void RtspResponseSent(RtspResponse msg) {
		// TODO Auto-generated method stub

	}

}
