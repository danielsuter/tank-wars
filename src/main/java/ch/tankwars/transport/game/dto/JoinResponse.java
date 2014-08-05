package ch.tankwars.transport.game.dto;

import ch.tankwars.game.BattlefieldMap;

public class JoinResponse extends Response {
	
	private final int playerId; 
	private final BattlefieldMap battlefieldMap; 
	
	public JoinResponse(int playerId, BattlefieldMap battlefieldMap) {
		super(MessageType.JOIN.getType());
		this.playerId = playerId;
		this.battlefieldMap = battlefieldMap;
	}

	public int getPlayerId() {
		return playerId;
	}

	public BattlefieldMap getPlayGround() {
		return battlefieldMap;
	}
}
