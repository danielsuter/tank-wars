package ch.tankwars.transport.game.dto;

import ch.tankwars.game.PlayGround;

public class JoinResponse extends Response {
	
	private final int playerId; 
	private final PlayGround playGround;
	
	public JoinResponse(int playerId, PlayGround playGround) {
		super(MessageType.JOIN.getType());
		this.playerId = playerId;
		this.playGround = playGround;
	}

	public int getPlayerId() {
		return playerId;
	}

	public PlayGround getPlayGround() {
		return playGround;
	}
}
