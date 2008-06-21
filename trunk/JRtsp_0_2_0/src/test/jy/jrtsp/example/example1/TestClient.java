package jy.jrtsp.example.example1;

import java.net.InetSocketAddress;

import jy.jrtsp.common.RtspConnection;
import jy.jrtsp.io.mina.MinaRtspConnection;

public class TestClient {

	public static void main(String[] args) {
		RtspConnection connection = new MinaRtspConnection();
		connection.setEventListener(new MyEventListener());
		connection.connect(new InetSocketAddress("218.24.6.115", 5555));
	}
}
