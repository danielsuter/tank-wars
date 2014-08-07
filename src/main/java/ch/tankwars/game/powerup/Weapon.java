package ch.tankwars.game.powerup;

import ch.tankwars.game.Direction;
import ch.tankwars.game.Projectile;

public enum Weapon {

	STANDARD_CANON(Projectile.DEFAULT_PROJECTILE_SPEED, Projectile.DEFAULT_PROJECTILE_POWER, Projectile.DEFAULT_PROJECTILE_DIMENSION, 1),
	LASER_GUN(30, 7, 2, 10),
	ROCKET_LAUNCHER(8, 25, 4, 1);

	private int velocity;
	private int power;
	private int dimension;
	private int fireRatePerSecond;

	private Weapon(int velocity, int power, int dimension, int fireRatePerSecond) {
		this.dimension = dimension;
		this.setFireRatePerSecond(fireRatePerSecond);
		this.setVelocity(velocity);
		this.setPower(power);
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
			projectile = new Projectile(tankId);
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

	public void setVelocity(int velocity) {
		this.velocity = velocity;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public int getDimension() {
		return dimension;
	}

	public void setDimension(int dimension) {
		this.dimension = dimension;
	}

	public int getFireRatePerSecond() {
		return fireRatePerSecond;
	}

	public void setFireRatePerSecond(int fireRatePerSecond) {
		this.fireRatePerSecond = fireRatePerSecond;
	}

}
