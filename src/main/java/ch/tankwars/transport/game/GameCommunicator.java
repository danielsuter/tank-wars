package ch.tankwars.transport.game;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Set;

import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.tankwars.performance.PerformanceCounter;
import ch.tankwars.transport.game.mapper.ResponseMapper;

public class GameCommunicator {
	private final static Logger LOGGER = LoggerFactory.getLogger(GameCommunicator.class);

	private final ResponseMapper responseMapper;
	
	private PerformanceCounter perf = new PerformanceCounter(10);
	
	public GameCommunicator() {
		responseMapper = new ResponseMapper();
	}

	public void sendMessage(Object objectToBroadcast, Set<Session> peers, Type type) {
		perf.start();
		
		String gameAsJson = responseMapper.map(objectToBroadcast, type);
		
		perf.lap("JSON TIME");
		
		peers.parallelStream().forEach(peer -> sendResponse(peer, gameAsJson));
		
		perf.stop("COMPLETE TIME ({0,number} characters)", gameAsJson.length());
	}

	public void sendMessage(Object objectToBroadcast, Session session) {
		sendResponse(session, responseMapper.map(objectToBroadcast));
	}
	
	public void sendMessage(Object objectToBroadcast, Set<Session> peers) {
		peers.parallelStream().forEach(peer -> sendResponse(peer, responseMapper.map(objectToBroadcast)));
	}
	
	private void sendResponse(Session session, String response) {
		try {
			session.getBasicRemote().sendText(response);
		} catch (IOException | IllegalStateException e) {
			LOGGER.error(e.toString(), e);
		}
	}

}
