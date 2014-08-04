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
	private static final long INTERVAL_MILIS = 1000L;
	
	private final static Logger LOGGER = LoggerFactory.getLogger(GameController.class);
	
	private static Game game = new Game();
	private static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());
	private Timer timer;
	private static GameCommunicator gameCommunicator = new GameCommunicator();
	private final Map<Session, Tank> tanksMap = new HashMap<Session, Tank>();

	public void start() {
		LOGGER.info("game started");
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				game.tick();
				gameCommunicator.sendMessage(game.getActors(), peers);
			}

		}, 0, INTERVAL_MILIS);
	}

	public void stop() {
		timer.cancel();
	}

	public void join(Session player, String playerName) {
		peers.add(player);
		Tank spawnedTank = game.spawn(playerName);
		tanksMap.put(player, spawnedTank);
		
		JoinResponse joinResponse = new JoinResponse(spawnedTank.getPlayerId(), Game.GAME_WIDTH, Game.GAME_HEIGHT);
		gameCommunicator.sendMessage(joinResponse, player);
	}

	public void move(Session player, Direction direction) {
		tanksMap.get(player).move(direction);
	}
	
	public void moveStop(Session player, Direction direction) {
		tanksMap.get(player).moveStop(direction);
	}
	
	public void removePlayer(Session player) {
		game.remove(tanksMap.get(player));
		tanksMap.remove(player);
		peers.remove(player);
	}

	public void shoot(Session playerSession) {
		tanksMap.get(playerSession).shoot();
	}
}
