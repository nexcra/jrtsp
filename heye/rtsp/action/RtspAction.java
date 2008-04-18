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
package heye.rtsp.action;

import heye.rtsp.protocol.RtspMessage;


/**
 * @author Jiang Ying
 * 2008-1-17 ÏÂÎç03:40:07
 */
public abstract class RtspAction {
	protected RtspMessage message;
	
	/**
	 * 
	 */
	public RtspAction() {
		message = null;
	}
	
	/**
	 * @param msg
	 */
	public void execute(RtspMessage msg) {
		
	}
	
	/**
	 * @param msg
	 */
	public void assignRtspMessage(RtspMessage msg) {
		this.message = msg;
	}
	
	/**
	 * @return
	 */
	public RtspMessage getRtspMessage() {
		return message;
	}
}
