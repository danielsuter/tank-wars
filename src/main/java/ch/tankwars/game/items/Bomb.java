package ch.tankwars.game.items;

import ch.tankwars.game.Actor;
import ch.tankwars.game.ActorListener;
import ch.tankwars.game.ActorType;
import ch.tankwars.game.BattlefieldMap;
import ch.tankwars.game.Direction;
import ch.tankwars.game.Referee;
import ch.tankwars.game.projectiles.Projectile;

public class Bomb extends Actor {

	private int ticksBeforeExplosion = 50;
	private int currentTick = 0;
	private ActorListener actorListener;

	private BattlefieldMap battlefieldMap;
	private int owningTankId;

	public Bomb(ActorListener actorListener, int owningTankId, BattlefieldMap battlefieldMap) {
		super(ActorType.BOMB);
		this.actorListener = actorListener;
		this.owningTankId = owningTankId;
		this.battlefieldMap = battlefieldMap;
		setHeight(25);
		setWidth(25);
	}

	@Override
	public void act() {
		if (currentTick >= ticksBeforeExplosion) {

			createBulletsLeft();
			createBulletsRight();
			createBulletsDown();
			createBulletsUp();

			setRemove(true);
		}

		++currentTick;
	}

	private void createBulletsLeft() {
		for (int i = 1; i < 7; i++) {
			Projectile projectile = new Projectile(owningTankId, battlefieldMap);
			projectile.setDirection(Direction.LEFT);
			projectile.setVelocity(40);
			projectile.setPower(20);
			int pX = getX() + getWidth() / 2;
			int pY = getY() + getHeight() / 2;
			projectile.setPosition(pX - i * getWidth(), pY);
			actorListener.createActor(projectile);
		}
	}

	private void createBulletsRight() {
		for (int i = 1; i < 7; i++) {
			Projectile projectile = new Projectile(owningTankId, battlefieldMap);
			projectile.setDirection(Direction.RIGHT);
			projectile.setVelocity(40);
			projectile.setPower(20);
			int pX = getX() + getWidth() / 2;
			int pY = getY() + getHeight() / 2;
			projectile.setPosition(pX + i * getWidth(), pY);
			actorListener.createActor(projectile);
		}
	}

	private void createBulletsUp() {
		for (int i = 1; i < 7; i++) {
			Projectile projectile = new Projectile(owningTankId, battlefieldMap);
			projectile.setDirection(Direction.UP);
			projectile.setVelocity(40);
			projectile.setPower(20);
			int pX = getX() + getWidth() / 2;
			int pY = getY() + getHeight() / 2;
			projectile.setPosition(pX, pY - i * getHeight());
			actorListener.createActor(projectile);
		}
	}

	private void createBulletsDown() {
		for (int i = 1; i < 7; i++) {
			Projectile projectile = new Projectile(owningTankId, battlefieldMap);
			projectile.setDirection(Direction.DOWN);
			projectile.setVelocity(40);
			projectile.setPower(20);
			int pX = getX() + getWidth() / 2;
			int pY = getY() + getHeight() / 2;
			projectile.setPosition(pX, pY + i * getHeight());
			actorListener.createActor(projectile);
		}
	}

	@Override
	public void onCollision(Actor actor, Referee referee) {
	}
}
