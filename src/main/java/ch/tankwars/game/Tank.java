package ch.tankwars.game;

import ch.tankwars.game.items.Mine;
import ch.tankwars.game.powerup.CurrentWeapon;
import ch.tankwars.game.powerup.HealthPowerUp;
import ch.tankwars.game.powerup.LaserGunPowerUp;
import ch.tankwars.game.powerup.MineBag;
import ch.tankwars.game.powerup.RocketLauncherPowerUp;
import ch.tankwars.game.powerup.WeaponData;
import ch.tankwars.game.projectiles.Projectile;

public class Tank extends Actor {

	private final static int DEFAULT_WIDTH = 35;
	private final static int DEFAULT_HEIGHT = 35;
	private final static int DEFAULT_SPEED = 10;
	private final static int MAX_HEALTH = 100;
	
	private CurrentWeapon currentWeapon;
	private Items items = new Items();
	
	private final String playerName;
	private int health = MAX_HEALTH;
	
	private int hitsMade = 0;
	private int killsMade = 0;
	private int mostRecentTankHit = -1;
	private int mostRecentTankKilled = -1;
	
	private ActorListener actorListener;
	private BattlefieldMap battlefieldMap;
	
	public Tank(ActorListener actorListener, String playerName, BattlefieldMap battlefieldMap) {
		super(ActorType.TANK);
		this.actorListener = actorListener;
		this.playerName = playerName;
		this.battlefieldMap = battlefieldMap;
		setWidth(DEFAULT_WIDTH);
		setHeight(DEFAULT_HEIGHT);
		setVelocity(0);
		this.currentWeapon = new CurrentWeapon();
	}
	
	public void move(Direction direction) {
		setDirection(direction);
	}	

	public String getPlayerName() {
		return playerName;
	}
	
	public void madeHit(int victimTankId) {
		hitsMade++;
		mostRecentTankHit = victimTankId;
	}
	
	public void madeKill(int victimTankId) {
		killsMade++;
		mostRecentTankKilled = victimTankId;
	}
	
	public int getHitsMade() {
		return hitsMade;
	}
	
	public int getKillsMade() {
		return killsMade;
	}
	
	public int getMostRecentTankHit() {
		return mostRecentTankHit;
	}
	
	public int getMostRecentTankKilled() {
		return mostRecentTankKilled;
	}
	
	@Override
	public Tank clone() throws CloneNotSupportedException {
		Tank clone = (Tank) super.clone();
		
		CurrentWeapon clonedCurrentWeapon = new CurrentWeapon();
		clonedCurrentWeapon.setWeapon(getWeaponData());
		
		clone.currentWeapon = clonedCurrentWeapon;
		return clone;
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
		
		if(newX > battlefieldMap.getFieldWidth() - getWidth()) {
			newX = battlefieldMap.getFieldWidth() - getWidth();
		} else if(newX < 0) {
			newX = 0;
		}
		
		if (newY > battlefieldMap.getFieldHeight() - getHeight()) {
			newY = battlefieldMap.getFieldHeight() - getHeight(); 
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
		Projectile projectile = currentWeapon.shoot(getId(), getDirection(), battlefieldMap);
		
		int projectileX = this.getX() + (this.getWidth() / 2 ) - (projectile.getProjectileDimension() / 2);
		int projectileY = this.getY() + (this.getHeight() / 2) - (projectile.getProjectileDimension()  / 2);
		projectile.setPosition(projectileX, projectileY);
		
		actorListener.createActor(projectile);
	}
	
	public void plantMine() {
		if(items.getMines() > 0) {
			items.removeMine();
			Mine mine = new Mine();
			mine.setPosition(getX(), getY());
			actorListener.createActor(mine);
		}
	}

	@Override
	public void onCollision(Actor actor, Referee referee) {
		// TODO switch case
		if(actor instanceof Projectile) {
			Projectile projectile = (Projectile) actor;
			if(projectile.getOwningTankId() != getId()) {
				damage(projectile.getPower());
				if (health <= 0) {
					referee.tankMadeKill(projectile.getOwningTankId(), getId());
				} else {
					referee.tankMadeHit(projectile.getOwningTankId(), getId());
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
			// We need to recheck, since the other tank could have already been moved
			if(collidesWith(actor)) {
				prohibitCollision(actor);
			}
		} else if(actor instanceof MineBag) {
			MineBag mineBag = (MineBag) actor;
			items.addMines(mineBag.getAmount());
		} else if(actor instanceof Mine) {
			Mine mine = (Mine) actor;
			damage(mine.getPower());
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

	public WeaponData getWeaponData() {
		return currentWeapon.getWeaponData();
	}

	public void setWeapon(WeaponData weaponData) {
		this.currentWeapon.setWeapon(weaponData);
	}
}
