package ch.tankwars.game;

import java.util.UUID;

public class Projectile extends Actor{
	
	private String playerId;
	private String projectileId;

	public Projectile(ActorListener actorListener, String playerId) {
		super(actorListener, ActorType.PROJECTILE);
		this.playerId = playerId;
		this.projectileId = generateNewProjectileId();
	}
	
	public String getPlayerId() {
		return playerId;
	}
	
	public String getProjectileId() {
		return projectileId;
	}
	
	private String generateNewProjectileId() {
		return UUID.randomUUID().toString();
	}
}
