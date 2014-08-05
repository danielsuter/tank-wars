package ch.tankwars.game;

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
