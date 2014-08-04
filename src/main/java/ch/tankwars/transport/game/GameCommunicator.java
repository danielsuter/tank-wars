package ch.tankwars.transport.game;

import java.io.IOException;
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
		peers.parallelStream().forEach(peer -> sendResponse(peer, gameAsJson));
		long responseTime = System.nanoTime();
		double durationMillis = (responseTime - startTime) / 1000000d;
		double durationJson = (jsonTime - startTime) / 1000000d;
		LOGGER.info("SEND TIME: {} JSON TIME: {}", durationMillis, durationJson);
	}

	public void sendMessage(Object objectToBroadcast, Session session) {
		sendResponse(session, responseMapper.map(objectToBroadcast));

	}
	
	private void sendResponse(Session session, String response) {
		try {
			session.getBasicRemote().sendText(response);
		} catch (IOException | IllegalStateException e) {
			LOGGER.error(e.toString(), e);
		}
	}

}
