package ch.tankwars.game;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class GameTest {

	private static final int GAME_HEIGHT = 100;
	private static final int GAME_WIDTH = 100;
	private Game game;
	private Tank tank;

	@Before
	public void setUp() {
		game = new Game();
		game.setPlayGround(new BattlefieldMap(GAME_WIDTH, GAME_HEIGHT));
		tank = game.spawn("testPlayer");
	}
	
	@Test
	public void tankCollisionWithWallsLeft() {
		tank.setPosition(0 + 2, 0);
		tank.move(Direction.LEFT);
		tank.setVelocity(5);
		game.tick();
		
		assertEquals(0, tank.getX());		
	}
	
	@Test
	public void tankCollisionWithWallsRight() {
		int expectedPositionX = GAME_WIDTH - tank.getWidth();
		tank.setPosition(expectedPositionX - 2, 0);
		tank.setVelocity(5);
		
		tank.move(Direction.RIGHT);
		game.tick();
		
		assertEquals(expectedPositionX, tank.getX());		
	}
	
	@Test
	public void tankCollisionWithWallsDown() {
		int expectedPositionY = GAME_HEIGHT - tank.getHeight();
		tank.setPosition(0, expectedPositionY - 2);
		tank.setVelocity(5);
		tank.move(Direction.DOWN);
		game.tick();
		
		assertEquals(expectedPositionY, tank.getY());		
	}
	
	@Test
	public void tankCollisionWithWallsUp() {
		tank.setPosition(0, 0 + 2);
		tank.setVelocity(5);
		tank.move(Direction.UP);

		game.tick();
		
		assertEquals(0, tank.getY());		
	}

	@Test
	public void tankCollisionWithTankOnXAxis() {
		tank.setPosition(0, 0);
		tank.setVelocity(2);
		tank.move(Direction.RIGHT);
		
		Tank tank2 = game.spawn("player 2");
		tank2.setPosition(tank.getWidth() + 2, 0);
		tank2.setVelocity(2);
		tank2.move(Direction.LEFT);
		
		game.tick();
		
		assertFalse("tanks must not collide", tank.collidesWith(tank2));
	}
	
	@Test
	public void tankCollisionTank() {
		tank.setPosition(0, 0);
		tank.setVelocity(2);
		tank.move(Direction.RIGHT);
		
		Tank tank2 = game.spawn("player 2");
		tank2.setPosition(0, tank.getHeight());
		tank2.setVelocity(2);
		tank2.move(Direction.UP);
		
		game.tick();
		
		assertFalse("tanks must not collide", tank.collidesWith(tank2));
	}
}
