package ch.tankwars.game;

public class Tank extends Actor {

	private final static int DEFAULT_WIDTH = 25;
	private final static int DEFAULT_HEIGHT = 25;
	private static final int DEFAULT_SPEED = 5;

	private final String playerName;

	public Tank(ActorListener actorListener, String playerName) {
		super(actorListener, ActorType.TANK);
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
	
	@Override
	public void setDirection(Direction direction) {
		super.setDirection(direction);
		if(getVelocity() == 0) {
			setVelocity(DEFAULT_SPEED);
		}
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
		final Projectile projectile = new Projectile(getActorListener(), getId());
		// TODO beautify
		projectile.setPosition(this.getX() + (this.getWidth() / 2 ) - (projectile.getProjectileDimension() / 2), this.getY() + (this.getHeight() / 2) - (projectile.getProjectileDimension()  / 2));
		
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

	@Override
	public void collision(Actor actor) {
		if(actor instanceof Projectile) {
			Projectile projectile = (Projectile) actor;
			if(projectile.getId() != getId()) {
				System.out.println("got killed uaaarrrghh");
			}
		}
	}
}
