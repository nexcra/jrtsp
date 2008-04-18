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
import heye.rtsp.protocol.RtspResponse;

/**
 * @author Jiang Ying
 *
 */
public class StopAction extends RtspAction {

	Example1 client;
	/**
	 * 
	 */
	public StopAction() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	public StopAction(Example1 ex) {
		this();
		client = ex;
	}
	
	public void execute(RtspMessage msg) {
		RtspResponse response = (RtspResponse)msg;
		System.out.println(response.GetDescribe());
		client.disconnect();
	}

}
