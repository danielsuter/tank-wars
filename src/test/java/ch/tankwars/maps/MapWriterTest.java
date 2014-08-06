package ch.tankwars.maps;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import ch.tankwars.game.FireRatePowerUp;
import ch.tankwars.game.Game;
import ch.tankwars.game.HealthPowerUp;
import ch.tankwars.game.LaserGunPowerUp;
import ch.tankwars.game.PlayGround;
import ch.tankwars.game.PowerUp;
import ch.tankwars.game.RocketLauncherPowerUp;
import ch.tankwars.transport.game.mapper.GsonFactory;

import com.google.gson.Gson;

public class MapWriterTest {
	
	private static final String MAP_PATH = "src/main/resources/maps/";
	private Gson gson;
	
	@Before
	public void init() {
		gson = GsonFactory.create();
	}
	
	@Test
	public void defaultMap() throws Exception {
		PlayGround playGround = new PlayGround(Game.GAME_WIDTH, Game.GAME_HEIGHT);
		Game game = new Game();
		game.setPlayGround(playGround);
		game.addWall(5, 5, 20, 100);
		game.addWall(50, 89, 200, 10);
		game.addWall(600, 411, 50, 50);
		game.addWall(555, 44, 80, 20);
		
		playGround.addPowerUp(addHealthPowerUp(200, 200));
		playGround.addPowerUp(addHealthPowerUp(400, 300));
		playGround.addPowerUp(addFireRatePowerUp(100, 100));
		playGround.addPowerUp(addFireRatePowerUp(500, 580));
		
		playGround.addPowerUp(new RocketLauncherPowerUp(250, 250));
		playGround.addPowerUp(new LaserGunPowerUp(430, 70));
		
		String mapAsJson = gson.toJson(playGround);
		writeMap("default.json", mapAsJson);
	}
	
	private void writeMap(String mapName, String json) throws IOException {
		File file = new File(MAP_PATH, mapName);
		FileWriter writer = new FileWriter(file);
		writer.write(json);
		writer.close();
	}
	
	private PowerUp addHealthPowerUp(int x, int y) {
		return new HealthPowerUp(x, y);
	}
	
	private PowerUp addFireRatePowerUp(int x, int y) {
		return new FireRatePowerUp(x, y);
	}
}
