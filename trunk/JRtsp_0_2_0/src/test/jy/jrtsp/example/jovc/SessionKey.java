package jy.jrtsp.example.jovc;

import java.io.Serializable;

public class SessionKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 662710668231034601L;

	public final static String RTSP_SESSION_STRING = new SessionKey("session_string").toString();
	public final static String RTSP_CONTEXT = new SessionKey("context").toString();
	public final static String RTSP_LOCAL_ADDRESS = new SessionKey("local").toString();
	
	private final String key;
	
	public SessionKey (String key) {
		this.key = getClass().getName() + '.' + String.valueOf(key) + '@' +
        Integer.toHexString(System.identityHashCode(this));
	}
	
	public String toString() {
		return key;
	}
}
