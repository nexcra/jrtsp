package jy.jrtsp.common;

import java.net.SocketAddress;

import jy.jrtsp.protocol.RtspRequest;
import jy.jrtsp.protocol.RtspResponse;

public interface RtspIoHandler {

	public void setListener(RtspEventListener listener);
	public RtspEventListener getListener();
	public void connectFailed(SocketAddress address);
	public void sessionCreated(RtspSession session);
	public void sessionOpened(RtspSession session);
	public void sessionIdle(RtspSession session);
	public void requestMessageReceived(RtspSession session, RtspRequest message);
	public void responseMessageReceived(RtspSession session, RtspResponse message);
	public void requestMessageSent(RtspSession session, RtspRequest message);
	public void responseMessageSent(RtspSession session, RtspResponse message);
	public void sessionClosed(RtspSession session);
	public void exceptionCaught(RtspSession session, Throwable cause);

}
