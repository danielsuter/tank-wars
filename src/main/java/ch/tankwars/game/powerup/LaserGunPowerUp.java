package ch.tankwars.game.powerup;

import ch.tankwars.game.ActorType;

public class LaserGunPowerUp extends PowerUp {
	
	private WeaponData weaponData;

	public LaserGunPowerUp(int x, int y) {
		super(ActorType.LASERGUNPOWERUP);
		setX(x);
		setY(y);
		weaponData = WeaponData.LASER_GUN;
	}

	public WeaponData getWeapon() {
		return weaponData;
	}

	public void setWeapon(WeaponData weaponData) {
		this.weaponData = weaponData;
	}

}
