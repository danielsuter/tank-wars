package ch.tankwars.game;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class WallTest {

	private ActorListener al;
	private Tank tank;
	private Wall wall;
	
	@Before
	public void setUp() {
		al = new ActorListener() {
			@Override
			public void removeActor(Actor actor) {
			}
			
			@Override
			public void createActor(Actor actor) {
			}
		};
		
		tank = new Tank(al, "Fritzli", 4);
		wall = new Wall(al, 1, 1, 1, 1, 1);
	}

	@Test
	public void wallHitOnSamePixel() {
		tank.setPosition(1, 1);
		tank.setWidth(1);
		tank.setHeight(1);
		
		assertTrue(wall.collidesWith(tank));
	}
	
	@Test
	public void noWallHitOnNextPixelOnXAxis() {
		tank.setPosition(2, 1);
		tank.setWidth(1);
		tank.setHeight(1);
		
		assertFalse(wall.collidesWith(tank));
	}
	
	@Test
	public void noWallHitOnNextPixelOnYAxis() {
		tank.setPosition(1, 2);
		tank.setWidth(1);
		tank.setHeight(1);
		
		assertFalse(wall.collidesWith(tank));
	}
	
	@Test
	public void wallHitTankWidthCausesCollision() {
		wall.setX(5);
		
		tank.setPosition(1, 1);
		tank.setWidth(5);
		tank.setHeight(1);
		
		assertTrue(wall.collidesWith(tank));
	}
	
	@Test
	public void wallHitTankWidthCausesCollision2() {
		wall.setX(4);
		
		tank.setPosition(1, 1);
		tank.setWidth(4);
		tank.setHeight(1);
		
		assertTrue(wall.collidesWith(tank));
	}
	

}
