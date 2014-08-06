package ch.tankwars.transport.game.dto;

public enum MessageType {

	JOIN("JOIN"),
	PLAYERS_CHANGED("PLAYERS_CHANGED");

	private final String type;

	private MessageType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
