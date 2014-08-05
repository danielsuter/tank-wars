package ch.tankwars.game;


public class Projectile extends Actor {
	
	private static final int DEFAULT_PROJECTILE_SPEED = 10;
	private static final int DEFAULT_PROJECTILE_DIMENSION = 3; 
	private int projectileDimension;
	private int owningTankId; 

	public Projectile(ActorListener actorListener, int owningTankId) {
		super(actorListener, ActorType.PROJECTILE);
		this.owningTankId = owningTankId;
		setProjectileDimension(DEFAULT_PROJECTILE_DIMENSION);
		setVelocity(DEFAULT_PROJECTILE_SPEED);
	}
	
	@Override
	public void act() {
		int newX = getX() + getDirection().calculateVelocityX(getVelocity());
		int newY = getY() + getDirection().calculateVelocityY(getVelocity());
		
		if(newX > Game.GAME_WIDTH - getProjectileDimension()  || newX < 0) {
			this.setRemove(true);
			return;
		} 
		if(newY > Game.GAME_HEIGHT - getProjectileDimension()  || newY < 0) {
			this.setRemove(true);
			return;
		}
		super.act();
	}
	
	public int getOwningTankId() {
		return owningTankId;
	}

	public int getProjectileDimension() {
		return projectileDimension;
	}
	
	public void setProjectileDimension(int dimension) {
		this.projectileDimension = dimension;
	}
	
	@Override
	public int getWidth() {
		return projectileDimension;
	}
	
	@Override
	public int getHeight() {
		return projectileDimension;
	}

	@Override
	public void collision(Actor actor) {
		if(actor.getId() != owningTankId) {
			setRemove(true);
		}
	}
}
