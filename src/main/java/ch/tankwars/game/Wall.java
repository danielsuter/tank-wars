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


	@Override
	public void collision(Actor actor) {
		// I'm wall and undestructible
	}

}