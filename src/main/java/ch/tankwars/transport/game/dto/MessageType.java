package ch.tankwars.transport.game.dto;

public enum MessageType {

	JOIN("JOIN");

	private final String type;

	private MessageType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
