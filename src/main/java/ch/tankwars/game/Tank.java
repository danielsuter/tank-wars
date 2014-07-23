package ch.tankwars.game;

public class Tank extends Actor {

	public final static int DEFAULT_WIDTH = 10;
	public final static int DEFAULT_HEIGHT = 10;

	private String playerName;
	private int speed = 5;

	public Tank(String playerName) {
		this.playerName = playerName;
		setWidth(DEFAULT_WIDTH);
		setHeight(DEFAULT_HEIGHT);
	}

	public void move(Direction direction) {
		switch (direction) {
		case DOWN:
			setVelocityX(0);
			setVelociyY(speed);
			break;
		case UP:
			setVelocityX(0);
			setVelociyY(-speed);
			break;
		case LEFT:
			setVelocityX(-speed);
			setVelociyY(0);
			break;
		case RIGHT:
			setVelocityX(speed);
			setVelociyY(0);
			break;
		default:
			throw new RuntimeException("Cannot handle direction: " + direction);
		}
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
}
