/**
 * 
 */
package heye.rtsp.protocol;

import org.apache.mina.common.ByteBuffer;

/**
 * @author Jiang Ying
 *
 */
public interface RTSPMessage {

	public ByteBuffer toByteBuffer();

}
