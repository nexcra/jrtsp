package jy.jrtsp.io.mina.codec;

import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class RtspCodecFactory implements ProtocolCodecFactory {

	private final RtspMessageDecoder decoder;
	private final RtspMessageEncoder encoder;
	
	public RtspCodecFactory() {
		// TODO Auto-generated constructor stub
		encoder = new RtspMessageEncoder();
		decoder = new RtspMessageDecoder();
	}

	public ProtocolDecoder getDecoder() throws Exception {
		// TODO Auto-generated method stub
		return decoder;
	}

	public ProtocolEncoder getEncoder() throws Exception {
		// TODO Auto-generated method stub
		return encoder;
	}

}
