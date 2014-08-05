package ch.tankwars.game;

public enum Weapon {

	STANDARD_CANON(Projectile.DEFAULT_PROJECTILE_SPEED, Projectile.DEFAULT_PROJECTILE_POWER, Projectile.DEFAULT_PROJECTILE_DIMENSION, 1),
	LASER_GUN(35, 7, 10, 3),
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
	
	public Projectile shoot(int tankId) {
		// TODO remove hack
		Projectile projectile = null;
		if(this == LASER_GUN) {
			projectile = new Projectile(tankId) {
				@Override
				public int getHeight() {
					return getDirection() == Direction.LEFT || getDirection() == Direction.RIGHT ? 2 : getDimension();
				}
				
				@Override
				public int getWidth() {
					return getDirection() == Direction.DOWN || getDirection() == Direction.UP ? 2 : getDimension();
				}
			};
		} else {
			projectile = new Projectile(tankId);
		}
		
		projectile.setVelocity(velocity);
		projectile.setPower(power);
		projectile.setProjectileDimension(dimension);
		
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
