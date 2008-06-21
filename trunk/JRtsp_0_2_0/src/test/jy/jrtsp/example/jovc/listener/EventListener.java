package jy.jrtsp.example.jovc.listener;

import java.net.SocketAddress;

import jy.jrtsp.common.RtspEventListener;
import jy.jrtsp.common.RtspSession;
import jy.jrtsp.example.jovc.CoshipClientSupport;
import jy.jrtsp.example.jovc.MainFrame;
import jy.jrtsp.protocol.RtspRequest;
import jy.jrtsp.protocol.RtspResponse;

public class EventListener implements RtspEventListener {

	private MainFrame ui;
	private CoshipClientSupport support;
	
	public EventListener(MainFrame ui, CoshipClientSupport support) {
		// TODO Auto-generated constructor stub
		this.ui = ui;
		this.support = support;
	}

	public void connectFailed(SocketAddress address) {
		// TODO Auto-generated method stub
		ui.log("Connect Failed!");
		ui.showButton(true, false, false, false, false);
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

	}

	public void sessionIdle(RtspSession session) {
		// TODO Auto-generated method stub
		support.heartBeat(session, null);
	}

	public void sessionOpened(RtspSession session) {
		// TODO Auto-generated method stub
		session.setIdleTimeout(30);
	}

}
