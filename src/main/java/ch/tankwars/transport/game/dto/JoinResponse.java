package ch.tankwars.transport.game.dto;

public class JoinResponse extends Response {
	
	private final String playerId; 
	private final int fieldWidth;
	private final int fieldHeight; 
	
	@Override
	public String getMessageType() {
		return MessageType.JOIN.getType();
	}
	
	public JoinResponse(String playerId, int fieldWidth, int fieldHeight) {
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
