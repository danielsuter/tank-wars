package ch.tankwars.game;

public class FireRatePowerUp extends PowerUp {
	
	private int fireRateGain;

	public FireRatePowerUp(ActorListener actorListener, int x, int y) {
		super(actorListener, ActorType.FIRERATEPOWERUP);
		setX(x);
		setY(y);
		fireRateGain = 4;
	}

	public int getFireRateGain() {
		return fireRateGain;
	}

	public void setFireRateGain(int fireRateGain) {
		this.fireRateGain = fireRateGain;
	}

}
