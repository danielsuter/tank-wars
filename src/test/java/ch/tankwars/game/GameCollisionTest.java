package ch.tankwars.game;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class GameCollisionTest {
	
	@Test
	public void collisionTankProjectile() {
		Game game = new Game();
		Tank tank = game.spawn("Chief");
		tank.setPosition(0, 0);
		
		Projectile projectile = new Projectile(game);
		projectile.setPosition(25, 0);
		
		assertTrue(projectile.collidesWith(tank));
	}
	
	@Test
	public void collisionTankProjectileWithSpeed() {
		Game game = new Game();
		Tank tank = game.spawn("Chief");
		tank.setPosition(0, 0);
		
		Projectile projectile = new Projectile(game);
		projectile.setPosition(27, 0);
		projectile.setDirection(Direction.LEFT);
		projectile.setVelocity(10);
		game.createActor(projectile);
		
		game.tick();
		
		assertTrue(projectile.collidesWith(tank));
		assertTrue(projectile.isRemove());
	}
	
	

}
