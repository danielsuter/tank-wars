package ch.tankwars.game;

public class Tank extends Actor {

	public final static int DEFAULT_WIDTH = 10;
	public final static int DEFAULT_HEIGHT = 10;

	private final String playerName;
	private final String playerId; 
	private int speed = 5;

	public Tank(String playerName, String playerId) {
		this.playerName = playerName;
		this.playerId = playerId;
		setWidth(DEFAULT_WIDTH);
		setHeight(DEFAULT_HEIGHT);
	}

	public void move(Direction direction) {
		switch (direction) {
		case DOWN:
			setVelocityX(0);
			setVelocityY(speed);
			break;
		case UP:
			setVelocityX(0);
			setVelocityY(-speed);
			break;
		case LEFT:
			setVelocityX(-speed);
			setVelocityY(0);
			break;
		case RIGHT:
			setVelocityX(speed);
			setVelocityY(0);
			break;
		default:
			throw new RuntimeException("Cannot handle direction: " + direction);
		}
	}

	public String getPlayerName() {
		return playerName;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public String getPlayerId() {
		return playerId;
	}

	public void moveStop(Direction direction) {
		// TODO is the direction really necessary?
		setVelocity(0, 0);
	}
}
