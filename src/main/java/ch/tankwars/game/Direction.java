package ch.tankwars.game;

public enum Direction {
	RIGHT("E"),
	LEFT("W"),
	UP("N"),
	DOWN("S");

	private String identifier;

	private Direction(String identifier) {
		this.identifier = identifier;
	}
	
	public String getIdentifier() {
		return identifier;
	}

	public int calculateVelocityX(int velocity) {
		switch(this) {
		case DOWN:
		case UP:
			return 0;
		case LEFT:
			return -velocity;
		case RIGHT:
			return velocity;
		}
		throw new RuntimeException("Cannot handle " + this);
	}

	public int calculateVelocityY(int velocity) {
		switch(this) {
		case DOWN:
			return velocity;
		case UP:
			return -velocity;
		case LEFT:
		case RIGHT:
			return 0;
		}
		throw new RuntimeException("Cannot handle " + this);
	}
}
