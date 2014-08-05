package ch.tankwars.transport.game;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.tankwars.game.Direction;
import ch.tankwars.game.FireRatePowerUp;
import ch.tankwars.game.Game;
import ch.tankwars.game.HealthPowerUp;
import ch.tankwars.game.PlayGround;
import ch.tankwars.game.Tank;
import ch.tankwars.performance.PerformanceCounter;
import ch.tankwars.transport.game.dto.JoinResponse;
import ch.tankwars.transport.game.dto.PlayersChangedResponse;
import ch.tankwars.transport.game.mapper.ActorListDeserializer;

public class GameController {
	private static final long INTERVAL_MILIS = 50L;
	
	private final static Logger LOGGER = LoggerFactory.getLogger(GameController.class);
	
	private static Game game = new Game();
	private Timer timer;
	private static GameCommunicator gameCommunicator = new GameCommunicator();
	
	private final Set<PlayerPeer> playerPeers = Collections.synchronizedSet(new HashSet<PlayerPeer>());
	
	private boolean isStarted;
	
	private PerformanceCounter perf = new PerformanceCounter(10);
	
	public GameController() {
		initPlayground();
		addHealthPowerUp(200, 200);
		addHealthPowerUp(400, 300);
		addFireRatePowerUp(100, 100);
		addFireRatePowerUp(500, 580);
	}

	private void initPlayground() {
		PlayGround playGround = new PlayGround(Game.GAME_WIDTH, Game.GAME_HEIGHT);
		game.setPlayGround(playGround);
		game.addWall(5, 5, 20, 100);
		game.addWall(50, 89, 200, 10);
		game.addWall(600, 411, 50, 50);
		game.addWall(555, 44, 80, 20);
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
				perf.start();
				
				game.tick();
				
				perf.lap("tick");
				
				gameCommunicator.sendMessage(game.getActors(), playerPeers, ActorListDeserializer.TYPE);
				
				perf.stop("COMPLETE LOOP");
			}

		}, 0, INTERVAL_MILIS);
	}

	public void stop() {
		timer.cancel();
		isStarted = false;
	}

	public PlayerPeer join(Session player, String playerName) {
		PlayerPeer playerPeer = new PlayerPeer(player, playerName);
		playerPeers.add(playerPeer);
		
		Tank spawnedTank = game.spawn(playerName);
		playerPeer.setTank(spawnedTank);
		
		JoinResponse joinResponse = new JoinResponse(spawnedTank.getId(), game.getPlayGround());
		gameCommunicator.sendMessage(joinResponse, playerPeer);
		updatePlayers();
		return playerPeer;
	}
	
	public void updatePlayers() {
		PlayersChangedResponse response = new PlayersChangedResponse(playerPeers);
		gameCommunicator.sendMessage(response, playerPeers);
	}

	public void move(PlayerPeer playerPeer, Direction direction) {
		Tank tank = playerPeer.getTank();
		if(tank != null) {
			tank.move(direction);
		}
	}
	
	public void moveStop(PlayerPeer playerPeer, Direction direction) {
		Tank tank = playerPeer.getTank();
		if(tank != null) {
			tank.moveStop(direction);
		}
	}
	
	public void removePlayer(PlayerPeer playerPeer) {
		Tank tank = playerPeer.getTank();
		if(tank != null) {
			tank.setRemove(true);
		}
		playerPeers.remove(playerPeer);
	}
	
	public void addHealthPowerUp(int x, int y) {
		game.createActor(new HealthPowerUp(game, x, y));
	}
	
	public void addFireRatePowerUp(int x, int y) {
		game.createActor(new FireRatePowerUp(game, x, y));
	}

	public void shoot(PlayerPeer playerPeer) {
		Tank tank = playerPeer.getTank();
		if(tank != null) {
			tank.shoot();
		}
	}

	public void clear() {
		game = new Game();
		timer.cancel();
		isStarted = false;
		playerPeers.clear();
		initPlayground();
	}
}
