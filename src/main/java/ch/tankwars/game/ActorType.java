package ch.tankwars.game;

public enum ActorType {
	TANK(0),
	PROJECTILE(1),
	WALL(2),
	HEALTHPOWERUP(3),
	FIRERATEPOWERUP(4),
	LASERGUNPOWERUP(5),
	ROCKETLAUNCHERPOWERUP(6),
	MINE_POWERUP(7),
	BOMB_POWERUP(8),
	MINE(9),
	BOMB(10);
	
	private int identifier;

	private ActorType(int identifier) {
		this.identifier = identifier;
	}
	
	public int getIdentifier() {
		return identifier;
	}
}
