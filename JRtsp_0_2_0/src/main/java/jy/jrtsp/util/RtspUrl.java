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

package jy.jrtsp.util;


import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Jiang Ying
 *
 */
public class RtspUrl {
	
	private URI rtspUrl;
	
	public RtspUrl(String host, int port, String file) throws URISyntaxException {

		String asset = file.startsWith("/") ? file : "/".concat(file);

		rtspUrl = new URI("rtsp",null,host,port,asset,null,null);

	}
	
	public RtspUrl(String url) throws URISyntaxException {
		rtspUrl = new URI(url);
	}
	
	public String toString() {
		return rtspUrl == null ? null : rtspUrl.toString();
	}
	
	public String getHost() {
		return rtspUrl.getHost();
	}
	
	public int getPort() {
		return rtspUrl.getPort();
	}
	
	public String getAsset() {
		return rtspUrl.getPath();
	}

	/**
	 * @param args
	 * @throws URISyntaxException 
	 */
	public static void main(String[] args) throws URISyntaxException {
		// TODO Auto-generated method stub
		RtspUrl url = new RtspUrl("10.10.10.1",5555,"/wuji");
		System.out.println(url.toString());
	}

}
