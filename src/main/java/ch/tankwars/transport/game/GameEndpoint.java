package ch.tankwars.transport.game;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.tankwars.game.Direction;

/**
 * One endpoint will be created per session.
 *
 */
@ServerEndpoint("/game")
public class GameEndpoint {

	private final static Logger LOGGER = LoggerFactory.getLogger(GameEndpoint.class);
	private final static GameController gameController = new GameController();

	private Session playerSession;

	@OnMessage
	public void onMessage(String message, Session clientSession) {
		LOGGER.debug("Received message: {} ", message);
		String[] fullCommand = message.split(" ");
		String command = fullCommand[0];

		switch (command) {
		case "JOIN":
			final String playerName = fullCommand[1];
			gameController.join(playerSession, playerName);
			break;
		case "START":
			gameController.start();
			break;
		case "STOP":
			gameController.stop();
			break;
		case "MOVE":
			String directionAsString = fullCommand[1];
			Direction direction = Direction.valueOf(directionAsString);
			gameController.move(playerSession, direction);
			break;
		case "MOVESTOP":
			directionAsString = fullCommand[1];
			direction = Direction.valueOf(directionAsString);
			gameController.moveStop(playerSession, direction);
			break;
		case "SHOOT":
			gameController.shoot(playerSession);
			break;
		default:
			throw new RuntimeException("Cannot handle command: " + command);
		}
	}

	@OnOpen
	public void onOpen(Session peer) {
		playerSession = peer;
	}

	@OnClose
	public void onClose(Session peer) {
		gameController.removePlayer(peer);
	}

	@OnError
	public void onError(Session session, Throwable throwable) {
		if (!isBrokenPipe(throwable)) {
			throwable.printStackTrace();
		}
	}

	/**
	 * Chrome causes a broken pipe error on connection close, we simply ignore
	 * this error. As the problem seems to be in the library code.
	 */
	private boolean isBrokenPipe(Throwable throwable) {
		if ("java.io.IOException: Broken pipe".equals(throwable.getMessage())) {
			LOGGER.warn("Ignoring broken pipe");
			return true;
		} else if (throwable.getCause() != null) {
			return isBrokenPipe(throwable.getCause());
		} else {
			return false;
		}
	}
}
