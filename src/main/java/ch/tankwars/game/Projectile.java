package ch.tankwars.game;


public class Projectile extends Actor{
	

	public Projectile(ActorListener actorListener, int id) {
		super(actorListener, ActorType.PROJECTILE, id);
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
