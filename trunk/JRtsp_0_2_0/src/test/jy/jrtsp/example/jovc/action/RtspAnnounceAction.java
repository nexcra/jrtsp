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
package jy.jrtsp.example.jovc.action;

import jy.jrtsp.action.RtspAction;
import jy.jrtsp.common.RtspSession;
import jy.jrtsp.example.jovc.CoshipClientSupport;
import jy.jrtsp.example.jovc.MainFrame;
import jy.jrtsp.protocol.RtspMessage;
import jy.jrtsp.protocol.RtspRequest;

/**
 * @author Jiang Ying
 * 2008-1-25 ÉÏÎç12:57:23
 */
public class RtspAnnounceAction implements RtspAction {

	public final static String ACTION_KEY = "ANNOUNCE";
	
	private MainFrame ui;
	private CoshipClientSupport client;
	/**
	 * 
	 */
	public RtspAnnounceAction(MainFrame ui, CoshipClientSupport client) {
		// TODO Auto-generated constructor stub
		this.ui = ui;
		this.client = client;
	}
	

	public int execute(RtspSession session, RtspMessage msg) {
		RtspRequest resp = (RtspRequest) msg;
		String xnotice = resp.getHeader("x-notice");
		ui.log("ANNOUNCE : " + xnotice+ "\n");
		String token[] = xnotice.split(" ", 2);
		if (token[0].trim().equals("2101")) {
			client.teardown(session, null);
		} else if (token[0].trim().equals("2104"))	{
			client.play(session, null, 1);
		} else if (token[0].trim().equals("5401")) {
			client.disconnect(session);
		} else if (token[0].trim().equals("5402")) {
			client.disconnect(session);
		}
		return RtspAction.RUNNING;
	}

}
