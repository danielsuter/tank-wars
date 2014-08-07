package ch.tankwars.game.powerup.strategy;

import java.util.Collections;
import java.util.List;

import ch.tankwars.game.Actor;
import ch.tankwars.game.powerup.PowerUp;

public class NoopPowerUpStrategy implements PowerUpSpawnStrategy {

	@Override
	@SuppressWarnings("unchecked")
	public List<PowerUp> spawnPowerUps(List<Actor> actors) {
		return (List<PowerUp>) Collections.EMPTY_LIST;
	}

}
