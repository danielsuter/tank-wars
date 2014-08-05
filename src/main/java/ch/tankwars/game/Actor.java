package ch.tankwars.game;

public abstract class Actor implements Cloneable {
	
	//FIXME: Replace x,y,width,height with Dimension 
	// see Wall.Dimension
	
	private int id;
	private int x;
	private int y;
	
	private int width;
	private int height;

	private int velocity = 0;
	private Direction direction = Direction.RIGHT;
	
	private ActorType actorType;
	private ActorListener actorListener;

	public Actor(ActorListener actorListener, ActorType actorType) {
		this.actorListener = actorListener;
		this.actorType = actorType;
	}
	
	@Override
	public Actor clone() throws CloneNotSupportedException {
		return (Actor)super.clone();
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public ActorListener getActorListener() {
		return actorListener;
	}
	
	public ActorType getActorType() {
		return actorType;
	}
	
	public void act() {
		x += direction.calculateVelocityX(velocity);
		y += direction.calculateVelocityY(velocity);
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public int getVelocity() {
		return velocity;
	}

	public void setVelocity(int velocity) {
		this.velocity = velocity;
	}
}
