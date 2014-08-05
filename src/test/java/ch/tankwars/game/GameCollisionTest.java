package ch.tankwars.game;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class GameCollisionTest {
	
	@Test
	public void collisionTankProjectile() {
		Game game = new Game();
		Tank tank = game.spawn("Chief");
		tank.setPosition(0, 0);
		
		Projectile projectile = new Projectile(game, -1);
		projectile.setPosition(24, 0);
		
		assertTrue(projectile.collidesWith(tank));
	}
	
	@Test
	public void collisionTankProjectileWithSpeed() {
		Game game = new Game();
		Tank tank = game.spawn("Chief");
		tank.setPosition(0, 0);
		
		Projectile projectile = new Projectile(game, -1);
		projectile.setPosition(27, 0);
		projectile.setDirection(Direction.LEFT);
		projectile.setVelocity(10);
		game.createActor(projectile);
		
		game.tick();
		
		assertTrue(projectile.collidesWith(tank));
		assertTrue(projectile.isRemove());
	}
	
	@Test
	public void collisionBetweenProjectiles() {
		Game game = new Game();
		Projectile projectile1 = new Projectile(game, -1);
		projectile1.setPosition(0, 0);
		projectile1.setDirection(Direction.LEFT);
		projectile1.setVelocity(10);
		
		Projectile projectile2 = new Projectile(game, -2);
		projectile2.setPosition(30, 0);
		projectile2.setDirection(Direction.LEFT);
		projectile2.setVelocity(10);
		
		assertFalse(projectile1.collidesWith(projectile2));
		assertFalse(projectile2.collidesWith(projectile1));
		
	}

}
