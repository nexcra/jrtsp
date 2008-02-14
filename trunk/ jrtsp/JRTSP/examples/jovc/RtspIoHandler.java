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

import org.apache.mina.common.IdleStatus;

/**
 * @author Jiang Ying
 * 2008-1-25 ÏÂÎç04:20:04
 */
public class RtspIoHandler implements heye.rtsp.net.RtspIoHandler {

	CoshipClientSupport client;
	
	/**
	 * 
	 */
	public RtspIoHandler(CoshipClientSupport client) {
		// TODO Auto-generated constructor stub
		this.client = client;
	}

	/* (non-Javadoc)
	 * @see heye.rtsp.net.RtspIoHandler#exceptionCaught(java.lang.Throwable)
	 */
	public void exceptionCaught(Throwable cause) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see heye.rtsp.net.RtspIoHandler#onClosed()
	 */
	public void onClosed() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see heye.rtsp.net.RtspIoHandler#onCreated()
	 */
	public void onCreated() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see heye.rtsp.net.RtspIoHandler#onIdle(org.apache.mina.common.IdleStatus)
	 */
	public void onIdle(IdleStatus status) {
		// TODO Auto-generated method stub
		client.heartBeat(null);
	}

	/* (non-Javadoc)
	 * @see heye.rtsp.net.RtspIoHandler#onOpened()
	 */
	public void onOpened() {
		// TODO Auto-generated method stub

	}

}
