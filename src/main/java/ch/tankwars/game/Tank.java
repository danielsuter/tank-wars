package ch.tankwars.game;

public class Tank extends Actor {

	private static final int DEFAULT_PROJECTILE_SPEED = 10;
	public final static int DEFAULT_WIDTH = 10;
	public final static int DEFAULT_HEIGHT = 10;

	private final String playerName;
	private int speed = 5;

	public Tank(ActorListener actorListener, String playerName, int id) {
		super(actorListener, ActorType.TANK, id);
		this.playerName = playerName;
		setWidth(DEFAULT_WIDTH);
		setHeight(DEFAULT_HEIGHT);
	}
	
	public void move(Direction direction) {
		setDirection(direction);
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

	@Override
	public void act() {
		int newX = getX() + getDirection().calculateVelocityX(getVelocity());
		int newY = getY() + getDirection().calculateVelocityY(getVelocity());
		
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
		setVelocity(0);
	}
	
	public void shoot() {
		Projectile projectile = new Projectile(getActorListener(), 1); // TODO does not work, game needs to create ids
		// TODO beautify
		projectile.setPosition(getX() + (getWidth() / 2) - 1, getY() - (getHeight() / 2) - 1);
		projectile.setWidth(3);
		projectile.setHeight(3);
		projectile.setVelocity(DEFAULT_PROJECTILE_SPEED);
		
		switch(getDirection()) {
		case DOWN:
			projectile.setDirection(getDirection());
			break;
		case UP:
			projectile.setDirection(getDirection());
			break;
		case LEFT:
			projectile.setDirection(getDirection());
			break;
		case RIGHT:
			projectile.setDirection(getDirection());
			break;
		}
		getActorListener().createActor(projectile);
	}
}
