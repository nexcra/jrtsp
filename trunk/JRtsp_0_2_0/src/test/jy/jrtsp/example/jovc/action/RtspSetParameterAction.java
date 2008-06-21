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
import jy.jrtsp.protocol.RtspResponse;

/**
 * @author Jiang Ying
 * 2008-1-25 ÉÏÎç12:57:23
 */
public class RtspSetParameterAction implements RtspAction {

	public final static String ACTION_KEY = "SET_PARAMETER";
	
	private MainFrame ui;
	//private CoshipClientSupport client;
	
	/**
	 * 
	 */
	public RtspSetParameterAction(MainFrame ui, CoshipClientSupport client) {
		// TODO Auto-generated constructor stub
		this.ui = ui;
	}


	public int execute(RtspSession session, RtspMessage msg) {
		RtspRequest request = (RtspRequest) msg;
		ui.log("SET_PARAMETER : " + request.getHeader("Scale") + "\n");
		
		/*if (resp.GetStatus().equals("200")) {
			ui.showButton(true, false, true, true, true);
		} else {
			ui.showButton(false, true, true, true, true);
		}*/
		RtspResponse response = new RtspResponse();
		response.setDescription("OK");
		response.setStatus("200");
		response.setHeader("CSeq", request.getHeader("CSeq"));
		response.setHeader("Session", request.getHeader("Session"));
		session.write(msg);
		ui.showButton(false, true, true, true, true);
		return RtspAction.RUNNING;
	}

}
