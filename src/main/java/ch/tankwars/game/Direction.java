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
}
