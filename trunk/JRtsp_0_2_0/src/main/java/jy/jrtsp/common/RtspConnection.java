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

public interface RtspConnection {

	/**
	 * 连接RTSP服务器
	 * @param address 服务器地址
	 */
	public void connect(SocketAddress address);
	
	/**
	 * 连接RTSP服务器,连接成功后调用listener.operationCompleted();
	 * @param address 
	 * @param listener 
	 */
	public void connect(final SocketAddress address, final ConnectListener listener);
	
	/**
	 * 连接RTSP服务器, 使用指定的<code>handler</code>
	 * @param address
	 * @param handler
	 */
	public void connect(final SocketAddress address, final RtspActionHandler handler);
	
	/**
	 * 连接RTSP服务器, 使用指定的<code>handler</code>, 
	 * 并在连接成功后调用listener.operationCompleted();
	 * @param address
	 * @param handler
	 * @param listener
	 */
	public void connect(final SocketAddress address, final RtspActionHandler handler, final ConnectListener listener);
	
	/**
	 * 设置连接的超时时间
	 * @param timeout 超时时长
	 */
	public void setConnectTimeout(int timeout);
	
	/**
	 * 设置事件监听器
	 * @param listener
	 */
	public void setEventListener(RtspEventListener listener);
	
	/**
	 * 关闭所有连接
	 */
	public void shutdown();
}
