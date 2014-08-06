package ch.tankwars.game.items;

import ch.tankwars.game.Actor;
import ch.tankwars.game.ActorType;
import ch.tankwars.game.Referee;
import ch.tankwars.game.Tank;

public class Mine extends Actor {
	private int power = 25;
	
	public Mine() {
		super(ActorType.MINE);
		setHeight(25);
		setWidth(25);
	}

	@Override
	public void onCollision(Actor actor, Referee referee) {
		if(actor instanceof Tank) {
			setRemove(true);
		}
	}

	public int getPower() {
		return power;
	}
}
