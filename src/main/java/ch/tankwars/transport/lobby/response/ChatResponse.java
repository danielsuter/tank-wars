package ch.tankwars.transport.lobby.response;

public class ChatResponse extends AbstractResponse {
	private String user;
	private String message;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	ResponseType getType() {
		return ResponseType.CHAT;
	}
}
