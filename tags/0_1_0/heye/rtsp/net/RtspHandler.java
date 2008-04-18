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
package heye.rtsp.net;

import heye.rtsp.protocol.RtspRequest;
import heye.rtsp.protocol.RtspResponse;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.mina.common.IdleStatus;
import org.apache.mina.common.IoHandlerAdapter;
import org.apache.mina.common.IoSession;

/**
 * @author Jiang Ying
 *
 */
public class RtspHandler extends IoHandlerAdapter {

	Set<RtspMessageHandler> messageEvents = Collections.synchronizedSet( new HashSet<RtspMessageHandler>() );
	Set<RtspIoHandler> ioEvents = Collections.synchronizedSet(new HashSet<RtspIoHandler>());
	
	/**
	 * 
	 */
	public RtspHandler() {
		// TODO Auto-generated constructor stub
	}


	public void sessionCreated(IoSession session) {
		synchronized (ioEvents) {
			Iterator it = ioEvents.iterator();
			while (it.hasNext()) {
				RtspIoHandler handler = (RtspIoHandler) it.next();
				handler.onCreated();
			}
		}
	}
	
	public void sessionOpened(IoSession session) {
		synchronized (ioEvents) {
			Iterator it = ioEvents.iterator();
			while (it.hasNext()) {
				RtspIoHandler handler = (RtspIoHandler) it.next();
				handler.onOpened();
			}
		}
	}
	
	public void sessionIdle(IoSession session, IdleStatus status) {
		synchronized (ioEvents) {
			Iterator it = ioEvents.iterator();
			while (it.hasNext()) {
				RtspIoHandler handler = (RtspIoHandler) it.next();
				handler.onIdle(status);
			}
		}
	}
	
	public void messageReceived(IoSession session, Object message) {
		synchronized (messageEvents) {
			Iterator it = messageEvents.iterator();
			while (it.hasNext()) {
				RtspMessageHandler handler = (RtspMessageHandler)it.next();
				if (message instanceof RtspRequest) {
					handler.RtspRequestReceived((RtspRequest)message);
				} else if (message instanceof RtspResponse) {
					handler.RtspResponseReceived((RtspResponse)message);
				}
			}
		}
	}
	
	public void messageSent(IoSession session, Object message) {
		synchronized (messageEvents) {
			Iterator it = messageEvents.iterator();
			while (it.hasNext()) {
				RtspMessageHandler handler = (RtspMessageHandler)it.next();
				if (message instanceof RtspRequest) {
					handler.RtspRequestSent((RtspRequest)message);
				} else if (message instanceof RtspResponse) {
					handler.RtspResponseSent((RtspResponse)message);
				}
			}
		}
	}
	
	public void sessionClosed(IoSession session) {
		synchronized (ioEvents) {
			Iterator it = ioEvents.iterator();
			while (it.hasNext()) {
				RtspIoHandler handler = (RtspIoHandler) it.next();
				handler.onClosed();
			}
		}
	}
	
	public void exceptionCaught(IoSession session, Throwable cause){
		synchronized (ioEvents) {
			Iterator it = ioEvents.iterator();
			while (it.hasNext()) {
				RtspIoHandler handler = (RtspIoHandler) it.next();
				handler.exceptionCaught(cause);
			}
		}
	}
	
	public boolean addMessageListener(RtspMessageHandler listener) {
		return messageEvents.add(listener);
	}
	
	public boolean removeMessageListener(RtspMessageHandler listener) {
		return messageEvents.remove(listener);
	}
	
	public void clearMessageListener() {
		messageEvents.clear();
	}
	
	public Iterator getMessageListener() {
		return messageEvents.iterator();
	}
	
	public boolean addIoListener(RtspIoHandler handler){
		return ioEvents.add(handler);
	}
	
	public boolean removeIoListener(RtspIoHandler handler) {
		return ioEvents.remove(handler);
	}
	
	public void clearIoListener() {
		ioEvents.clear();
	}
	
	public Iterator getIoListener() {
		return ioEvents.iterator();
	}
}
