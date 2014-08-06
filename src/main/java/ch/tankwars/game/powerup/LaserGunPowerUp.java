package ch.tankwars.game.powerup;

import ch.tankwars.game.ActorType;

public class LaserGunPowerUp extends PowerUp {
	
	private Weapon weapon;

	public LaserGunPowerUp(int x, int y) {
		super(ActorType.LASERGUNPOWERUP);
		setX(x);
		setY(y);
		weapon = Weapon.LASER_GUN;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

}
