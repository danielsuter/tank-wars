package ch.tankwars.game;

public enum ActorType {
	TANK(0),
	PROJECTILE(1),
	WALL(2),
	HEALTHPOWERUP(3),
	FIRERATEPOWERUP(4),
	LASERGUNPOWERUP(5),
	ROCKETLAUNCHERPOWERUP(6);
	
	private int identifier;

	private ActorType(int identifier) {
		this.identifier = identifier;
	}
	
	public int getIdentifier() {
		return identifier;
	}
}
