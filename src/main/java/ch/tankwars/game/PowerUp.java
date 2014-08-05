package ch.tankwars.game;

public abstract class PowerUp extends Actor {

	public PowerUp(ActorListener actorListener, ActorType actorType) {
		super(actorListener, actorType);
		setHeight(10);
		setWidth(10);
	}

	@Override
	public void collision(Actor actor) {
		setRemove(true);
	}

}
