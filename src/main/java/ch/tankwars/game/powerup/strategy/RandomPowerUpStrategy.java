package ch.tankwars.game.powerup.strategy;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import ch.tankwars.game.Actor;
import ch.tankwars.game.powerup.HealthPowerUp;
import ch.tankwars.game.powerup.LaserGunPowerUp;
import ch.tankwars.game.powerup.PowerUp;
import ch.tankwars.game.powerup.RocketLauncherPowerUp;

public class RandomPowerUpStrategy implements PowerUpSpawnStrategy {
	
	private int roundCounter = 0;
	
	public RandomPowerUpStrategy() {
	}
	
	@Override
	public List<PowerUp> spawnPowerUps(List<Actor> actors) {
		++roundCounter;
		if(roundCounter % 200 == 0) {
			final List<Actor> powerUps = actors.stream().filter(a -> a instanceof PowerUp).collect(Collectors.toList());
			if(powerUps.size() <= 12) {
				return reSpawnNewPowerUps(actors);
			}
		}
		return Collections.emptyList();
	}


	private List<PowerUp> reSpawnNewPowerUps(List<Actor> actors) {
		final Random random = new Random();
		final int powerUpCount = random.nextInt(5);
		List<PowerUp> powerUps = new LinkedList<PowerUp>();
		for (int i = 0; i <= powerUpCount; i++) {
			PowerUp powerUp = spawnNewPowerUp(random);
			powerUps.add(powerUp);
		}
		return powerUps;
	}
	
	private PowerUp spawnNewPowerUp(final Random random) {
		final int type = random.nextInt(3);
		
		switch (type) {
		case 0: 
			return new HealthPowerUp(0, 0);
		case 1: 
			return new LaserGunPowerUp(0, 0);
		default:
			return new RocketLauncherPowerUp(0, 0);
		}
	}
}
