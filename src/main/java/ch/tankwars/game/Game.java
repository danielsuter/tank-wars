package ch.tankwars.game;

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
	private ConcurrentLinkedQueue<Actor> actorsToRemove = new ConcurrentLinkedQueue<Actor>();

	private List<Actor> actors = new LinkedList<Actor>();
	private int globalId;

	public synchronized void tick() {
		Actor actorToAdd = null;
		while ((actorToAdd = actorsToAdd.poll()) != null) {
			actors.add(actorToAdd);
		}

		Actor actorToRemove = null;
		while ((actorToRemove = actorsToRemove.poll()) != null) {
			actors.remove(actorToRemove);
		}

		for (Actor actor : actors) {
			actor.act();
		}
		// detect collisions
	}

	public Tank spawn(String playerName) {
		final Tank tank = new Tank(this, playerName, generateId());

		final Random random = new Random();
		final int x = random.nextInt(GAME_WIDTH + 1 - tank.getWidth());
		final int y = random.nextInt(GAME_HEIGHT + 1 - tank.getHeight());

		tank.setPosition(x, y);
		actors.add(tank);

		return tank;
	}

	public List<Actor> getActors() {
		return actors;
	}

	private int generateId() {
		return ++globalId;
	}

	@Override
	public void removeActor(Actor actor) {
		actorsToRemove.add(actor);
	}

	@Override
	public void createActor(Actor actor) {
		actor.setId(generateId());
		actorsToAdd.add(actor);
	}

	public Wall addWall(int x, int y, int height, int width) {
		final Wall wall = new Wall(this, generateId(), x, y, height, width);
		actors.add(wall);

		return wall;
	}
}
