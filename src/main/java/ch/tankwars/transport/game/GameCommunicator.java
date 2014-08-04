package ch.tankwars.transport.game;

import java.io.IOException;
import java.util.Set;

import javax.websocket.Session;

import com.google.gson.Gson;

public class GameCommunicator {

	private final Gson gson = new Gson();

	public void sendMessage(Object objectToBroadcast, Set<Session> peers) {
		String gameAsJson = gson.toJson(objectToBroadcast);
		for (Session session : peers) {
			sendResponse(session, gameAsJson);
		}
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
