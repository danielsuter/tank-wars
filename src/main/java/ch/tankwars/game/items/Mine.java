package ch.tankwars.game.items;

import ch.tankwars.game.Actor;
import ch.tankwars.game.ActorType;
import ch.tankwars.game.Referee;
import ch.tankwars.game.Tank;
import ch.tankwars.game.projectiles.Projectile;

public class Mine extends Actor {
	private int power = 25;
	private int owningTankId;
	private int health = 50;
	
	public Mine(int owningTankId) {
		super(ActorType.MINE);
		this.owningTankId = owningTankId;
		setHeight(25);
		setWidth(25);
	}

	@Override
	public void onCollision(Actor actor, Referee referee) {
		if(actor instanceof Projectile) {
			Projectile projectile = (Projectile) actor;
			health -= projectile.getPower();
			if(health <= 0) {
				setRemove(true);
			}
		} else if(actor instanceof Tank) {
			Tank tank = (Tank) actor;
			if(tank.getId() != owningTankId) {
				setRemove(true);
			}
		}
	}

	public int getPower() {
		return power;
	}
	
	public int getOwningTankId() {
		return owningTankId;
	}
}
