package jy.jrtsp.io.mina.codec;

import jy.jrtsp.protocol.RtspMessage;

import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class RtspMessageEncoder extends ProtocolEncoderAdapter {

	public RtspMessageEncoder() {
		// TODO Auto-generated constructor stub
	}

	public void encode(IoSession session, Object message, ProtocolEncoderOutput out)
			throws Exception {
		// TODO Auto-generated method stub
		if (message instanceof RtspMessage) {
			out.write(((RtspMessage)message).toByteBuffer());
		}
	}

}
