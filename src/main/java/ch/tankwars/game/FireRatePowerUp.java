package ch.tankwars.game;

public class FireRatePowerUp extends PowerUp {
	
	private int fireRateGain;

	public FireRatePowerUp(int x, int y) {
		super(ActorType.FIRERATEPOWERUP);
		setX(x);
		setY(y);
		fireRateGain = 3;
	}

	public int getFireRateGain() {
		return fireRateGain;
	}

	public void setFireRateGain(int fireRateGain) {
		this.fireRateGain = fireRateGain;
	}

}
