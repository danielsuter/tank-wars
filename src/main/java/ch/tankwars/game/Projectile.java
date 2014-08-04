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
	
	@Override
	public void act() {
		int newX = getX() + getVelocityX();
		int newY = getY() + getVelocityY();
		
		if(newX > Game.GAME_WIDTH - getWidth() || newX < 0) {
			getActorListener().removeActor(this);
			return;
		} 
		if(newY > Game.GAME_HEIGHT - getHeight() || newY < 0) {
			getActorListener().removeActor(this);
			return;
		}
		super.act();
	}
}
