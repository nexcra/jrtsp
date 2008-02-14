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
package heye.rtsp.api.impl;

import java.net.InetSocketAddress;

import org.apache.mina.common.ConnectFuture;
import org.apache.mina.common.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.SocketConnectorConfig;


import heye.rtsp.action.RtspAction;
import heye.rtsp.action.RtspActionHandler;
import heye.rtsp.api.AbstractRtspClient;
import heye.rtsp.net.codec.RtspProtocolCodecFactory;
import heye.rtsp.protocol.RtspMessage;


/**
 * @author Jiang Ying
 * 
 */
public class RtspClient extends AbstractRtspClient {
	
	RtspActionHandler actionHandler = null;
	MessageHandler msgHandler = null;
	
	/**
	 * 
	 */
	public RtspClient() {
		// TODO Auto-generated constructor stub
    	actionHandler = new RtspActionHandler();
    	msgHandler = new MessageHandler(actionHandler);
    	rtspHandler.addMessageListener(msgHandler);
	}

	/* (non-Javadoc)
	 * @see heye.rtsp.api.RtspClientInterface#connect()
	 */
	public boolean connect(InetSocketAddress addr) throws IllegalStateException{
		// TODO Auto-generated method stub
        if (ioSession != null && ioSession.isConnected()) {
            throw new IllegalStateException(
                    "Already connected. Disconnect first.");
        }
        try {
        	connector.setWorkerTimeout(1);
        	SocketConnectorConfig cfg = new SocketConnectorConfig();
        	cfg.setConnectTimeout(30);
		
        	cfg.getFilterChain().addLast("codec", 
        			new ProtocolCodecFilter(new RtspProtocolCodecFactory()));

        	ConnectFuture future = connector.connect(addr, rtspHandler, cfg);
		
        	future.join();
		
        	this.connected = future.isConnected();
		
        	if (!connected) {
        		this.disconnect();
        		return false;
        	}
        	ioSession = future.getSession();
        	ioSession.setIdleTime(IdleStatus.BOTH_IDLE, 600);
        } catch (Exception e) {
        	e.printStackTrace();
        	return false;
        }
        return true;
	}
	
	public void disconnect() {
		// TODO Auto-generated method stub
		try {
			if (ioSession != null) ioSession.close();
			if (actionHandler != null) actionHandler.clearClientRequestAction();
		}catch(Exception e){
			// do nothing...
		}finally {
			ioSession = null;
		}
		this.connected = false;
	}
	
	public void setTimeout(int timeout) {
		if (ioSession != null)
			ioSession.setIdleTime(IdleStatus.WRITER_IDLE, timeout);
	}

	/* (non-Javadoc)
	 * @see heye.rtsp.api.RtspClientInterface#send(heye.rtsp.action.RtspAction)
	 */
	public synchronized void send(RtspAction action) {
		// TODO Auto-generated method stub
		RtspMessage msg = action.getRtspMessage();
		actionHandler.addClientRequestAction(action);
		ioSession.write(msg);
	}

	public void addServerRequestAction(RtspAction action, String key) {
		this.actionHandler.addServerRequestAction(action, key);
	}
	
	public void removeServerRequestAction(String key) {
		this.actionHandler.removeServerRequestAction(key);
	}
	
	public void clearServerRequestAction() {
		this.actionHandler.clearServerRequestAction();
	}
}
