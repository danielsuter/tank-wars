package ch.tankwars.game;

public enum Weapon {

	STANDARD_CANON(Projectile.DEFAULT_PROJECTILE_SPEED, Projectile.DEFAULT_PROJECTILE_POWER, Projectile.DEFAULT_PROJECTILE_DIMENSION),
	LASER_GUN(25, 7, 10),
	ROCKET_LAUNCHER(8, 25, 4);

	private int velocity;
	private int power;
	private int dimension;

	private Weapon(int velocity, int power, int dimension) {
		this.dimension = dimension;
		this.setVelocity(velocity);
		this.setPower(power);
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

}
