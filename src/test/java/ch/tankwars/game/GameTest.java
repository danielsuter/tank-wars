package ch.tankwars.game;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class GameTest {

	private Game game;
	private Tank tank;

	@Before
	public void setUp() {
		game = new Game();
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
		int expectedPositionX = Game.GAME_WIDTH - tank.getWidth();
		tank.setPosition(expectedPositionX - 2, 0);
		tank.setVelocity(5);
		
		tank.move(Direction.RIGHT);
		game.tick();
		
		assertEquals(expectedPositionX, tank.getX());		
	}
	
	@Test
	public void tankCollisionWithWallsDown() {
		int expectedPositionY = Game.GAME_HEIGHT - tank.getHeight();
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

}
