package examples;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ServerEndpoint("/echo")
public class EchoWebsocket {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(EchoWebsocket.class);
	
	private static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());

	@OnMessage
	public void echo(String message, Session session) {
		LOGGER.debug("Received messsage from client: {}", message);
		try {
			session.getBasicRemote().sendText(message + " (from your server)");
		} catch (IOException e) {
			LOGGER.error(e.toString(), e);
		}
	}

	@OnOpen
	public void onOpen(Session peer) {
		LOGGER.debug("opening connection");
		peers.add(peer);
	}

	@OnClose
	public void onClose(Session peer) {
		LOGGER.debug("closing connection");
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
			LOGGER.info("Ignoring broken pipe");
			return true;
		} else if (throwable.getCause() != null) {
			return isBrokenPipe(throwable.getCause());
		} else {
			return false;
		}
	}
}
