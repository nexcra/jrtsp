package jy.jrtsp.common;

import java.net.SocketAddress;

import jy.jrtsp.protocol.RtspRequest;
import jy.jrtsp.protocol.RtspResponse;

public class DefaultRtspIoHandler implements RtspIoHandler {
	
	private RtspEventListener listener= null;
	
	public DefaultRtspIoHandler() {
		// TODO Auto-generated constructor stub
		
	}

	public void setListener(RtspEventListener listener) {
		this.listener = listener;
	}

	public RtspEventListener getListener() {
		return listener;
	}
	
	public void sessionCreated(RtspSession session) {
		// TODO Auto-generated method stub
		
	}

	public void exceptionCaught(RtspSession session, Throwable cause) {
		// TODO Auto-generated method stub
		if (listener != null) {
			listener.exceptionCaught(session, cause);
		}
	}

	public void requestMessageReceived(RtspSession session, RtspRequest message) {
		// TODO Auto-generated method stub
		RtspActionHandler actionHandler = (RtspActionHandler) session.getAttribute(RtspActionHandler.SESSION_KEY);
		actionHandler.execute(session, message);
		if (listener != null) {
			listener.requestMessageReceived(session, message);
		}
	}

	public void requestMessageSent(RtspSession session, RtspRequest message) {
		// TODO Auto-generated method stub
		if (listener!= null) {
			listener.requestMessageSent(session, message);
		}
	}

	public void responseMessageReceived(RtspSession session,
			RtspResponse message) {
		// TODO Auto-generated method stub
		RtspActionHandler actionHandler = (RtspActionHandler) session.getAttribute(RtspActionHandler.SESSION_KEY);
		actionHandler.execute(session, message);
		if (listener != null) {
			listener.responseMessageReceived(session, message);
		}
	}

	public void responseMessageSent(RtspSession session, RtspResponse message) {
		// TODO Auto-generated method stub
		if (listener != null) {
			listener.responseMessageSent(session, message);
		}
	}

	public void sessionClosed(RtspSession session) {
		// TODO Auto-generated method stub
		if (listener != null) {
			listener.sessionClosed(session);
		}
	}
	
	public void sessionIdle(RtspSession session) {
		// TODO Auto-generated method stub
		if (listener != null) {
			listener.sessionIdle(session);
		}
	}

	public void sessionOpened(RtspSession session) {
		// TODO Auto-generated method stub

	}

	public void connectFailed(SocketAddress address) {
		// TODO Auto-generated method stub
		if (listener != null) {
			listener.connectFailed(address);
		}
	}

}
