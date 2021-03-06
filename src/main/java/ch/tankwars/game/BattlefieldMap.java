package ch.tankwars.game;

import java.util.HashSet;
import java.util.Set;

import ch.tankwars.game.powerup.PowerUp;

public class BattlefieldMap{
	
	private int fieldWidth;
	private int fieldHeight;
	private Set<Wall> walls = new HashSet<Wall>();
	private Set<PowerUp> powerUps = new HashSet<PowerUp>();
	
	public BattlefieldMap(int fieldWidth, int fieldHeight) {
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
	
	public void addPowerUp(PowerUp powerUp) {
		powerUps.add(powerUp);
	}
	
	public Set<Wall> getWalls() {
		return walls;
	}
	
	public Set<PowerUp> getPowerUps() {
		return powerUps;
	}
}
