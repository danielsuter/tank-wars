package ch.tankwars.transport.game;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.websocket.Session;

import ch.tankwars.game.Game;

import com.google.gson.Gson;

public class GameLoop {
	private static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());

	private static final long INTERVAL_MILIS = 1000L;
	
	private Game game;
	private Timer timer;
	private Gson gson = new Gson();

	public GameLoop(Game game) {
		this.game = game;
	}
	
	public void start() {
		System.out.println("starting loop...");
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				game.tick();
				broadcastState();
			}


		}, 0, INTERVAL_MILIS);
	}
	
	private void broadcastState() {
		String gameAsJson = gson.toJson(game);
		
		for (Session session : peers) {
			sendResponse(session, gameAsJson);
		}		
	}
	

	private void sendResponse(Session session, String response) {
		try {
			session.getBasicRemote().sendText(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void stop() {
		timer.cancel();
	}
	
	public void registerListener(Session playerSession) {
		peers.add(playerSession);
	}
	
	public void removeListener(Session playerSession) {
		peers.remove(playerSession);
	}
}
