package ch.tankwars.game;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Contains all game logic.
 */
public class Game implements ActorListener {

	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;
	
	private ConcurrentLinkedQueue<Actor> actorsToAdd = new ConcurrentLinkedQueue<Actor>();
	
	private List<Actor> actors = new LinkedList<Actor>();
	private int globalId;
	
	private PlayGround playGround;

	public synchronized void tick() {
		addActorsInQueue();
		
		removeDeadActors();
		
		for (Actor actor : actors) {
			if(!actor.isRemove()) {
				actor.act();
				for (Actor otherActor : actors) {
					if(actor != otherActor && actor.collidesWith(otherActor)) {
						actor.collision(otherActor);
						otherActor.collision(actor);
					}
				}
			}
		}
	}

	private void addActorsInQueue() {
		Actor actorToAdd = null;
		while((actorToAdd = actorsToAdd.poll()) != null){
			actors.add(actorToAdd);
		}
	}

	private void removeDeadActors() {
		Iterator<Actor> iterator = actors.iterator();
		while(iterator.hasNext()) {
			Actor actor = iterator.next();
			if(actor.isRemove()) {
				iterator.remove();
			}
		}
	}
	
	public Tank spawn(String playerName) {
		final Tank tank = new Tank(this, playerName);
		
		final Random random = new Random();
		final int x = random.nextInt(GAME_WIDTH + 1 - tank.getWidth());
		final int y = random.nextInt(GAME_HEIGHT + 1 - tank.getHeight());
		
		tank.setPosition(x, y);
		
		createActor(tank);
		
		return tank;
	}
	
	public List<Actor> getActors() {
		return actors;
	}

	private int generateId() {
		return ++globalId ;
	}

	@Override
	public void createActor(Actor actor) {
		actor.setId(generateId());
		actorsToAdd.add(actor);
	}

	public Wall addWall(int x, int y, int height, int width) {
		final Wall wall = new Wall(this, generateId(), x, y, height, width);
		playGround.addWall(wall);
		return wall;
	}

	public PlayGround getPlayGround() {
		return playGround;
	}

	public void setPlayGround(PlayGround playGround) {
		this.playGround = playGround;
	}
}
