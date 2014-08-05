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
import ch.tankwars.game.PlayGround;
import ch.tankwars.game.Wall;
import ch.tankwars.transport.game.dto.JoinResponse;
import ch.tankwars.transport.game.mapper.ActorListDeserializer;

public class GameController {
	private static final long INTERVAL_MILIS = 100L;
	
	private final static Logger LOGGER = LoggerFactory.getLogger(GameController.class);
	
	private static Game game = new Game();
	private static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());
	private Timer timer;
	private static GameCommunicator gameCommunicator = new GameCommunicator();
	private final Map<Session, Tank> tanksMap = new HashMap<Session, Tank>();

	private boolean isStarted;

	public GameController() {
		game.setPlayGround(initPlayGround());
	}
	
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
				gameCommunicator.sendMessage(game.getActors(), peers, ActorListDeserializer.TYPE);
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
		
		JoinResponse joinResponse = new JoinResponse(spawnedTank.getId(), null);
		gameCommunicator.sendMessage(joinResponse, player);
	}

	private PlayGround initPlayGround() {
		PlayGround playGround = new PlayGround(Game.GAME_WIDTH, Game.GAME_HEIGHT);
		Wall wall = game.addWall(1, 1, 1, 1);
		playGround.addWall(wall); 
		return playGround;
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
