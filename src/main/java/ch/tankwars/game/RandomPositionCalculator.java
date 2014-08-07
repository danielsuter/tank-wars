package ch.tankwars.game;

import java.util.List;
import java.util.Random;

public class RandomPositionCalculator {
	
	public void setPosition(List<Actor> actors, Actor forActor) {
		computeRandomActorPosition(actors, forActor);
	}
	
	private void computeRandomActorPosition(List<Actor> actors, Actor actor) {
		final Random random = new Random();
		final int x = random.nextInt(Game.GAME_WIDTH - 50 - actor.getWidth()) + 20;
		final int y = random.nextInt(Game.GAME_HEIGHT - 50 - actor.getHeight()) + 20;
		actor.setPosition(x, y);
		checkForCollisions(actors, actor);
	}

	private void checkForCollisions(List<Actor> actors, Actor actor) {
		for (Actor otherActor : actors) {
			if (actor.collidesWith(otherActor)) {
				computeRandomActorPosition(actors, actor);
			}
		}
	}
}
