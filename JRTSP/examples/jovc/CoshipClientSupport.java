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
package examples.jovc;

import java.net.InetSocketAddress;
import java.net.URISyntaxException;

import examples.jovc.action.RtspAnnounceAction;
import examples.jovc.action.RtspGetParameterAction;
import examples.jovc.action.RtspPauseAction;
import examples.jovc.action.RtspPlayAction;
import examples.jovc.action.RtspScaleAction;
import examples.jovc.action.RtspSetParameterAction;
import examples.jovc.action.RtspSetupAction;
import examples.jovc.action.RtspTeardownAction;

import heye.rtsp.action.RtspAction;
import heye.rtsp.api.impl.RtspClient;
import heye.rtsp.net.RtspUrl;
import heye.rtsp.protocol.RtspMessage;
import heye.rtsp.protocol.RtspRequest;

/**
 * @author Jiang Ying
 * 2008-1-25 ÉÏÎç12:42:50
 */
public class CoshipClientSupport {

	private final String userAgent = "Coship RTSP Client 1.0";
	
	private RtspClient client;
	private MainFrame ui;
	private RtspUrl context;
	
	private int seq = 1;
	private String session = "";
	
	private String clientIP = null;
	private String clientPort = null;
	
	/**
	 * 
	 */
	public CoshipClientSupport(MainFrame ui) {
		// TODO Auto-generated constructor stub
		this.ui = ui;
		client = new RtspClient();
		client.addIoHandler(new RtspIoHandler(this));
		client.addMessageHandler(new RtspMessageHandler());
	}
	
	public void addEvents() {
		RtspAnnounceAction action = new RtspAnnounceAction(ui, this);
		client.addServerRequestAction(action, "ANNOUNCE");
		RtspSetParameterAction action1 = new RtspSetParameterAction(ui, this);
		client.addServerRequestAction(action1, "SET_PARAMETER");
	}
	
	public boolean connect(RtspUrl context, String clientIP, String clientPort) {
		this.context = context == null ? this.context : context;
		this.clientIP = (clientIP == null ? this.clientIP : clientIP);
		this.clientPort = (clientPort == null ? this.clientPort : clientPort);
		
		if (client.connect(new InetSocketAddress(context.GetHost(), context.GetPort()))) {
			ui.log("Connected to: " + context.GetHost() + "\n");
			addEvents();
			client.setTimeout(30);
		} else {
			ui.log("Connect failed\n");
			return false;
		}
		this.setup(this.context, this.clientIP, this.clientPort);
		return true;
	}
	
	public void disconnect() {
		client.disconnect();
		ui.showButton(true, false, false, false, false);
		ui.log("Disconnected\n");
	}
	
	public synchronized String getCSeq() {
		seq += 1;
		return String.valueOf(seq);
	}
	
	public void setSession(String session) {
		this.session = session;
	}
	
	public void send(RtspMessage msg) {
		client.send(msg);
	}
	
	public void redirect(String context) {
		try {
			client.disconnect();
			ui.log("Disconnected\n");
			this.context = new RtspUrl(context);
			this.connect(this.context, null, null);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			client.disconnect();
		}
		
	}
	
	public void setup(RtspUrl context, String ip, String port) {
		RtspRequest msg = new RtspRequest();
		msg.SetMethod("SETUP");
		msg.SetContext(context.toString());
		msg.SetHeader("CSeq", this.getCSeq());
		msg.SetHeader("x-playNow", "");
		msg.SetHeader("x-mayNotify", "");
		msg.SetHeader("Range", "npt=-end");
		msg.SetHeader("User-Agent", this.userAgent);
		msg.SetHeader("Transport", "MP2T/H2221/UDP;unicast;destination=" + ip + ":" + port);
		RtspAction action = new RtspSetupAction(ui, this);
		action.assignRtspMessage(msg);
		client.send(action);
	}
	
	public void teardown(RtspUrl context) {
		RtspRequest msg = new RtspRequest();
		msg.SetMethod("TEARDOWN");
		msg.SetContext(context == null? this.context.toString() : context.toString());
		msg.SetHeader("CSeq", this.getCSeq());
		msg.SetHeader("Session", this.session);
		msg.SetHeader("User-Agent", this.userAgent);
		RtspAction action = new RtspTeardownAction(ui, this);
		action.assignRtspMessage(msg);
		client.send(action);
	}
	              
	public void play(RtspUrl context, int scale) {
		RtspRequest msg = new RtspRequest();
		msg.SetMethod("PLAY");
		msg.SetContext(context == null? this.context.toString() : context.toString());
		msg.SetHeader("CSeq", this.getCSeq());
		msg.SetHeader("Session", this.session);
		msg.SetHeader("User-Agent", this.userAgent);
		msg.SetHeader("Scale", String.valueOf(scale));
		RtspAction action = new RtspPlayAction(ui, this);
		action.assignRtspMessage(msg);
		client.send(action);	
	}
	
	public void pause(RtspUrl context) {
		RtspRequest msg = new RtspRequest();
		msg.SetMethod("PAUSE");
		msg.SetContext(context == null? this.context.toString() : context.toString());
		msg.SetHeader("CSeq", this.getCSeq());
		msg.SetHeader("Session", this.session);
		msg.SetHeader("User-Agent", this.userAgent);
		RtspAction action = new RtspPauseAction(ui, this);
		action.assignRtspMessage(msg);
		client.send(action);
	}

	public void scale(RtspUrl context, int scale) {
		RtspRequest msg = new RtspRequest();
		msg.SetMethod("PLAY");
		msg.SetContext(context == null? this.context.toString() : context.toString());
		msg.SetHeader("CSeq", this.getCSeq());
		msg.SetHeader("Session", this.session);
		msg.SetHeader("User-Agent", this.userAgent);
		msg.SetHeader("Scale", String.valueOf(scale));
		RtspAction action = new RtspScaleAction(ui, this);
		action.assignRtspMessage(msg);
		client.send(action);
	}

	public void heartBeat(RtspUrl context) {
		RtspRequest msg = new RtspRequest();
		msg.SetMethod("GET_PARAMETER");
		msg.SetContext(context == null? this.context.toString() : context.toString());
		msg.SetHeader("CSeq", this.getCSeq());
		msg.SetHeader("Session", this.session);
		msg.SetHeader("User-Agent", this.userAgent);
		RtspAction action = new RtspGetParameterAction(ui, this);
		action.assignRtspMessage(msg);
		client.send(action);
	}
}
