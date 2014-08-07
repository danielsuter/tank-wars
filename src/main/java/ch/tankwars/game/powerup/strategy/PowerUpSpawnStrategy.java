package ch.tankwars.game.powerup.strategy;

import java.util.List;

import ch.tankwars.game.Actor;
import ch.tankwars.game.powerup.PowerUp;

public interface PowerUpSpawnStrategy {
	
	public List<PowerUp> spawnPowerUps(List<Actor> actors);
}