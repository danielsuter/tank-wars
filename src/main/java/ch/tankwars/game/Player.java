package ch.tankwars.game;

public class Player {
	
	private String name;
	private Tank tank;
	private int hitsMade;
	private int killsMade;
	
	public Player(String name, Tank tank) {
		super();
		this.name = name;
		this.tank = tank;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Tank getTank() {
		return tank;
	}

	public void setTank(Tank tank) {
		this.tank = tank;
	}

	public int getHitsMade() {
		return hitsMade;
	}

	public void setHitsMade(int hitsMade) {
		this.hitsMade = hitsMade;
	}

	public int getKillsMade() {
		return killsMade;
	}

	public void setKillsMade(int killsMade) {
		this.killsMade = killsMade;
	}
	
}
