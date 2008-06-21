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
package jy.jrtsp.example.jovc;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URISyntaxException;
import java.util.concurrent.atomic.AtomicInteger;

import jy.jrtsp.action.RtspAction;
import jy.jrtsp.common.ConnectListener;
import jy.jrtsp.common.RtspActionHandler;
import jy.jrtsp.common.RtspConnection;
import jy.jrtsp.common.RtspSession;
import jy.jrtsp.example.jovc.action.RtspAnnounceAction;
import jy.jrtsp.example.jovc.action.RtspGetParameterAction;
import jy.jrtsp.example.jovc.action.RtspPauseAction;
import jy.jrtsp.example.jovc.action.RtspPlayAction;
import jy.jrtsp.example.jovc.action.RtspScaleAction;
import jy.jrtsp.example.jovc.action.RtspSetParameterAction;
import jy.jrtsp.example.jovc.action.RtspSetupAction;
import jy.jrtsp.example.jovc.action.RtspTeardownAction;
import jy.jrtsp.example.jovc.listener.EventListener;
import jy.jrtsp.io.mina.MinaRtspConnection;
import jy.jrtsp.protocol.RtspMessage;
import jy.jrtsp.protocol.RtspRequest;
import jy.jrtsp.util.RtspUrl;


/**
 * @author Jiang Ying
 * 2008-1-25 ÉÏÎç12:42:50
 */
public class CoshipClientSupport {

	private final String userAgent = "Coship RTSP Client 1.0";
	
	private RtspConnection client;
	private MainFrame ui;
	
	private AtomicInteger seq = new AtomicInteger(0);
	
	/**
	 * 
	 */
	public CoshipClientSupport(MainFrame ui) {
		// TODO Auto-generated constructor stub
		this.ui = ui;
		client = new MinaRtspConnection();
		client.setConnectTimeout(30);
		client.setEventListener(new EventListener(ui,this));

	}

	
	public void connect(final RtspUrl context, final SocketAddress localAddress) {

		ui.showButton(false, false, false, false, false);
		client.connect(new InetSocketAddress(context.getHost(), context.getPort()), 
				new ConnectListener() {

					public void operationCompleted(RtspSession session) {
						// TODO Auto-generated method stub

						if (session.isConnected()) {
							addEvents(session.getActionHandler());
							session.setAttribute(SessionKey.RTSP_LOCAL_ADDRESS, localAddress);
							session.setAttribute(SessionKey.RTSP_CONTEXT, context);
							ui.setSession(session);
							setup(session, context, localAddress);
						}
					}
			
		});
	}
	
	
	private void addEvents(RtspActionHandler handler) {
		RtspAnnounceAction action = new RtspAnnounceAction(ui, this);
		handler.addActionListener(RtspAnnounceAction.ACTION_KEY, action);
		RtspSetParameterAction action1 = new RtspSetParameterAction(ui, this);
		handler.addActionListener(RtspSetParameterAction.ACTION_KEY, action1);
	}
	
	public void disconnect(RtspSession session) {
		session.close();
		ui.showButton(true, false, false, false, false);
		ui.log("Disconnected\n");
	}
	
	public String getCSeq() {
		return String.valueOf(seq.incrementAndGet());
	}

	public void writeMessage(RtspSession session, RtspMessage msg, RtspAction action) {
		msg.setHeader("User-Agent", this.userAgent);
		msg.setHeader("CSeq", this.getCSeq());
		session.write(msg, action);
	}
	
	public void redirect(RtspSession session, String context) {
		try {
			SocketAddress address = (SocketAddress) session.getAttribute(SessionKey.RTSP_LOCAL_ADDRESS);
			session.close();
			ui.log("Disconnected\n");
			RtspUrl url = new RtspUrl(context);
			this.connect(url, address);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			disconnect(session);
		}
		
	}
	
	public void setup(RtspSession session, RtspUrl context, SocketAddress local) {
		InetSocketAddress adr = (InetSocketAddress)local;
		RtspRequest msg = new RtspRequest();
		msg.setMethod("SETUP");
		msg.setContext(context.toString());
		msg.setHeader("x-playNow", "");
		msg.setHeader("x-mayNotify", "");
		msg.setHeader("Range", "npt=-end");
		msg.setHeader("Transport", "MP2T/H2221/UDP;unicast;destination=" + adr.getAddress().getHostAddress() + ":" + String.valueOf(adr.getPort()));
		RtspAction action = new RtspSetupAction(ui, this);
		writeMessage(session, msg, action);
		
	}
	
	public void teardown(RtspSession session, RtspUrl context) {
		RtspRequest msg = new RtspRequest();
		msg.setMethod("TEARDOWN");
		msg.setContext(context == null? 
				getContext(session) : context.toString());
		msg.setHeader("Session", getSessionString(session));
		RtspAction action = new RtspTeardownAction(ui, this);
		writeMessage(session, msg, action);
	}
	              
	public void play(RtspSession session, RtspUrl context, int scale) {
		RtspRequest msg = new RtspRequest();
		msg.setMethod("PLAY");
		msg.setContext(context == null? 
				getContext(session) : context.toString());
		msg.setHeader("Session", getSessionString(session));
		msg.setHeader("Scale", String.valueOf(scale));
		RtspAction action = new RtspPlayAction(ui, this);
		writeMessage(session, msg, action);
	}
	
	public void pause(RtspSession session, RtspUrl context) {
		RtspRequest msg = new RtspRequest();
		msg.setMethod("PAUSE");
		msg.setContext(context == null? 
				getContext(session) : context.toString());
		msg.setHeader("Session", getSessionString(session));
		RtspAction action = new RtspPauseAction(ui, this);
		writeMessage(session, msg, action);
	}

	public void scale(RtspSession session, RtspUrl context, int scale) {
		RtspRequest msg = new RtspRequest();
		msg.setMethod("PLAY");
		msg.setContext(context == null? 
				getContext(session) : context.toString());
		msg.setHeader("Session", getSessionString(session));
		msg.setHeader("Scale", String.valueOf(scale));
		RtspAction action = new RtspScaleAction(ui, this);
		writeMessage(session, msg, action);
	}

	public void heartBeat(RtspSession session, RtspUrl context) {
		RtspRequest msg = new RtspRequest();
		msg.setMethod("GET_PARAMETER");
		msg.setContext(context == null? 
				getContext(session) : context.toString());
		msg.setHeader("Session", getSessionString(session));
		RtspAction action = new RtspGetParameterAction(ui, this);
		writeMessage(session, msg, action);
	}
	
	private String getSessionString(RtspSession session) {
		return (String)session.getAttribute(SessionKey.RTSP_SESSION_STRING);
	}
	
	private String getContext(RtspSession session) {
		return ((RtspUrl)session.getAttribute(SessionKey.RTSP_CONTEXT)).toString();
	}
}
