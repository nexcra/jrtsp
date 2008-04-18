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

import java.net.InetSocketAddress;
import java.net.URISyntaxException;

import heye.rtsp.api.impl.RtspClient;
import heye.rtsp.net.RtspUrl;
import heye.rtsp.protocol.RtspRequest;


/**
 * @author Jiang Ying
 * 2008-1-17 ÏÂÎç03:39:51
 */
public class Example1 {

	RtspClient client = new RtspClient();
	
	/**
	 * @throws URISyntaxException 
	 * 
	 */
	public Example1() throws URISyntaxException {
		// TODO Auto-generated constructor stub
		RtspUrl url = new RtspUrl("192.168.1.1", 5004, "movie1");
		client.connect(new InetSocketAddress(url.GetHost(), url.GetPort()));
		System.out.println(client.isConnected());
		RtspRequest msg = new RtspRequest();
		msg.SetMethod("SETUP");
		msg.SetContext(url.toString());
		msg.SetHeader("x-playNow", "");
		msg.SetHeader("x-mayNotify", "");
		msg.SetHeader("Range", "npt=-end");
		msg.SetHeader("Transport",
				"MP2T/H2221/UDP;unicast;destination=192.169.1.100:11111");
		msg.SetHeader("CSeq", "1");
		msg.SetHeader("User-Agent", "Coship RTSP Client 1.0");
		this.setup(msg);

	}
	public void setup(RtspRequest msg) {
		SetupAction action = new SetupAction(this);
		action.assignRtspMessage(msg);
		client.send(action);
	}
	
	public void stop(RtspRequest msg) {
		StopAction action = new StopAction(this);
		action.assignRtspMessage(msg);
		client.send(action);
	}
	
	public void disconnect() {
		client.disconnect();
	}
	/**
	 * @param args
	 * @throws URISyntaxException
	 */
	public static void main(String[] args) throws URISyntaxException {
		// TODO Auto-generated method stub
		new Example1();
	}

}
