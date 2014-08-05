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
import ch.tankwars.game.BattlefieldMap;
import ch.tankwars.game.PowerUp;
import ch.tankwars.game.RocketLauncherPowerUp;
import ch.tankwars.game.Wall;
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
		BattlefieldMap battlefieldMap = new BattlefieldMap(Game.GAME_WIDTH, Game.GAME_HEIGHT);
		Game game = new Game();
		game.setPlayGround(battlefieldMap);
		battlefieldMap.addWall(new Wall( 5, 5, 20, 100));
		battlefieldMap.addWall(new Wall(50, 89, 200, 10));
		battlefieldMap.addWall(new Wall(600, 411, 50, 50));
		battlefieldMap.addWall(new Wall(555, 44, 80, 20));
		
		battlefieldMap.addPowerUp(addHealthPowerUp(200, 200));
		battlefieldMap.addPowerUp(addHealthPowerUp(400, 300));
		battlefieldMap.addPowerUp(addFireRatePowerUp(100, 100));
		battlefieldMap.addPowerUp(addFireRatePowerUp(500, 580));
		
		battlefieldMap.addPowerUp(new RocketLauncherPowerUp(250, 250));
		battlefieldMap.addPowerUp(new LaserGunPowerUp(430, 70));
		
		String mapAsJson = gson.toJson(battlefieldMap);
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
