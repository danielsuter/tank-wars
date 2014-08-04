package ch.tankwars.transport.game.dto;

public class JoinResponse extends Response {
	
	private final String playerId; 
	private final int fieldWidth;
	private final int fieldHeight; 
	
	public JoinResponse(String playerId, int fieldWidth, int fieldHeight) {
		super(MessageType.JOIN.getType());
		this.playerId = playerId;
		this.fieldWidth = fieldWidth; 
		this.fieldHeight = fieldHeight; 
	}

	public String getPlayerId() {
		return playerId;
	}

	public int getFieldWidth() {
		return fieldWidth;
	}

	public int getFieldHeight() {
		return fieldHeight;
	}
}
