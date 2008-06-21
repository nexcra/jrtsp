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
package jy.jrtsp.common;

import java.net.SocketAddress;

import jy.jrtsp.protocol.RtspRequest;
import jy.jrtsp.protocol.RtspResponse;


/**
 * @author Jiang Ying
 * Date: 2008-4-27 ÏÂÎç08:41:43
 */
public interface RtspEventListener {
	
	public void connectFailed(SocketAddress address);
	
    /**
     * Invoked when a connection has been opened.
     */
	public void sessionOpened(RtspSession session);
	
    /**
     * Invoked when a connection becomes idle.
     */
	public void sessionIdle(RtspSession session);
	
    /**
     * Invoked when a message is received.
     */
	public void requestMessageReceived(RtspSession session, RtspRequest message);
	
    /**
     * Invoked when a message is received.
     */
	public void responseMessageReceived(RtspSession session, RtspResponse message);
	
    /**
     * Invoked when a message written by {@link RtspSession#write(Object)} is
     * sent out.
     */
	public void requestMessageSent(RtspSession session, RtspRequest message);
	
    /**
     * Invoked when a message written by {@link RtspSession#write(Object)} is
     * sent out.
     */
	public void responseMessageSent(RtspSession session, RtspResponse message);
	
    /**
     * Invoked when a connection is closed.
     */
	public void sessionClosed(RtspSession session);
	
    /**
     * Invoked when any exception is thrown. 
     * If <code>cause</code> is instanceof {@link IOException}, 
     * JRTSP will close the connection automatically.
     */
	public void exceptionCaught(RtspSession session, Throwable cause);
}
