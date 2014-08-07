package ch.tankwars.game.powerup;

import ch.tankwars.game.ActorType;

public class RocketLauncherPowerUp extends PowerUp {
	
	private Weapon weapon;

	public RocketLauncherPowerUp(int x, int y) {
		super(ActorType.ROCKETLAUNCHERPOWERUP);
		setX(x);
		setY(y);
		weapon = Weapon.ROCKET_LAUNCHER;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

}
