package ch.tankwars.game.powerup;

import ch.tankwars.game.ActorType;

public class RocketLauncherPowerUp extends PowerUp {
	
	private WeaponData weaponData;

	public RocketLauncherPowerUp(int x, int y) {
		super(ActorType.ROCKETLAUNCHERPOWERUP);
		setX(x);
		setY(y);
		weaponData = WeaponData.ROCKET_LAUNCHER;
	}

	public WeaponData getWeapon() {
		return weaponData;
	}

	public void setWeapon(WeaponData weaponData) {
		this.weaponData = weaponData;
	}

}
