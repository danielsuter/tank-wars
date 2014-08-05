package ch.tankwars.transport.game.dto;


public class ConnectResponse extends Response {
	
	private boolean gameRunning;
	
	public ConnectResponse(boolean gameRunning) {
		super(MessageType.CONNECT.getType());
		this.gameRunning = gameRunning;
	}

	public boolean isGameRunning() {
		return gameRunning;
	}

}
