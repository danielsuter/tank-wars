package ch.tankwars.game;

public class Wall extends Actor {

	public Wall(ActorListener actorListener, int id, int x, int y, int width, int height) {
		super(actorListener, ActorType.WALL);
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
	}
	
	@Override
	public void act() {
		// I'm a wall...
	}
	
	public boolean collidesWith(Actor actor) {
		if (topLeftCornerEqual(actor)) {
			return true;
		}
		
		if (actor.getX() + actor.getWidth() - this.getX() <= 1 && actor.getY() + actor.getHeight() - this.getY() <= 1 ) {
			return true;
		}
		
		if (this.getX() + this.getWidth() - actor.getX() >= 1 && this.getY() + this.getHeight() - actor.getY() >= 1 ) {
			return true;
		}
		
		return false;
	}

	private boolean topLeftCornerEqual(Actor actor) {
		return actor.getX() == this.getX() && actor.getY() == this.getY();
	};
	
	class Dimensions {
		private int x;
		private int y;
		private int width;
		private int height;
		
		public Dimensions(int x, int y, int width, int height) {
			super();
			this.x = x;
			this.y = y;
			this.width = width;
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
		
		
	}

}
