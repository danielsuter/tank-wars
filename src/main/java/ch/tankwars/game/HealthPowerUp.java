package ch.tankwars.game;

public class HealthPowerUp extends PowerUp {
	
	private int healthGain;

	public HealthPowerUp(ActorListener actorListener, int x, int y) {
		super(actorListener, ActorType.HEALTHPOWERUP);
		setX(x);
		setY(y);
		healthGain = 50;
	}

	public int getHealthGain() {
		return healthGain;
	}

	public void setHealthGain(int healthGain) {
		this.healthGain = healthGain;
	}

}
