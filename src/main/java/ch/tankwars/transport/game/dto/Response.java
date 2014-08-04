package ch.tankwars.transport.game.dto;

public abstract class Response {
	private String messageType;

	public Response(String messageType) {
		this.messageType = messageType;
	}

	public String getMessageType() {
		return messageType;
	}
}
