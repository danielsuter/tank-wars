package ch.tankwars.game.powerup;

import ch.tankwars.game.Actor;
import ch.tankwars.game.ActorType;
import ch.tankwars.game.Referee;

public abstract class PowerUp extends Actor {
	
	public PowerUp(ActorType actorType) {
		super(actorType);
		setHeight(16);
		setWidth(16);
	}

	@Override
	public void onCollision(Actor actor, Referee referee) {
		setRemove(true);
	}

}
