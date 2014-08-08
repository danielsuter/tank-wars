package ch.tankwars.game.powerup;

import ch.tankwars.game.Actor;
import ch.tankwars.game.ActorType;
import ch.tankwars.game.Referee;

public class BombBag extends PowerUp {
	private int amount = 5;
	
	public BombBag() {
		super(ActorType.BOMB_POWERUP);
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	@Override
	public void onCollision(Actor actor, Referee referee) {
		setRemove(true);
	}
}
