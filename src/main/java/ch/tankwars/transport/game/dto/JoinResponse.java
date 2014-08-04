package ch.tankwars.transport.game.dto;

public class JoinResponse extends Response {
	
	private final int playerId; 
	private final int fieldWidth;
	private final int fieldHeight; 
	
	public JoinResponse(int playerId, int fieldWidth, int fieldHeight) {
		super(MessageType.JOIN.getType());
		this.playerId = playerId;
		this.fieldWidth = fieldWidth; 
		this.fieldHeight = fieldHeight; 
	}

	public int getPlayerId() {
		return playerId;
	}

	public int getFieldWidth() {
		return fieldWidth;
	}

	public int getFieldHeight() {
		return fieldHeight;
	}
}
