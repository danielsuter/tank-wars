package ch.tankwars.game;

import java.awt.Dimension;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

import ch.tankwars.game.powerup.PowerUp;
import ch.tankwars.game.powerup.strategy.PowerUpSpawnStrategy;
import ch.tankwars.game.powerup.strategy.RandomPowerUpStrategy;

/**
 * Contains all game logic.
 */
public class Game implements ActorListener {
	private ConcurrentLinkedQueue<Actor> actorsToAdd = new ConcurrentLinkedQueue<Actor>();

	private List<Actor> actors = new LinkedList<Actor>();
	private int globalId;

	private BattlefieldMap battlefieldMap;
	private Referee referee = new Referee();
	
	private PowerUpSpawnStrategy powerUpSpawnStrategy = new RandomPowerUpStrategy();
	
	private RandomPositionCalculator positionCalculator;
	
	public synchronized void tick() {
		powerUpSpawnStrategy.spawnPowerUps(actors).forEach(new Consumer<PowerUp>() {
			@Override
			public void accept(PowerUp powerUp) {
				positionCalculator.setPosition(getActors(), powerUp);
				createActor(powerUp);
			}
		});
		
		
		addActorsInQueue();

		removeDeadActors();

		for (Actor actor : actors) {
			if (!actor.isDead()) {
				actor.act();
				
				for (Wall wall : battlefieldMap.getWalls()) {
					if(actor.collidesWith(wall)) {
						actor.onCollision(wall, referee);
					}
				}
				for (Actor otherActor : actors) {
					if (actor != otherActor && actor.collidesWith(otherActor)) {
						actor.onCollision(otherActor, referee);
						otherActor.onCollision(actor, referee);
					}
				}
			}
		}
		removeDeadActors();
	}


	private void addActorsInQueue() {
		Actor actorToAdd = null;
		while ((actorToAdd = actorsToAdd.poll()) != null) {
			actors.add(actorToAdd);
		}
	}

	private void removeDeadActors() {
		Iterator<Actor> iterator = actors.iterator();
		while (iterator.hasNext()) {
			Actor actor = iterator.next();
			if (actor.isDead()) {
				iterator.remove();
			}
		}
	}

	public Tank spawn(String playerName) {
		final Tank tank = new Tank(this, playerName, battlefieldMap);
		positionCalculator.setPosition(getActors(), tank);
		createActor(tank);
		referee.addTank(tank);

		return tank;
	}
	
	public List<Actor> getActors() {
		return actors;
	}

	private int generateId() {
		return ++globalId;
	}

	@Override
	public void createActor(Actor actor) {
		actor.setId(generateId());
		actorsToAdd.add(actor);
	}

	@Override
	public boolean createActorIfNoCollision(Actor actorToCreate) {
		boolean hasCollision = actors.stream().anyMatch(actor -> actor.collidesWith(actorToCreate));
		if(!hasCollision) {
			createActor(actorToCreate);
		}
		return !hasCollision;
	}
	
	public BattlefieldMap getPlayGround() {
		return battlefieldMap;
	}
	// TODO should become load playground
	public void setPlayGround(BattlefieldMap battlefieldMap) {
		this.battlefieldMap = battlefieldMap;
		positionCalculator = new RandomPositionCalculator(battlefieldMap);
		
		battlefieldMap.getWalls().forEach(wall -> wall.setId(generateId()));
		battlefieldMap.getPowerUps().forEach(powerup -> createActor(powerup));
	}
	
	public Dimension getPlayfieldSize() {
		return new Dimension(battlefieldMap.getFieldWidth(), battlefieldMap.getFieldHeight());
	}
}
