package ch.tankwars.game;


public abstract class Obstacle extends Actor {
	
	public Obstacle(ActorListener actorListener, ActorType actorType) {
		super(actorListener, actorType);
	}
	
	@Override
	public void act() {
		throw new UnsupportedOperationException("Obstacles cannot act!");
	}
	
	@Override
	public void onCollision(Actor actor, Referee referee) {
		throw new UnsupportedOperationException("I came here to sit, not to collide.");
	}

}
