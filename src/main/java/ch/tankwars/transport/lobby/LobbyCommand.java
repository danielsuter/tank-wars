package ch.tankwars.transport.lobby;

public class LobbyCommand {
	private Command command;
	
	private String data;
	
	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
