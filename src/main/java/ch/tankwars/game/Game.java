package ch.tankwars.game;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Contains all game logic.
 */
public class Game implements ActorListener {

	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;
	
	private List<Actor> actors = Collections.synchronizedList(new LinkedList<Actor>());
	
	public void tick() {
		for (Actor actor : actors) {
			actor.act();
		}
		// detect collisions
	}
	
	public Tank spawn(String playerName) {
		final String playerId = generateNewPlayerId();
		final Tank tank = new Tank(this, playerName, playerId);
		
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

	private String generateNewPlayerId() {
		return UUID.randomUUID().toString();
	}

	public void remove(Tank tank) {
		actors.remove(tank);
	}

	@Override
	public void createActor(Actor actor) {
		actors.add(actor);
	}
}
