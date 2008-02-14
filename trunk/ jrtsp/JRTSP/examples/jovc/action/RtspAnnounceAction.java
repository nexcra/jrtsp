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
package examples.jovc.action;

import examples.jovc.CoshipClientSupport;
import examples.jovc.MainFrame;
import heye.rtsp.action.RtspAction;
import heye.rtsp.protocol.RtspMessage;
import heye.rtsp.protocol.RtspRequest;

/**
 * @author Jiang Ying
 * 2008-1-25 ÉÏÎç12:57:23
 */
public class RtspAnnounceAction extends RtspAction {

	MainFrame ui;
	CoshipClientSupport client;
	/**
	 * 
	 */
	public RtspAnnounceAction(MainFrame ui, CoshipClientSupport client) {
		// TODO Auto-generated constructor stub
		this();
		this.ui = ui;
		this.client = client;
	}
	
	public RtspAnnounceAction() {
		// TODO Auto-generated constructor stub
		super();
	}

	public void execute(RtspMessage msg) {
		RtspRequest resp = (RtspRequest) msg;
		String xnotice = resp.GetHeader("x-notice");
		ui.log("ANNOUNCE : " + xnotice+ "\n");
		String token[] = xnotice.split(" ", 2);
		if (token[0].trim().equals("2101")) {
			client.teardown(null);
		} else if (token[0].trim().equals("2104"))	{
			client.play(null, 1);
		} else if (token[0].trim().equals("5401")) {
			client.disconnect();
		} else if (token[0].trim().equals("5402")) {
			client.disconnect();
		}
	}

}
