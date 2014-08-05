package ch.tankwars.game;

public class Tank extends Actor {

	private static final int LASERGUN_SIZE = 2;
	private final static int DEFAULT_WIDTH = 35;
	private final static int DEFAULT_HEIGHT = 35;
	private final static int DEFAULT_SPEED = 6;
	private final static int DEFAULT_HEALTH = 100;
	
	private int fireRatePerSecond = 1;
	private Weapon weapon;

	private final String playerName;
	private int health = DEFAULT_HEALTH;
	private int hitsMade = 0;
	private int killsMade = 0;
	
	private ActorListener actorListener;
	
	public Tank(ActorListener actorListener, String playerName) {
		super(ActorType.TANK);
		this.actorListener = actorListener;
		this.playerName = playerName;
		setWidth(DEFAULT_WIDTH);
		setHeight(DEFAULT_HEIGHT);
		setVelocity(DEFAULT_SPEED);
		this.weapon = Weapon.STANDARD_CANON;
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
		// TODO strategy pattern
		Projectile projectile = null; 
		switch (weapon) {
			case STANDARD_CANON:
				projectile = new Projectile(getId());
				break;
			case LASER_GUN:
				// FIXME: Extract to own subclass
				projectile = new Projectile(getId()) {
					@Override
					public int getHeight() {
						return getDirection() == Direction.LEFT || getDirection() == Direction.RIGHT ? LASERGUN_SIZE : Weapon.LASER_GUN.getDimension();
					}
					
					@Override
					public int getWidth() {
						return getDirection() == Direction.DOWN || getDirection() == Direction.UP ? LASERGUN_SIZE : Weapon.LASER_GUN.getDimension();
					}
				};
				projectile.setProjectileDimension(LASERGUN_SIZE);
				projectile.setPower(Weapon.LASER_GUN.getPower());
				projectile.setVelocity(Weapon.LASER_GUN.getVelocity());
				break;
			case ROCKET_LAUNCHER:
				projectile = new Projectile(getId());
				projectile.setPower(Weapon.ROCKET_LAUNCHER.getPower());
				projectile.setProjectileDimension(Weapon.ROCKET_LAUNCHER.getDimension());
				projectile.setVelocity(Weapon.ROCKET_LAUNCHER.getVelocity());
				break;
		}
		projectile.setDirection(getDirection());
		// TODO beautify
		projectile.setPosition(this.getX() + (this.getWidth() / 2 ) - (projectile.getProjectileDimension() / 2), 
				this.getY() + (this.getHeight() / 2) - (projectile.getProjectileDimension()  / 2));
		
		actorListener.createActor(projectile);
	}

	@Override
	public void onCollision(Actor actor, Referee referee) {
		// TODO switch case
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
			prohibitCollision(actor);
		} else if (actor instanceof HealthPowerUp) {
			HealthPowerUp healthPowerUp = (HealthPowerUp) actor;
			increaseHealth(healthPowerUp);
		} else if (actor instanceof FireRatePowerUp) {
			FireRatePowerUp fireRatePowerUp = (FireRatePowerUp) actor;
			increaseFireRate(fireRatePowerUp);
		} else if (actor instanceof LaserGunPowerUp) {
			LaserGunPowerUp laserGunPowerUp = (LaserGunPowerUp) actor;
			setWeapon(laserGunPowerUp.getWeapon());
		} else if (actor instanceof RocketLauncherPowerUp) {
			RocketLauncherPowerUp rocketLauncherPowerUp = (RocketLauncherPowerUp) actor;
			setWeapon(rocketLauncherPowerUp.getWeapon());
		} else if(actor instanceof Tank) {
			prohibitCollision(actor);
		}
	}

	private void prohibitCollision(Actor actor) {
		switch(getDirection()) {
			case DOWN: 
				setY(actor.getY() - getHeight());
				break; 
			case UP:
				setY(actor.getY() + actor.getHeight());
				break;
			case RIGHT:
				setX(actor.getX() - getWidth());
				break;
			case LEFT:
				setX(actor.getX() + actor.getWidth());
				break; 
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

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}
}
