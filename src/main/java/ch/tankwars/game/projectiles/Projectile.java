package ch.tankwars.game.projectiles;

import java.awt.Rectangle;

import ch.tankwars.game.Actor;
import ch.tankwars.game.ActorType;
import ch.tankwars.game.BattlefieldMap;
import ch.tankwars.game.Referee;
import ch.tankwars.game.items.Mine;


public class Projectile extends Actor {
	
	public static final int DEFAULT_PROJECTILE_SPEED = 30;
	public static final int DEFAULT_PROJECTILE_DIMENSION = 3; 
	public static final int DEFAULT_PROJECTILE_POWER = 10; 
	private int owningTankId; 
	private int power;
	private BattlefieldMap battlefieldMap;
	
	@Override
	public Rectangle getBounds() {
		int deltaX = 0;
		int deltaY = 0;
		switch(getDirection()) {
			case DOWN: 
			case UP:
				deltaY = getVelocity();
				break;
			case RIGHT:
			case LEFT:
				deltaX = getVelocity();
				break; 
		}
		return new Rectangle(getX(), getY(), getWidth() + deltaX, getHeight() + deltaY);
	}
	
	public Projectile(int owningTankId, BattlefieldMap battlefieldMap) {
		super(ActorType.PROJECTILE);
		this.owningTankId = owningTankId;
		this.battlefieldMap = battlefieldMap;
		setHeight(DEFAULT_PROJECTILE_DIMENSION);
		setWidth(DEFAULT_PROJECTILE_DIMENSION);
		setVelocity(DEFAULT_PROJECTILE_SPEED);
		setPower(DEFAULT_PROJECTILE_POWER);
	}
	
	@Override
	public void act() {
		int newX = getX() + getDirection().calculateVelocityX(getVelocity());
		int newY = getY() + getDirection().calculateVelocityY(getVelocity());
		if(newX > battlefieldMap.getFieldWidth() - getWidth() || newX < 0) {
			this.setRemove(true);
			return;
		} 
		if(newY > battlefieldMap.getFieldHeight() - getHeight()  || newY < 0) {
			this.setRemove(true);
			return;
		}
		super.act();
	}
	
	public int getOwningTankId() {
		return owningTankId;
	}

	@Override
	public void onCollision(Actor actor, Referee referee) {
		if(actor.getId() != owningTankId && !(actor instanceof Mine)) {
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
