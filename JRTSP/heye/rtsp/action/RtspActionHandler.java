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

import heye.rtsp.protocol.RtspRequest;
import heye.rtsp.protocol.RtspResponse;

import java.util.Hashtable;
import java.util.Iterator;


/**
 * @author Jiang Ying
 * 2008-1-29 ÏÂÎç08:29:58
 */
public class RtspActionHandler {

	Hashtable<String, RtspAction> clientRequestActions = new Hashtable<String, RtspAction>();
	Hashtable<String, RtspAction> serverRequestActions = new Hashtable<String, RtspAction>();
	/**
	 * 
	 */
	public RtspActionHandler() {
		clientRequestActions.clear();
		serverRequestActions.clear();
		// TODO Auto-generated constructor stub
	}

	public void addClientRequestAction(RtspAction action) {
		clientRequestActions.put(((RtspRequest)action.getRtspMessage()).GetHeader("CSeq"), action);
	}
	
	public void removeClientRequestAction(String key) {
		clientRequestActions.remove(key);
	}
	
	public void clearClientRequestAction() {
		clientRequestActions.clear();
	}
	
	public void addServerRequestAction(RtspAction action, String key) {
		serverRequestActions.put(key, action);
	}
	
	public void removeServerRequestAction(String key) {
		serverRequestActions.remove(key);
	}
	
	public void clearServerRequestAction() {
		serverRequestActions.clear();
	}
	
	public Iterator getServerRequestActionListener() {
		return serverRequestActions.entrySet().iterator();
	}
	
	public void execute(RtspResponse msg) {
		String seq = msg.GetHeader("CSeq");
		RtspAction action = clientRequestActions.get(seq);
		if (action != null) {
			action.execute(msg);
			this.removeClientRequestAction(seq);
		}
	}
	
	public void execute(RtspRequest msg) {
		RtspAction action = serverRequestActions.get(msg.GetMethod());

		if (action != null) {
			action.execute(msg);
		}
		
	}
}
