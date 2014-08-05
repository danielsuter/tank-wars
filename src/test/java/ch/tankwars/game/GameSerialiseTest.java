package ch.tankwars.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import ch.tankwars.transport.game.dto.JoinResponse;
import ch.tankwars.transport.game.mapper.ActorListDeserializer;
import ch.tankwars.transport.game.mapper.GsonFactory;
import ch.tankwars.transport.game.mapper.ResponseMapper;

import com.google.gson.Gson;

public class GameSerialiseTest {

	private static final int TICKS_PER_SECOND = 10;
	private static final int MAX_SIZE_BYTES = 4000;

	private ResponseMapper mapper = new ResponseMapper();
	private Gson gson = GsonFactory.create();
	
	@Test
	public void actorMapping() {
		
		Game game = new Game();
		Tank tank = game.spawn("Tristana");
		
		gson.toJson(tank);
	}

	@Test
	public void assertSize() {
		
		Game game = new Game();
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
			mapper.map(game.getActors(), ActorListDeserializer.TYPE);
		}
		
		game.tick();
		List<Actor> actors = game.getActors();
		String response = mapper.map(actors, ActorListDeserializer.TYPE);
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
		Game game = new Game();
		Tank tank = game.spawn("Lux");
		tank.setPosition(0, 0);
		tank.setVelocity(1);
		tank.setDirection(Direction.DOWN);
		mapper.map(game.getActors(), ActorListDeserializer.TYPE);
		
		game.tick();

		String response2 = mapper.map(game.getActors(), ActorListDeserializer.TYPE);
		assertEquals("[0,{\"i\":1,\"y\":1}]", response2);
		
	}
}
