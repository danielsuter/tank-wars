package ch.tankwars.game.powerup;

import ch.tankwars.game.Direction;
import ch.tankwars.game.projectiles.CircularProjectile;
import ch.tankwars.game.projectiles.Projectile;

public enum WeaponData {

	STANDARD_CANON(0, Projectile.DEFAULT_PROJECTILE_SPEED, Projectile.DEFAULT_PROJECTILE_POWER, Projectile.DEFAULT_PROJECTILE_DIMENSION, 1, -1),
	LASER_GUN(1, 30, 7, 2, 10, 30),
	ROCKET_LAUNCHER(2, 8, 25, 4, 1, 10);
	
	private int velocity;
	private int power;
	private int dimension;
	private int fireRatePerSecond;
	private int weaponId;
	private int maxShots;

	private WeaponData(int weaponId, int velocity, int power, int dimension, int fireRatePerSecond, int maxShots) {
		this.weaponId = weaponId;
		this.dimension = dimension;
		this.fireRatePerSecond = fireRatePerSecond;
		this.velocity = velocity;
		this.power = power;
		this.maxShots = maxShots;
	}
	
	public Projectile shoot(int tankId, Direction direction) {
		// TODO remove hack
		Projectile projectile = null;
		if(this == LASER_GUN) {
			projectile = new Projectile(tankId) {
				@Override
				public int getHeight() {
					return direction == Direction.LEFT || direction == Direction.RIGHT ? 2 : 16;
				}
				
				@Override
				public int getWidth() {
					return direction == Direction.DOWN || direction == Direction.UP ? 2 : 16;
				}
			};
		} else {
			projectile = new CircularProjectile(tankId);
		}
		projectile.setProjectileDimension(dimension);
		projectile.setVelocity(velocity);
		projectile.setPower(power);
		projectile.setDirection(direction);
		
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
}
