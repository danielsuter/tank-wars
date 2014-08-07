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
			public void createActor(Actor actor) {
			}
			
			@Override
			public boolean createActorIfNoCollision(Actor actor) {
				return false;
			}
		};
		
		tank = new Tank(al, "Fritzli", null);
		wall = new Wall(1, 1, 1, 1);
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
		wall.setX(4);
		
		tank.setPosition(1, 1);
		tank.setWidth(4);
		tank.setHeight(1);
		
		assertTrue(wall.collidesWith(tank));
	}
	
	@Test
	public void wallHitTankHeightCausesCollision() {
		wall.setY(4);
		
		tank.setPosition(1, 1);
		tank.setWidth(1);
		tank.setHeight(4);
		
		assertTrue(wall.collidesWith(tank));
	}
	
	@Test
	public void wallWidthHitTankCausesCollision() {
		wall.setWidth(4);
		
		tank.setPosition(2, 1);
		tank.setWidth(1);
		tank.setHeight(1);
		
		assertTrue(wall.collidesWith(tank));
	}
	
	@Test
	public void tankSizeCausesCollision() {
		wall.setWidth(3);
		wall.setHeight(4);
		wall.setPosition(4, 3);
		
		tank.setPosition(3, 2);
		tank.setWidth(2);
		tank.setHeight(2);
		
		assertTrue(wall.collidesWith(tank));
	}
	
	@Test
	public void wallSizeCausesCollisionBottomRight() {
		wall.setWidth(3);
		wall.setHeight(4);
		wall.setPosition(4, 3);
		
		tank.setPosition(6, 5);
		tank.setWidth(2);
		tank.setHeight(2);
		
		assertTrue(wall.collidesWith(tank));
	}
	
	@Test
	public void wallSizeCausesCollisionTopRight() {
		wall.setWidth(3);
		wall.setHeight(4);
		wall.setPosition(4, 3);
		
		tank.setPosition(3, 5);
		tank.setWidth(2);
		tank.setHeight(2);
		
		assertTrue(wall.collidesWith(tank));
	}
	

}
