package jy.jrtsp.common;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import jy.jrtsp.action.RtspAction;
import jy.jrtsp.protocol.RtspRequest;
import jy.jrtsp.protocol.RtspResponse;

public class DefaultRtspActionHandler implements RtspActionHandler {

	private Map<String, RtspAction> actions = Collections.synchronizedMap(new HashMap<String, RtspAction>());
	
	public DefaultRtspActionHandler() {
		// TODO Auto-generated constructor stub
	}

	public void addActionListener(String key, RtspAction action) {
		// TODO Auto-generated method stub
		actions.put(key, action);
	}

	public void clearActionListeners() {
		// TODO Auto-generated method stub
		actions.clear();
	}

	public void execute(RtspSession session, RtspRequest message) {
		// TODO Auto-generated method stub
		String method = message.getMethod();
		RtspAction action = getActionListener(method);
		if (action != null) {
			int r = action.execute(session, message);
			switch (r) {
				case RtspAction.OK:
				case RtspAction.NOK:
					removeActionListener(method);
					break;
				case RtspAction.RUNNING:
					break;
			}
		}
	}

	public void execute(RtspSession session, RtspResponse message) {
		// TODO Auto-generated method stub
		String seq = message.getCSeq();
		RtspAction action = getActionListener(seq);
		if (action != null) {
			int r = action.execute(session, message);
			switch (r) {
				case RtspAction.OK:
				case RtspAction.NOK:
					removeActionListener(seq);
					break;
				case RtspAction.RUNNING:
					break;
			}
		}
	}

	public RtspAction getActionListener(String key) {
		// TODO Auto-generated method stub
		return actions.get(key);
	}

	public void removeActionListener(String key) {
		// TODO Auto-generated method stub
		actions.remove(key);
	}

}
