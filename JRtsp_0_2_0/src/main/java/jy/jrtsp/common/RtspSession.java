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

import jy.jrtsp.action.RtspAction;
import jy.jrtsp.protocol.RtspMessage;

public interface RtspSession {

	/**
	 * 发送RTSP消息
	 * @param message RTSP消息
	 */
	public void write(RtspMessage message);
	
	/**
	 * 发送RTSP消息，并添加RtspAction
	 * @param message
	 * @param action
	 */
	public void write(RtspMessage message, RtspAction action);
		
	/**
	 * 关闭RTSP连接
	 */
	public void close();
	
	/**
	 * 为当前Session设置一个私有的属性
	 * @param key
	 * @param value
	 */
	public void setAttribute(String key, Object value);
	
	/**
	 * 获取当前Session的一个属性
	 * @param key
	 * @return
	 */
	public Object getAttribute(String key);
	
	/**
	 * 删除当前Session的一个属性
	 * @param key
	 */
	public void removeAttribute(String key);
	
	/**
	 * 清空当前Session的所有属性
	 */
	public void clearAttributes();

	/**
	 * 当前Session是否已连接
	 * @return
	 */
	public boolean isConnected();
	
	/**
	 * 设置Session的空闲周期，以毫秒为单位。
	 * 当空闲的时长达到设定的周期时，将会引发RtspEventListener.sessionIdle(RtspSession session)
	 * @param timeout
	 */
	public void setIdleTimeout(int timeout);
	
	/**
	 * 获取当前Session的RtspActionHandler;
	 * @return
	 */
	public RtspActionHandler getActionHandler();
}
