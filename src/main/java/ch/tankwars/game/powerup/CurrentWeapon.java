package ch.tankwars.game.powerup;

import ch.tankwars.game.Direction;
import ch.tankwars.game.projectiles.Projectile;

public class CurrentWeapon {
	private static final int INFINITE = -1;
	private WeaponData weaponData;
	private int currentShots;
	
	public CurrentWeapon() {
		this.weaponData = WeaponData.STANDARD_CANON;
	}
	
	public void setWeapon(WeaponData data) {
		this.weaponData  = data;
		this.currentShots = 0;
	}
	
	public Projectile shoot(int tankId, Direction direction) {
		++currentShots;
		Projectile projectile = weaponData.shoot(tankId, direction);
		if(weaponData.getMaxShots() != INFINITE && currentShots >= weaponData.getMaxShots()) {
			setWeapon(WeaponData.STANDARD_CANON);
			currentShots = 0;
		}
		return projectile;
	}
	
	public WeaponData getWeaponData() {
		return weaponData;
	}
}
