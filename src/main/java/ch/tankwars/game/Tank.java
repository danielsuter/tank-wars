package ch.tankwars.game;

public class Tank extends Actor {

	private static final int DEFAULT_PROJECTILE_SPEED = 10;
	public final static int DEFAULT_WIDTH = 25;
	public final static int DEFAULT_HEIGHT = 25;

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
	
	@Override
	public void act() {
		int newX = getX() + getVelocityX();
		int newY = getY() + getVelocityY();
		
		if(newX > Game.GAME_WIDTH - getWidth()) {
			newX = Game.GAME_WIDTH - getWidth();
		} else if(newX < 0) {
			newX = 0;
		}
		
		if (newY > Game.GAME_HEIGHT - getHeight()) {
			newY = Game.GAME_HEIGHT - getHeight(); 
		} else if(newY < 0) {
			newY = 0;
		}
		
		setX(newX);
		setY(newY);
	}

	public void moveStop(Direction direction) {
		// TODO is the direction really necessary?
		setVelocity(0, 0);
	}
	
	public void shoot() {
		Projectile projectile = new Projectile(getActorListener(), this.playerId);
		// TODO beautify
		projectile.setPosition(getX() + (getWidth() / 2) - 1, getY() - (getHeight() / 2) - 1);
		projectile.setWidth(3);
		projectile.setHeight(3);
		
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
		getActorListener().createActor(projectile);
	}
}
