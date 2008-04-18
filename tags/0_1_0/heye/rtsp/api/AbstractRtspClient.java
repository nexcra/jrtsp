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
package heye.rtsp.api;

import heye.rtsp.net.RtspHandler;
import heye.rtsp.net.RtspIoHandler;
import heye.rtsp.net.RtspMessageHandler;
import heye.rtsp.net.codec.RtspProtocolCodecFactory;
import heye.rtsp.protocol.RtspMessage;

import java.net.InetSocketAddress;

import org.apache.mina.common.ConnectFuture;
import org.apache.mina.common.IdleStatus;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.SocketConnector;
import org.apache.mina.transport.socket.nio.SocketConnectorConfig;

/**
 * @author Jiang Ying
 * 2008-1-22 ÏÂÎç11:42:38
 */
public abstract class AbstractRtspClient {

	protected IoSession ioSession;
	protected RtspHandler rtspHandler = new RtspHandler();
	protected SocketConnector connector = new SocketConnector();
	
	protected boolean connected = false;
	
	
	/**
	 * 
	 */
	public AbstractRtspClient() {
		// TODO Auto-generated constructor stub

	}

	public boolean connect(InetSocketAddress addr) throws IllegalStateException {
		
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
        	return false;
        }
        return true;
	}
	
	public void disconnect() {
		try {
			if (ioSession != null) ioSession.close();
		}catch(Exception e){
			// do nothing...
		}finally {
			ioSession = null;
		}
		this.connected = false;
	}
	
	public boolean isConnected() {
		// TODO Auto-generated method stub
		return this.connected;
	}
	
	public synchronized void send(RtspMessage msg) {
		// TODO Auto-generated method stub
		ioSession.write(msg);
	}
	
	public boolean addMessageHandler(RtspMessageHandler handler) {
		// TODO Auto-generated method stub
		if (this.rtspHandler != null) {
			return rtspHandler.addMessageListener(handler);
		} else return false;
	}

	public boolean removeMessageHandler(RtspMessageHandler handler) {
		// TODO Auto-generated method stub
		if (this.rtspHandler != null) {
			return rtspHandler.removeMessageListener(handler);
		} else return false;
	}

	public void clearMessageHandler( ) {
		if (this.rtspHandler != null) rtspHandler.clearMessageListener();
	}

	public boolean addIoHandler(RtspIoHandler handler) {
		if (this.rtspHandler != null) {
			return rtspHandler.addIoListener(handler);
		} else return false;
	}
	
	public boolean removeIoHandler(RtspIoHandler handler) {
		if (this.rtspHandler != null) {
			return rtspHandler.removeIoListener(handler);
		} else return false;
	}
	
	public void clearIoHandler() {
		if (this.rtspHandler != null) {
			rtspHandler.clearIoListener();
		}
	}
	
}
