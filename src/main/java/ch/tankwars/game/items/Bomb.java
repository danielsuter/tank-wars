package ch.tankwars.game.items;

import ch.tankwars.game.Actor;
import ch.tankwars.game.ActorListener;
import ch.tankwars.game.ActorType;
import ch.tankwars.game.Referee;

public class Bomb extends Actor {
	
	private int ticksBeforeExplosion = 50;
	private int currentTick = 0;
	private ActorListener actorListener;
	
	public Bomb(ActorListener actorListener) {
		super(ActorType.BOMB);
		this.actorListener = actorListener;
		setHeight(25);
		setWidth(25);
	}

	@Override
	public void act() {
		if(currentTick >= ticksBeforeExplosion) {
			// TODO create projecttiles
			setRemove(true);
		}
		
		++currentTick;
	}
	
	@Override
	public void onCollision(Actor actor, Referee referee) {
	}
}
