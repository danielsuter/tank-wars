package ch.tankwars.game.powerup;

import ch.tankwars.game.ActorType;

public class HealthPowerUp extends PowerUp {
	
	private int healthGain;

	public HealthPowerUp(int x, int y) {
		super(ActorType.HEALTHPOWERUP);
		setX(x);
		setY(y);
		healthGain = 30;
	}

	public int getHealthGain() {
		return healthGain;
	}

	public void setHealthGain(int healthGain) {
		this.healthGain = healthGain;
	}

}
