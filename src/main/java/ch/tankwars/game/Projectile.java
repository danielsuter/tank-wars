package ch.tankwars.game;

public class Projectile extends Actor{

	public Projectile(ActorListener actorListener) {
		super(actorListener, ActorType.PROJECTILE);
	}
}
