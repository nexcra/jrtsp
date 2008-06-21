package jy.jrtsp.protocol;

import org.apache.mina.common.ByteBuffer;

public interface RtspMessage {
	public static final String VERSION_RTSP_1_0 = "RTSP/1.0";
	public static final String VERSION_RTSP_1_1 = "RTSP/1.1";
	public ByteBuffer toByteBuffer();
	public void setHeader(String name, String value);
	public void setBody(byte[] body);
}
