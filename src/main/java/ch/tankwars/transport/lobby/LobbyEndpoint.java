package ch.tankwars.transport.lobby;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/lobby")
public class LobbyEndpoint {
	private static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());

	@OnMessage
	public void onMessage(String message, Session clientSession) {
		System.out.println("Received messsage from client: " + message);
		
		broadcastStringMessage(message);

	}

	private void broadcastStringMessage(String message) {
		for (Session session : peers) {
			sendStringMessage(session, message);
		}
	}

	private void sendStringMessage(Session session, String message) {
		try {
			session.getBasicRemote().sendText(message + " (from your server)");
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	@OnOpen
	public void onOpen(Session peer) {
		System.out.println("opening connection");
		peers.add(peer);
	}

	@OnClose
	public void onClose(Session peer) {
		System.out.println("closing connection");
		peers.remove(peer);
	}

	@OnError
	public void onError(Session session, Throwable throwable) {
		if (!isBrokenPipe(throwable)) {
			throwable.printStackTrace();
		}
	}

	/**
	 * Chrome causes a broken pipe error on connection close, we simply ignore
	 * this error. As the problem seems to be in the library code.
	 */
	private boolean isBrokenPipe(Throwable throwable) {
		if ("java.io.IOException: Broken pipe".equals(throwable.getMessage())) {
			System.out.println("Ignoring broken pipe");
			return true;
		} else if (throwable.getCause() != null) {
			return isBrokenPipe(throwable.getCause());
		} else {
			return false;
		}
	}	
}
