package ch.tankwars.transport.game.dto;

public enum MessageType {

	JOIN("JOIN"),
	PLAYERS_CHANGED("PLAYERS_CHANGED"),
	CONNECT("CONNECT"), 
	START("START");

	private final String type;

	private MessageType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
