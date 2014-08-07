package ch.tankwars.game;

public class Items implements Cloneable{
	private int mines;
	private int bombs;
	
	@Override
	protected Items clone() throws CloneNotSupportedException {
		return (Items) super.clone();
	}
	
	public int getMines() {
		return mines;
	}

	public int getBombs() {
		return bombs;
	}

	public void setBombs(int bombs) {
		this.bombs = bombs;
	}

	public void addMines(int amount) {
		mines += amount;
	}

	public void removeMine() {
		--mines;
	}
}
