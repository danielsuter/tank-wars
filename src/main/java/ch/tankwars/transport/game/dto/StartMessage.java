package ch.tankwars.transport.game.dto;

public class StartMessage extends Response {

	public StartMessage() {
		super(MessageType.START.getType());
	}

}
