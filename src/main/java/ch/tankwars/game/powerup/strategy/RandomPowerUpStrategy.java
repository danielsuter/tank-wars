package ch.tankwars.game.powerup.strategy;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import ch.tankwars.game.Actor;
import ch.tankwars.game.RandomPositionCalculator;
import ch.tankwars.game.powerup.HealthPowerUp;
import ch.tankwars.game.powerup.LaserGunPowerUp;
import ch.tankwars.game.powerup.PowerUp;
import ch.tankwars.game.powerup.RocketLauncherPowerUp;

public class RandomPowerUpStrategy implements PowerUpSpawnStrategy {
	
	private int roundCounter = 0;
	private RandomPositionCalculator positionCalculator;
	
	public RandomPowerUpStrategy() {
		positionCalculator = new RandomPositionCalculator();
	}
	
	@Override
	public List<PowerUp> spawnPowerUps(List<Actor> actors) {
		++roundCounter;
		if(roundCounter % 200 == 0) {
			final List<Actor> powerUps = actors.stream().filter(a -> a instanceof PowerUp).collect(Collectors.toList());
			if(powerUps.size() <= 10) {
				return reSpawnNewPowerUps(actors);
			}
		}
		return Collections.emptyList();
	}


	private List<PowerUp> reSpawnNewPowerUps(List<Actor> actors) {
		final Random random = new Random();
		final int powerUpCount = random.nextInt(3);
		List<PowerUp> powerUps = new LinkedList<PowerUp>();
		for (int i = 0; i <= powerUpCount; i++) {
			PowerUp powerUp = spawnNewPowerUp(random);
			positionCalculator.setPosition(actors, powerUp);
			powerUps.add(powerUp);
		}
		return powerUps;
	}
	
	private PowerUp spawnNewPowerUp(final Random random) {
		final int type = random.nextInt(5);
		
		switch (type) {
		case 0: 
			return new HealthPowerUp(0, 0);
		case 1: 
		case 2:
			return new LaserGunPowerUp(0, 0);
		case 3:
		case 4:
		default:
			return new RocketLauncherPowerUp(0, 0);
		}
	}
}
