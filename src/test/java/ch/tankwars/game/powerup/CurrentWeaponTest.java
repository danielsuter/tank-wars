package ch.tankwars.game.powerup;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ch.tankwars.game.Direction;
import ch.tankwars.game.projectiles.Projectile;

public class CurrentWeaponTest {

	@Test
	public void weaponChangeAfterXShots() {
		CurrentWeapon currentWeapon = new CurrentWeapon();
		currentWeapon.setWeapon(WeaponData.ROCKET_LAUNCHER);

		for (int currentShot = 0; currentShot < WeaponData.ROCKET_LAUNCHER.getMaxShots(); currentShot++) {
			Projectile shot = currentWeapon.shoot(0, Direction.LEFT, null);
			assertEquals(WeaponData.ROCKET_LAUNCHER.getPower(), shot.getPower());
		}
		
		Projectile shot = currentWeapon.shoot(0, Direction.LEFT, null);
		assertEquals(WeaponData.STANDARD_CANON.getPower(), shot.getPower());
		
		currentWeapon.setWeapon(WeaponData.LASER_GUN);
		for (int currentShot = 0; currentShot < WeaponData.LASER_GUN.getMaxShots(); currentShot++) {
			shot = currentWeapon.shoot(0, Direction.LEFT, null);
			assertEquals(WeaponData.LASER_GUN.getPower(), shot.getPower());
		}
		
		shot = currentWeapon.shoot(0, Direction.LEFT, null);
		assertEquals(WeaponData.STANDARD_CANON.getPower(), shot.getPower());
	}
	
	
}
