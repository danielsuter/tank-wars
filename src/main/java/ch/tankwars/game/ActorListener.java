package ch.tankwars.game;


public interface ActorListener {
	 void createActor(Actor actor);

	boolean createActorIfNoCollision(Actor actor);
}
