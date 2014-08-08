package ch.tankwars.game.powerup;

import ch.tankwars.game.ActorType;

public class MineBag extends PowerUp {
	private int amount = 5;
	
	public MineBag() {
		super(ActorType.MINE_POWERUP);
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

}
