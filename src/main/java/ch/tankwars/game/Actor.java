package ch.tankwars.game;

public abstract class Actor {
	private int x;
	private int y;
	private int width;
	private int height;

	private int velocityX;
	private int velocityY;

	public void act() {
		x += velocityX;
		y += velocityY;
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setVelocity(int velocityX, int velocityY) {
		this.velocityX = velocityX;
		this.velocityY = velocityY;
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

	public int getVelocityX() {
		return velocityX;
	}

	public void setVelocityX(int velocityX) {
		this.velocityX = velocityX;
	}

	public int getVelociyY() {
		return velocityY;
	}

	public void setVelocityY(int velociyY) {
		this.velocityY = velociyY;
	}
}
