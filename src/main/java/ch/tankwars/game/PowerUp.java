package ch.tankwars.game;

public abstract class PowerUp extends Actor {

	public PowerUp(ActorListener actorListener, ActorType actorType) {
		super(actorListener, actorType);
		setHeight(16);
		setWidth(16);
	}

	@Override
	public void collision(Actor actor) {
		setRemove(true);
	}

}
