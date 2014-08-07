package ch.tankwars.game;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

/**
 * Contains all game logic.
 */
public class Game implements ActorListener {

	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;

	private ConcurrentLinkedQueue<Actor> actorsToAdd = new ConcurrentLinkedQueue<Actor>();

	private List<Actor> actors = new LinkedList<Actor>();
	private int globalId;
	private static int roundCounter = 0; 

	private BattlefieldMap battlefieldMap;
	private Referee referee = new Referee();

	public synchronized void tick() {
		roundCounter++;
		addActorsInQueue();

		removeDeadActors();

		for (Actor actor : actors) {
			if (!actor.isDead()) {
				actor.act();
				for (Wall wall : battlefieldMap.getWalls()) {
					if(actor.collidesWith(wall)) {
//						wall.onCollision(actor, referee);
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
		if(roundCounter % 200 == 0) {
			final List<Actor> powerUps = actors.stream().filter(a -> a instanceof PowerUp).collect(Collectors.toList());
			if(powerUps.size() <= 10) {
				reSpawnNewPowerUps();
			}
		}
		removeDeadActors();
	}

	private void reSpawnNewPowerUps() {
		final Random random = new Random();
		final int powerUpCount = random.nextInt(5);
		for (int i = 0; i <= powerUpCount; i++) {
			spawnNewPowerUp(random);
		}
	}
	
	// TODO rework
	private void spawnNewPowerUp(final Random random) {
		PowerUp powerUp = null;
		final int type = random.nextInt(4);
		
		switch (type) {
		case 0: 
		case 1:
			powerUp = new HealthPowerUp(0, 0);
			break;
		case 2: 
			powerUp = new LaserGunPowerUp(0, 0);
			break;
		case 3: 
			powerUp = new RocketLauncherPowerUp(0, 0);
			break;
		default:
			powerUp = new HealthPowerUp(0, 0);
			break;
		}
		computeRandomActorPosition(powerUp);
		createActor(powerUp);
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
		final Tank tank = new Tank(this, playerName);

		computeRandomActorPosition(tank);
		createActor(tank);
		referee.addTank(tank);

		return tank;
	}

	private void computeRandomActorPosition(final Actor actor) {
		final Random random = new Random();
		final int x = random.nextInt(GAME_WIDTH + 1 - actor.getWidth());
		final int y = random.nextInt(GAME_HEIGHT + 1 - actor.getHeight());
		actor.setPosition(x, y);
		checkForCollisions(actor);
	}

	private void checkForCollisions(Actor actor) {
		for (Actor otherActor : actors) {
			if (actor.collidesWith(otherActor)) {
				computeRandomActorPosition(actor);
			}
		}
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

	public BattlefieldMap getPlayGround() {
		return battlefieldMap;
	}
	// TODO should become load playground
	public void setPlayGround(BattlefieldMap battlefieldMap) {
		this.battlefieldMap = battlefieldMap;
		
		battlefieldMap.getWalls().forEach(wall -> wall.setId(generateId()));
		battlefieldMap.getPowerUps().forEach(powerup -> createActor(powerup));
	}
}
