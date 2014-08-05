package ch.tankwars.game;

public class LaserGunPowerUp extends PowerUp {
	
	private Weapon weapon;

	public LaserGunPowerUp(ActorListener actorListener, int x, int y) {
		super(actorListener, ActorType.LASERGUNPOWERUP);
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
