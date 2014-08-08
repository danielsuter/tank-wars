package ch.tankwars.game.items;

import ch.tankwars.game.Actor;
import ch.tankwars.game.ActorType;
import ch.tankwars.game.Referee;

public class Mine extends Actor {
	private int power = 25;
	private int owningTankId;
	
	public Mine(int owningTankId) {
		super(ActorType.MINE);
		this.owningTankId = owningTankId;
		setHeight(25);
		setWidth(25);
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
	
	public int getOwningTankId() {
		return owningTankId;
	}
}
