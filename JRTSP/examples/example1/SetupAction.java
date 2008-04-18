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
package examples.example1;

import heye.rtsp.action.RtspAction;
import heye.rtsp.protocol.RtspMessage;
import heye.rtsp.protocol.RtspRequest;
import heye.rtsp.protocol.RtspResponse;

/**
 * @author Jiang Ying
 *
 */
public class SetupAction extends RtspAction {

	Example1 client;
	/**
	 * 
	 */
	SetupAction(Example1 ex){
		this();
		client = ex;
	}
	
	SetupAction() {
		// TODO Auto-generated constructor stub
		super();
	}
	


	public void execute(RtspMessage msg) {
		RtspResponse response = (RtspResponse)msg;
		System.out.println(response.GetBody());
		
		RtspRequest msg1 = new RtspRequest();
		msg1.SetMethod("TEARDOWN");
		msg1.SetContext("rtsp://218.24.6.115:5555/wuji");
		msg1.SetHeader("CSeq", "2");
		msg1.SetHeader("Session", response.GetHeader("Session"));
		msg1.SetHeader("User-Agent", "Coship RTSP Client 1.0");
		
		client.stop(msg1);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
