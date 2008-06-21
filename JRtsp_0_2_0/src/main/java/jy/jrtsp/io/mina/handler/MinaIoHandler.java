package jy.jrtsp.io.mina.handler;

import jy.jrtsp.common.RtspIoHandler;
import jy.jrtsp.common.RtspSession;
import jy.jrtsp.protocol.RtspRequest;
import jy.jrtsp.protocol.RtspResponse;

import org.apache.mina.common.IdleStatus;
import org.apache.mina.common.IoHandler;
import org.apache.mina.common.IoSession;

public class MinaIoHandler implements IoHandler {

	private RtspIoHandler handler;
	
	public MinaIoHandler(RtspIoHandler handler) {
		// TODO Auto-generated constructor stub
		this.handler = handler;
	}


	public void sessionCreated(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		session.suspendRead();
		RtspSession rtspSession = (RtspSession) session.getAttachment();
		handler.sessionCreated(rtspSession);
	}
	
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub
		RtspSession rtspSession = (RtspSession) session.getAttachment();
		handler.exceptionCaught(rtspSession, cause);
	}

	public void messageReceived(IoSession session, Object message) throws Exception {
		// TODO Auto-generated method stub
		RtspSession rtspSession = (RtspSession) session.getAttachment();
		if (message instanceof RtspResponse) {
			handler.responseMessageReceived(rtspSession, (RtspResponse) message);
		} else if (message instanceof RtspRequest) {
			handler.requestMessageReceived(rtspSession, (RtspRequest) message);
		}

	}

	public void messageSent(IoSession session, Object message) throws Exception {
		// TODO Auto-generated method stub
		RtspSession rtspSession = (RtspSession) session.getAttachment();
		if (message instanceof RtspResponse) {
			handler.responseMessageSent(rtspSession, (RtspResponse) message);
		} else if (message instanceof RtspRequest) {
			handler.requestMessageSent(rtspSession, (RtspRequest) message);
		}
	}

	public void sessionClosed(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		RtspSession rtspSession = (RtspSession) session.getAttachment();
		handler.sessionClosed(rtspSession);
	}

	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		// TODO Auto-generated method stub
		RtspSession rtspSession = (RtspSession) session.getAttachment();
		handler.sessionIdle(rtspSession);
	}

	public void sessionOpened(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		RtspSession rtspSession = (RtspSession) session.getAttachment();
		handler.sessionOpened(rtspSession);
	}

}
