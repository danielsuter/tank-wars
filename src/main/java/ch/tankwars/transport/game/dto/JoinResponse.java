package ch.tankwars.transport.game.dto;

public class JoinResponse extends Response {
	
	private final int playerId; 
	
	public JoinResponse(int playerId) {
		super(MessageType.JOIN.getType());
		this.playerId = playerId;
	}

	public int getPlayerId() {
		return playerId;
	}
}
