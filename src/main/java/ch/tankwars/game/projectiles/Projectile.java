package ch.tankwars.game.projectiles;

import ch.tankwars.game.Actor;
import ch.tankwars.game.ActorType;
import ch.tankwars.game.Game;
import ch.tankwars.game.Referee;


public abstract class Projectile extends Actor {
	
	public static final int DEFAULT_PROJECTILE_SPEED = 10;
	public static final int DEFAULT_PROJECTILE_DIMENSION = 3; 
	public static final int DEFAULT_PROJECTILE_POWER = 10; 
	private int projectileDimension;
	private int owningTankId; 
	private int power;
	
	public Projectile(int owningTankId) {
		super(ActorType.PROJECTILE);
		this.owningTankId = owningTankId;
		setProjectileDimension(DEFAULT_PROJECTILE_DIMENSION);
		setVelocity(DEFAULT_PROJECTILE_SPEED);
		setPower(DEFAULT_PROJECTILE_POWER);
	}
	
	@Override
	public void act() {
		int newX = getX() + getDirection().calculateVelocityX(getVelocity());
		int newY = getY() + getDirection().calculateVelocityY(getVelocity());
		
		if(newX > Game.GAME_WIDTH - getProjectileDimension()  || newX < 0) {
			this.setRemove(true);
			return;
		} 
		if(newY > Game.GAME_HEIGHT - getProjectileDimension()  || newY < 0) {
			this.setRemove(true);
			return;
		}
		super.act();
	}
	
	public int getOwningTankId() {
		return owningTankId;
	}

	public int getProjectileDimension() {
		return projectileDimension;
	}
	
	public void setProjectileDimension(int dimension) {
		this.projectileDimension = dimension;
	}
	
	@Override
	public int getWidth() {
		return projectileDimension;
	}
	
	@Override
	public int getHeight() {
		return projectileDimension;
	}

	@Override
	public void onCollision(Actor actor, Referee referee) {
		if(actor.getId() != owningTankId) {
			setRemove(true);
		}
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}
}
