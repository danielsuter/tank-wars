package ch.tankwars.game;

public class Tank extends Actor {

	private static final int DEFAULT_PROJECTILE_SPEED = 10;
	public final static int DEFAULT_WIDTH = 10;
	public final static int DEFAULT_HEIGHT = 10;

	private final String playerName;
	private final String playerId; 
	private int speed = 5;

	public Tank(ActorListener actorListener, String playerName, String playerId) {
		super(actorListener, ActorType.TANK);
		this.playerName = playerName;
		this.playerId = playerId;
		setWidth(DEFAULT_WIDTH);
		setHeight(DEFAULT_HEIGHT);
	}

	public void move(Direction direction) {
		setDirection(direction);
		
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
	
	public void shoot() {
		Projectile projectile = new Projectile(getActorListener());
		switch(getDirection()) {
		case DOWN:
			projectile.setVelocity(0, DEFAULT_PROJECTILE_SPEED);
			break;
		case UP:
			projectile.setVelocity(0, -DEFAULT_PROJECTILE_SPEED);
			break;
		case LEFT:
			projectile.setVelocity(-DEFAULT_PROJECTILE_SPEED, 0);
			break;
		case RIGHT:
			projectile.setVelocity(DEFAULT_PROJECTILE_SPEED, 0);
			break;
		}
		
	}
}
