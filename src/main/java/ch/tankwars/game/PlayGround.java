package ch.tankwars.game;

import java.util.HashSet;
import java.util.Set;

public class PlayGround{
	
	private int fieldWidth;
	private int fieldHeight;
	private Set<Wall> walls = new HashSet<Wall>();
	
	public PlayGround(int fieldWidth, int fieldHeight) {
		this.setFieldWidth(fieldWidth);
		this.setFieldHeight(fieldHeight);
	}

	public int getFieldWidth() {
		return fieldWidth;
	}

	public void setFieldWidth(int width) {
		this.fieldWidth = width;
	}

	public int getFieldHeight() {
		return fieldHeight;
	}

	public void setFieldHeight(int height) {
		this.fieldHeight = height;
	}

	public void addWall(Wall wall) {
		walls.add(wall);
	}
	
	public Set<Wall> getWalls() {
		return walls;
	}
}
