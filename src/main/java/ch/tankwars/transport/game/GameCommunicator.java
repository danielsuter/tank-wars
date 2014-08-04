package ch.tankwars.transport.game;

import java.io.IOException;
import java.util.Set;

import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class GameCommunicator {
	private final static Logger LOGGER = LoggerFactory.getLogger(GameCommunicator.class);

	private final Gson gson;

	public GameCommunicator() {
		gson = GsonFactory.create();
	}
	
	public void sendMessage(Object objectToBroadcast, Set<Session> peers) {
		long startTime = System.nanoTime();
		String gameAsJson = gson.toJson(objectToBroadcast);
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
		sendResponse(session, gson.toJson(objectToBroadcast));
	}

	private void sendResponse(Session session, String response) {
		try {
			session.getBasicRemote().sendText(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
