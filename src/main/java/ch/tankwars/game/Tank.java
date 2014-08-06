package ch.tankwars.game;

public class Tank extends Actor {

	private final static int DEFAULT_WIDTH = 25;
	private final static int DEFAULT_HEIGHT = 25;
	private final static int DEFAULT_SPEED = 5;
	private final static int DEFAULT_HEALTH = 100;
	
	private int fireRatePerSecond = 1;

	private final String playerName;
	private int health = DEFAULT_HEALTH;
	private int hitsMade = 0;
	private int killsMade = 0;
	
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
	
	public void setFireRatePerSecond(int fireRatePerSecond) {
		this.fireRatePerSecond = fireRatePerSecond;
	}
	
	public int getFireRatePerSecond() {
		return fireRatePerSecond;
	}
	
	public void madeHit() {
		hitsMade++;
	}
	
	public void madeKill() {
		killsMade++;
	}
	
	public int getHitsMade() {
		return hitsMade;
	}
	
	public int getKillsMade() {
		return killsMade;
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
	public void onCollision(Actor actor, Referee referee) {
		if(actor instanceof Projectile) {
			Projectile projectile = (Projectile) actor;
			if(projectile.getOwningTankId() != getId()) {
				damage(projectile.getPower());
				
				if (health <= 0) {
					referee.tankMadeKill(projectile.getOwningTankId());
				} else {
					referee.tankMadeHit(projectile.getOwningTankId());
				}
			}
			
		} else if (actor instanceof Wall) {
			Wall wall = (Wall) actor;
			switch(getDirection()) {
				case DOWN: 
					setY(wall.getY() - getHeight());
					break; 
				case UP:
					setY(wall.getY() + wall.getHeight());
					break;
				case RIGHT:
					setX(wall.getX() - getWidth());
					break;
				case LEFT:
					setX(wall.getX() + wall.getWidth());
					break; 
			}
		} else if (actor instanceof HealthPowerUp) {
			HealthPowerUp healthPowerUp = (HealthPowerUp) actor;
			increaseHealth(healthPowerUp);
		} else if (actor instanceof FireRatePowerUp) {
			FireRatePowerUp fireRatePowerUp = (FireRatePowerUp) actor;
			increaseFireRate(fireRatePowerUp);
		}
	}

	public int getHealth() {
		return health;
	}
	
	public void damage(int amount) {
		health -= amount;
		if(health <= 0) {
			setRemove(true);
		}
	}
	
	private void increaseHealth(HealthPowerUp healthPowerUp) {
		health =  Math.min(DEFAULT_HEALTH, health + healthPowerUp.getHealthGain());
	}
	private void increaseFireRate(FireRatePowerUp fireRatePowerUp) {
		fireRatePerSecond += fireRatePowerUp.getFireRateGain();
	}
}
