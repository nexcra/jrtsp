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

import org.apache.mina.common.IdleStatus;
import org.apache.mina.common.IoSession;

import jy.jrtsp.action.RtspAction;
import jy.jrtsp.protocol.RtspMessage;
import jy.jrtsp.protocol.RtspRequest;

/**
 * @author Jiang Ying
 *
 */
public class RtspSessionImpl implements RtspSession {

	private IoSession session;
	
	/**
	 * @param session
	 */
	public RtspSessionImpl(IoSession session) {
		// TODO Auto-generated constructor stub
		this.session = session;
	}

	/* (non-Javadoc)
	 * @see jy.jrtsp.common.RtspSession#clearAttributes()
	 */
	public void clearAttributes() {
		// TODO Auto-generated method stub
		if (session == null) return;
		for (String key : session.getAttributeKeys()) {
			session.removeAttribute(key);
		}
	}

	/* (non-Javadoc)
	 * @see jy.jrtsp.common.RtspSession#close()
	 */
	public void close() {
		// TODO Auto-generated method stub
		session.close();
	}

	/* (non-Javadoc)
	 * @see jy.jrtsp.common.RtspSession#getAttribute(java.lang.String)
	 */
	public Object getAttribute(String key) {
		// TODO Auto-generated method stub
		if (session == null) return null;
		return session.getAttribute(key);
	}

	/* (non-Javadoc)
	 * @see jy.jrtsp.common.RtspSession#removeAttribute(java.lang.String)
	 */
	public void removeAttribute(String key) {
		// TODO Auto-generated method stub
		if (session == null) return ;
		session.removeAttribute(key);
	}

	/* (non-Javadoc)
	 * @see jy.jrtsp.common.RtspSession#setAttribute(java.lang.String, java.lang.Object)
	 */
	public void setAttribute(String key, Object value) {
		// TODO Auto-generated method stub
		if (session == null) return ;
		session.setAttribute(key, value);
	}

	/* (non-Javadoc)
	 * @see jy.jrtsp.common.RtspSession#write(jy.jrtsp.protocol.RtspMessage, jy.jrtsp.action.RtspAction)
	 */
	public void write(RtspMessage message, RtspAction action) {
		// TODO Auto-generated method stub
		if (message instanceof RtspRequest) {
			String  seq = ((RtspRequest)message).getCSeq();
			if (seq != null) {
				getActionHandler().addActionListener(seq, action);
			}
		}
		session.write(message.toByteBuffer());
	}

	/* (non-Javadoc)
	 * @see jy.jrtsp.common.RtspSession#write(jy.jrtsp.protocol.RtspMessage)
	 */
	public void write(RtspMessage message) {
		// TODO Auto-generated method stub
		session.write(message.toByteBuffer());
	}

	/* (non-Javadoc)
	 * @see jy.jrtsp.common.RtspSession#getActionHandler()
	 */
	public RtspActionHandler getActionHandler() {
		return (RtspActionHandler) getAttribute(RtspActionHandler.SESSION_KEY);
	}

	/* (non-Javadoc)
	 * @see jy.jrtsp.common.RtspSession#isConnected()
	 */
	public boolean isConnected() {
		// TODO Auto-generated method stub
		if (session == null) return false;
		return session.isConnected();
	}

	/* (non-Javadoc)
	 * @see jy.jrtsp.common.RtspSession#setIdleTimeout(int)
	 */
	public void setIdleTimeout(int timeout) {
		// TODO Auto-generated method stub
		session.setIdleTime(IdleStatus.BOTH_IDLE, timeout);
	}
}
