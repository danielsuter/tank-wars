package ch.tankwars.game;

public class Wall extends Obstacle {

	public Wall(int x, int y, int width, int height) {
		super(ActorType.WALL);
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
	}
}
