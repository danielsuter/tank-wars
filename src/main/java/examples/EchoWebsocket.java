package examples;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/echo")
public class EchoWebsocket {

	private static Set<Session> peers = Collections
			.synchronizedSet(new HashSet<Session>());

	@OnMessage
    public String echo(String message) {
		System.out.println("Received messsage from client: " + message);
        return message + " (from your server)";
    }

	@OnOpen
	public void onOpen(Session peer) {
		peers.add(peer);
	}

	@OnClose
	public void onClose(Session peer) {
		peers.remove(peer);
	}
}
