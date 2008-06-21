package jy.jrtsp.action;

import jy.jrtsp.common.RtspSession;
import jy.jrtsp.protocol.RtspMessage;

/**
 * @author Jiang Ying
 * Date: 2008-4-27 обнГ08:49:11
 */
public interface RtspAction {

	public final static int OK = 0;
	public final static int RUNNING = 1;
	public final static int NOK = 2;
	
	/**
	 * @param session
	 * @param message
	 * @return 
	 */
	public int execute(RtspSession session, RtspMessage message);
}
