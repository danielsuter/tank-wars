package ch.tankwars.transport.game;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.tankwars.game.Direction;
import ch.tankwars.game.Game;
import ch.tankwars.game.Tank;
import ch.tankwars.transport.game.dto.JoinResponse;

public class GameController {
	private static final long INTERVAL_MILIS = 100L;
	
	private final static Logger LOGGER = LoggerFactory.getLogger(GameController.class);
	
	private static Game game = new Game();
	private static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());
	private Timer timer;
	private static GameCommunicator gameCommunicator = new GameCommunicator();
	private final Map<Session, Tank> tanksMap = new HashMap<Session, Tank>();

	private boolean isStarted;

	public synchronized void start() {
		if(isStarted) {
			LOGGER.warn("Already started! Aborting...");
			return;
		}
		isStarted = true;
		LOGGER.info("game started");
		
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				double startTime = System.nanoTime();
				game.tick();
				double tickTime = System.nanoTime();
				gameCommunicator.sendMessage(game.getActors(), peers, GsonFactory.ACTOR_LIST_TYPE);
				double endTime = System.nanoTime();
				double durationMilis = (endTime - startTime) / 1000000d;
				double durationTick = (tickTime - startTime) / 1000000d;
				LOGGER.info("LOOP TIME: {} TICK TIME: {}", durationMilis, durationTick);
			}

		}, 0, INTERVAL_MILIS);
	}

	public void stop() {
		timer.cancel();
		isStarted = false;
	}

	public void join(Session player, String playerName) {
		peers.add(player);
		Tank spawnedTank = game.spawn(playerName);
		tanksMap.put(player, spawnedTank);
		
		JoinResponse joinResponse = new JoinResponse(spawnedTank.getId(), Game.GAME_WIDTH, Game.GAME_HEIGHT);
		gameCommunicator.sendMessage(joinResponse, player);
	}

	public void move(Session player, Direction direction) {
		tanksMap.get(player).move(direction);
	}
	
	public void moveStop(Session player, Direction direction) {
		tanksMap.get(player).moveStop(direction);
	}
	
	public void removePlayer(Session player) {
		game.removeActor(tanksMap.get(player));
		tanksMap.remove(player);
		peers.remove(player);
	}

	public void shoot(Session playerSession) {
		tanksMap.get(playerSession).shoot();
	}
}
