package ch.tankwars.game.powerup;

import ch.tankwars.game.BattlefieldMap;
import ch.tankwars.game.Direction;
import ch.tankwars.game.projectiles.Projectile;

public enum WeaponData {

	STANDARD_CANON(0, Projectile.DEFAULT_PROJECTILE_SPEED, Projectile.DEFAULT_PROJECTILE_POWER, Projectile.DEFAULT_PROJECTILE_DIMENSION, Projectile.DEFAULT_PROJECTILE_DIMENSION, 4, -1),
	LASER_GUN(1, 40, 7, 16, 2, 10, 35),
	ROCKET_LAUNCHER(2, 30, 25, 4, 4, 2, 10);
	
	private int velocity;
	private int power;
	private int width;
	private int height;
	
	private int dimension;
	private int fireRatePerSecond;
	private int weaponId;
	private int maxShots;

	private WeaponData(int weaponId, int velocity, int power, int width, int height, int fireRatePerSecond, int maxShots) {
		this.weaponId = weaponId;
		this.width = width;
		this.height = height;
		this.fireRatePerSecond = fireRatePerSecond;
		this.velocity = velocity;
		this.power = power;
		this.maxShots = maxShots;
	}
	
	public Projectile shoot(int tankId, Direction direction, BattlefieldMap battlefieldMap) {
		Projectile projectile = new Projectile(tankId, battlefieldMap);
		projectile.setDirection(direction);
		if(this == LASER_GUN) {
			int laserGunWidth = direction == Direction.LEFT || direction == Direction.RIGHT ? width : height;
			int laserGunHeight = direction == Direction.DOWN || direction == Direction.UP ? width : height;
			projectile.setWidth(laserGunWidth);
			projectile.setHeight(laserGunHeight);

		} else {
			projectile.setWidth(width);
			projectile.setHeight(height);
		}
		projectile.setVelocity(velocity);
		projectile.setPower(power);
		
		return projectile;
	}

	public int getVelocity() {
		return velocity;
	}

	public int getPower() {
		return power;
	}

	public int getDimension() {
		return dimension;
	}

	public int getFireRatePerSecond() {
		return fireRatePerSecond;
	}
	
	public int getMaxShots() {
		return maxShots;
	}
	
	public int getWeaponId() {
		return weaponId;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
