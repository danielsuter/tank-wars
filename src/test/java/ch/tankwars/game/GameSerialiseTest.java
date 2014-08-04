package ch.tankwars.game;

import org.junit.Test;

import ch.tankwars.transport.game.GsonFactory;

import com.google.gson.Gson;

public class GameSerialiseTest {

	@Test
	public void actorMapping() {
		Gson gson = GsonFactory.create();
		
		Game game = new Game();
		Tank tank = game.spawn("Tristana");
		
		gson.toJson(tank);
	}

}
