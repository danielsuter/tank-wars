package ch.tankwars.transport.game;

import javax.websocket.Session;

import ch.tankwars.game.Tank;

public class PlayerPeer {
	
	private Session playerSession;
	private Tank tank;
	private String name;
	
	public PlayerPeer(Session playerSession, String name) {
		this.playerSession = playerSession;
		this.name = name;
	}
	
	public void setTank(Tank tank) {
		this.tank = tank;
	}
	
	public String getName() {
		return name;
	}
	
	public Tank getTank() {
		return tank;
	}

	public Session getSession() {
		return playerSession;
	}
}
