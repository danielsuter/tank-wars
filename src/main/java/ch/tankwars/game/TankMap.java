package ch.tankwars.game;

import java.util.HashSet;
import java.util.Set;

public class TankMap {

	private int width;
	private int height;

	private Set<Wall> walls = new HashSet<Wall>();

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

	public void addWall(Wall wall) {
		walls.add(wall);
	}

}
