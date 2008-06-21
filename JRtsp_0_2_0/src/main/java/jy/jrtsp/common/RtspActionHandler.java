package jy.jrtsp.common;

import jy.jrtsp.action.RtspAction;
import jy.jrtsp.protocol.RtspRequest;
import jy.jrtsp.protocol.RtspResponse;

public interface RtspActionHandler {
	
	public static final String SESSION_KEY = "actionHandler";
	
	public void addActionListener(String key, RtspAction action);
	public RtspAction getActionListener(String key);
	public void removeActionListener(String key);
	public void clearActionListeners();
	public void execute(RtspSession session, RtspRequest message);
	public void execute(RtspSession session, RtspResponse message);
}
