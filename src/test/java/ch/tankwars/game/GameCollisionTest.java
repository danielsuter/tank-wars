package ch.tankwars.game;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ch.tankwars.game.projectiles.CircularProjectile;
import ch.tankwars.game.projectiles.Projectile;

public class GameCollisionTest {
	
	private Game game;

	@Before
	public void setUp() {
		game = new Game();
	}
	
	@Test
	public void collisionTankProjectile() {
		Tank tank = game.spawn("Chief");
		tank.setPosition(0, 0);
		
		Projectile projectile = new CircularProjectile(-1);
		projectile.setPosition(24, 0);
		
		assertTrue(projectile.collidesWith(tank));
	}
	
	@Test
	public void collisionTankProjectileWithSpeed() {
		game.setPlayGround(new BattlefieldMap(100, 100));
		Tank tank = game.spawn("Chief");
		tank.setPosition(0, 0);
		
		Projectile projectile = new CircularProjectile(-1);
		projectile.setPosition(27, 0);
		projectile.setDirection(Direction.LEFT);
		projectile.setVelocity(10);
		game.createActor(projectile);
		
		game.tick();
		
		assertTrue(projectile.collidesWith(tank));
		assertTrue(projectile.isDead());
	}
	
	@Test
	public void noCollisionBetweenProjectiles() {
		Projectile projectile1 = new CircularProjectile(-1);
		projectile1.setPosition(0, 0);
		projectile1.setDirection(Direction.LEFT);
		projectile1.setVelocity(10);
		
		Projectile projectile2 = new CircularProjectile(-2);
		projectile2.setPosition(30, 0);
		projectile2.setDirection(Direction.LEFT);
		projectile2.setVelocity(10);
		
		assertFalse(projectile1.collidesWith(projectile2));
		assertFalse(projectile2.collidesWith(projectile1));
		
	}
	
	@Test
	public void collisionProjectileWall() throws Exception {
		BattlefieldMap battlefieldMap = new BattlefieldMap(1000, 1000);
		Wall wall = new Wall(1, 1, 200, 10);
		battlefieldMap.addWall(wall);
		game.setPlayGround(battlefieldMap);
		
		Projectile projectile = new CircularProjectile(-1);
		projectile.setPosition(15, 15);
		projectile.setDirection(Direction.UP);
		projectile.setVelocity(10);
		game.createActor(projectile);
		game.tick();
		
		assertTrue(projectile.collidesWith(wall));
	}
	
	@Test
	public void collisionWallTank() throws Exception {
		BattlefieldMap battlefieldMap = new BattlefieldMap(1000, 1000);
		game.setPlayGround(battlefieldMap);
		Wall wall = new Wall(1, 1, 200, 15);
		battlefieldMap.addWall(wall);
		
		Tank tank = game.spawn("Support Veigar");
		tank.setPosition(15, 20);
		tank.move(Direction.UP);
		game.tick();
		
		assertEquals(tank.getY(), wall.getY() + wall.getHeight());
	}

}
