package ch.tankwars.transport.game;

import java.lang.reflect.Type;
import java.util.Set;

import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.tankwars.transport.game.mapper.ResponseMapper;

public class GameCommunicator {
	private final static Logger LOGGER = LoggerFactory.getLogger(GameCommunicator.class);

	private final ResponseMapper responseMapper;
	
	public GameCommunicator() {
		responseMapper = new ResponseMapper();
	}

	public void sendMessage(Object objectToBroadcast, Set<Session> peers, Type type) {
		long startTime = System.nanoTime();
		String gameAsJson = responseMapper.map(objectToBroadcast, type);
		long jsonTime = System.nanoTime();
		for (Session session : peers) {
			sendResponse(session, gameAsJson);
		}
		long responseTime = System.nanoTime();
		double durationMillis = (responseTime - startTime) / 1000000d;
		double durationJson = (jsonTime - startTime) / 1000000d;
		LOGGER.info("SEND TIME: {} JSON TIME: {}", durationMillis, durationJson);
	}

	public void sendMessage(Object objectToBroadcast, Session session) {
		sendResponse(session, responseMapper.map(objectToBroadcast));

	}
	
	// TODO sync futures
	private void sendResponse(Session session, String response) {
		try {
			session.getAsyncRemote().sendText(response);
		} catch (IllegalStateException e) {
			LOGGER.error(e.toString(), e);
		}
	}

}
