package ch.tankwars.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.tankwars.transport.game.mapper.ActorListSerializer;
import ch.tankwars.transport.game.mapper.GsonFactory;
import ch.tankwars.transport.game.mapper.ResponseMapper;

import com.google.gson.Gson;

public class GameSerialiseTest {

	private static final int TICKS_PER_SECOND = 10;
	private static final int MAX_SIZE_BYTES = 4000;

	private ResponseMapper mapper;
	private Gson gson;
	
	private Game game;
	
	@Before
	public void setUp() {
		game = new Game();
		game.setPlayGround(new BattlefieldMap(1000, 1000));
		mapper = new ResponseMapper();
		gson = GsonFactory.create();
	}
	
	@Test
	public void actorMapping() {
		Tank tank = game.spawn("Tristana");
		
		gson.toJson(tank);
	}

	@Test
	public void playgroundMapping() {
		BattlefieldMap battlefieldMap = new BattlefieldMap(1000, 1000);
		battlefieldMap.addPowerUp(new FireRatePowerUp(0, 0));
		battlefieldMap.addWall(new Wall(0, 0, 100, 100));
		
		gson.toJson(battlefieldMap);
	}
	
	@Test
	public void assertSize() {
		List<Tank> tanks = new LinkedList<Tank>();
		for(int i = 0; i< 4; i++) {
			Tank tank = game.spawn("player " + i);
			tanks.add(tank);
			tank.setPosition(i * tank.getWidth(), 0);
			tank.setVelocity(1);
			tank.setDirection(Direction.DOWN);
		}
		
		for(int i = 0; i < 5; i++) {
			tanks.stream().forEach(t -> t.shoot());
			game.tick();
			// Allow caching
			mapper.map(game.getActors(), ActorListSerializer.TYPE);
		}
		
		game.tick();
		List<Actor> actors = game.getActors();
		String response = mapper.map(actors, ActorListSerializer.TYPE);
		long actualSize = getSizeInBytes(response) * TICKS_PER_SECOND;
		assertTrue("Expected size to be below " + MAX_SIZE_BYTES + " but was " + actualSize ,actualSize < MAX_SIZE_BYTES);
	}

	private int getSizeInBytes(String string) {
		try {
			return string.getBytes("UTF-8").length;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Test
	public void assertDeltaRecogniztion() {
		Tank tank = game.spawn("Lux");
		tank.setPosition(0, 0);
		tank.setVelocity(1);
		tank.setDirection(Direction.DOWN);
		game.tick();
		
		mapper.map(game.getActors(), ActorListSerializer.TYPE);
		
		game.tick();

		String response2 = mapper.map(game.getActors(), ActorListSerializer.TYPE);
		assertEquals("[0,{\"i\":1,\"y\":2}]", response2);
		
	}
	
	@Test
	public void assertResponse() {
		Tank tank = game.spawn("Lux");
		tank.setPosition(0, 0);
		tank.setVelocity(1);
		tank.setDirection(Direction.DOWN);
		tank.shoot();
		game.tick();
		
		String response = mapper.map(game.getActors(), ActorListSerializer.TYPE);
		assertEquals("[0,{\"t\":0,\"i\":1,\"x\":0,\"y\":1,\"w\":35,\"h\":35,\"d\":\"S\",\"v\":1,\"f\":1,\"l\":100,\"s\":0,\"k\":0},"
				+ "{\"t\":1,\"i\":2,\"x\":16,\"y\":26,\"w\":3,\"h\":3,\"r\":3,\"d\":\"S\",\"v\":10}]", response);
	}
}
