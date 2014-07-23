package ch.tankwars.game;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Contains all game logic.
 */
public class Game {
	private int width = 800;
	private int height = 600;
	
	private List<Actor> actors = Collections.synchronizedList(new LinkedList<Actor>());
	
	public void tick() {
		for (Actor actor : actors) {
			actor.act();
		}
		
		// detect collisions
	}
	
	public Tank spawn(String playerName) {
		Tank tank = new Tank(playerName);
		
		Random random = new Random();
		int x = random.nextInt(width + 1 - tank.getWidth());
		int y = random.nextInt(height + 1 - tank.getHeight());
		tank.setPosition(x, y);
		
		actors.add(tank);
		
		return tank;
	}
	
	public List<Actor> getActors() {
		return actors;
	}
}
