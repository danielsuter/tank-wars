package ch.tankwars.transport.game.dto;

public class StopMessage extends Response {

	public StopMessage() {
		super(MessageType.STOP.getType());
	}

}
