package jy.jrtsp.example.example1;

import java.net.SocketAddress;

import jy.jrtsp.common.RtspEventListener;
import jy.jrtsp.common.RtspSession;
import jy.jrtsp.protocol.RtspRequest;
import jy.jrtsp.protocol.RtspResponse;

public class MyEventListener implements RtspEventListener {

	public MyEventListener() {
		// TODO Auto-generated constructor stub
	}

	public void connectFailed(SocketAddress address) {
		// TODO Auto-generated method stub
		System.out.println("connect failed");
	}

	public void exceptionCaught(RtspSession session, Throwable cause) {
		// TODO Auto-generated method stub
		cause.printStackTrace();
	}

	public void requestMessageReceived(RtspSession session, RtspRequest message) {
		// TODO Auto-generated method stub

	}

	public void requestMessageSent(RtspSession session, RtspRequest message) {
		// TODO Auto-generated method stub

	}

	public void responseMessageReceived(RtspSession session,
			RtspResponse message) {
		// TODO Auto-generated method stub

	}

	public void responseMessageSent(RtspSession session, RtspResponse message) {
		// TODO Auto-generated method stub

	}

	public void sessionClosed(RtspSession session) {
		// TODO Auto-generated method stub
		System.out.println("closed");

	}

	public void sessionCreated(RtspSession session) {
		// TODO Auto-generated method stub
		System.out.println("connect successful");
		session.close();
	}

	public void sessionIdle(RtspSession session) {
		// TODO Auto-generated method stub

	}

	public void sessionOpened(RtspSession session) {
		// TODO Auto-generated method stub
		session.close();
	}


}
