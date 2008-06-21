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
package jy.jrtsp.io.mina;

import java.net.SocketAddress;
import java.util.Set;

import jy.jrtsp.common.ConnectListener;
import jy.jrtsp.common.DefaultRtspActionHandler;
import jy.jrtsp.common.DefaultRtspIoHandler;
import jy.jrtsp.common.RtspEventListener;
import jy.jrtsp.common.RtspActionHandler;
import jy.jrtsp.common.RtspConnection;
import jy.jrtsp.common.RtspIoHandler;
import jy.jrtsp.common.RtspSession;
import jy.jrtsp.common.RtspSessionImpl;
import jy.jrtsp.io.mina.codec.RtspCodecFactory;
import jy.jrtsp.io.mina.handler.MinaIoHandler;

import org.apache.mina.common.ConnectFuture;
import org.apache.mina.common.IoConnector;
import org.apache.mina.common.IoConnectorConfig;
import org.apache.mina.common.IoFuture;
import org.apache.mina.common.IoFutureListener;
import org.apache.mina.common.IoHandler;
import org.apache.mina.common.IoSession;
import org.apache.mina.common.RuntimeIOException;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.SocketConnector;

/**
 * @author Jiang Ying
 *
 */
public class MinaRtspConnection implements RtspConnection {

	private IoConnector connector;
	private IoHandler minaHandler;
	private RtspIoHandler rtspHandler;
	
	public MinaRtspConnection() {
		// TODO Auto-generated constructor stub
		connector = new SocketConnector();
		connector.getFilterChain().addLast("codec", 
				new ProtocolCodecFilter(new RtspCodecFactory()));
		
		rtspHandler = new DefaultRtspIoHandler(); 
		
		minaHandler = new MinaIoHandler(rtspHandler);
	}

	/* (non-Javadoc)
	 * @see jy.jrtsp.common.RtspConnection#connect(java.net.SocketAddress)
	 */
	public void connect(final SocketAddress address) {
		// TODO Auto-generated method stub
		connect(address, new DefaultRtspActionHandler(), null);
	}
	
	/* (non-Javadoc)
	 * @see jy.jrtsp.common.RtspConnection#connect(java.net.SocketAddress, jy.jrtsp.common.ConnectListener)
	 */
	public void connect(final SocketAddress address, 
			final ConnectListener listener) {
		connect(address, new DefaultRtspActionHandler(), listener);
	}
	
	/* (non-Javadoc)
	 * @see jy.jrtsp.common.RtspConnection#connect(java.net.SocketAddress, jy.jrtsp.common.RtspActionHandler)
	 */
	public void connect(final SocketAddress address, 
			final RtspActionHandler handler) {
		connect(address, handler, null);
	}
	
	/* (non-Javadoc)
	 * @see jy.jrtsp.common.RtspConnection#connect(java.net.SocketAddress, jy.jrtsp.common.RtspActionHandler, jy.jrtsp.common.ConnectListener)
	 */
	public void connect(final SocketAddress address, 
			final RtspActionHandler handler, final ConnectListener listener) {
		// TODO Auto-generated method stub
		ConnectFuture future = connector.connect(address, minaHandler);
		future.addListener(new IoFutureListener() {

			public void operationComplete(IoFuture future) {
				// TODO Auto-generated method stub
				try {
					future.getSession()
						.setAttribute(RtspActionHandler.SESSION_KEY, handler);
					RtspSession rtspSession = new RtspSessionImpl(future.getSession());
					future.getSession().setAttachment(rtspSession);
					if (listener != null) listener.operationCompleted(rtspSession);
					if (rtspHandler.getListener() != null) {
						rtspHandler.getListener().sessionOpened(rtspSession);
					}
					future.getSession().resumeRead();
				} catch (RuntimeIOException e) {
					rtspHandler.connectFailed(address);
				}
			}
			
		});
	}

	/* (non-Javadoc)
	 * @see jy.jrtsp.common.RtspConnection#setEventListener(jy.jrtsp.common.RtspEventListener)
	 */
	public void setEventListener(final RtspEventListener listener) {
		// TODO Auto-generated method stub
		rtspHandler.setListener(listener);
	}

	/* (non-Javadoc)
	 * @see jy.jrtsp.common.RtspConnection#setConnectTimeout(int)
	 */
	public void setConnectTimeout(final int timeout) {
		// TODO Auto-generated method stub
		((IoConnectorConfig) connector.getDefaultConfig()).setConnectTimeout(timeout);
	}

	/* (non-Javadoc)
	 * @see jy.jrtsp.common.RtspConnection#shutdown()
	 */
	public void shutdown() {
		// TODO Auto-generated method stub
		Set<SocketAddress> adrs = connector.getManagedServiceAddresses();
		for (SocketAddress adr : adrs) {
			for (IoSession session: connector.getManagedSessions(adr)) {
				if (session != null) {
					if (session.isConnected()) {
						session.close();
					}
				}
			}
		}
	}
	
}
