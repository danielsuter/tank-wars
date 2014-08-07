package ch.tankwars.game;

import ch.tankwars.game.powerup.HealthPowerUp;
import ch.tankwars.game.powerup.LaserGunPowerUp;
import ch.tankwars.game.powerup.RocketLauncherPowerUp;
import ch.tankwars.game.powerup.WeaponData;

public class Tank extends Actor {

	private final static int DEFAULT_WIDTH = 35;
	private final static int DEFAULT_HEIGHT = 35;
	private final static int DEFAULT_SPEED = 8;
	private final static int MAX_HEALTH = 100;
	
	private WeaponData weaponData;

	private final String playerName;
	private int health = MAX_HEALTH;
	private int hitsMade = 0;
	private int killsMade = 0;
	
	private ActorListener actorListener;
	
	public Tank(ActorListener actorListener, String playerName) {
		super(ActorType.TANK);
		this.actorListener = actorListener;
		this.playerName = playerName;
		setWidth(DEFAULT_WIDTH);
		setHeight(DEFAULT_HEIGHT);
		setVelocity(0);
		this.weaponData = WeaponData.STANDARD_CANON;
	}
	
	public void move(Direction direction) {
		setDirection(direction);
	}	

	public String getPlayerName() {
		return playerName;
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
		Projectile projectile = weaponData.shoot(getId(), getDirection());
		
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
		health =  Math.min(MAX_HEALTH, health + healthPowerUp.getHealthGain());
	}

	public WeaponData getWeapon() {
		return weaponData;
	}

	public void setWeapon(WeaponData weaponData) {
		this.weaponData = weaponData;
	}
}
