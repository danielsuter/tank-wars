package ch.tankwars.maps;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import ch.tankwars.game.BattlefieldMap;
import ch.tankwars.game.Game;
import ch.tankwars.game.Wall;
import ch.tankwars.game.powerup.HealthPowerUp;
import ch.tankwars.game.powerup.LaserGunPowerUp;
import ch.tankwars.game.powerup.RocketLauncherPowerUp;
import ch.tankwars.transport.game.mapper.GsonFactory;

import com.google.gson.Gson;

public class MapWriterTest {

	private static final int WALL_WIDTH = 10;
	private static final String MAP_PATH = "src/main/resources/maps/";
	private Gson gson;

	@Before
	public void init() {
		gson = GsonFactory.create();
	}

	@Test
	public void labyrinth() throws Exception {
		BattlefieldMap battlefieldMap = new BattlefieldMap(Game.GAME_WIDTH, Game.GAME_HEIGHT);

		Wall leftWall = new Wall(0, 0, WALL_WIDTH, Game.GAME_HEIGHT);
		Wall rightWall = new Wall(Game.GAME_WIDTH - WALL_WIDTH, 0, WALL_WIDTH, Game.GAME_HEIGHT);
		Wall topWall = new Wall(WALL_WIDTH, 0, Game.GAME_WIDTH - 2 * WALL_WIDTH, WALL_WIDTH);
		Wall bottomWall = new Wall(WALL_WIDTH, Game.GAME_HEIGHT-WALL_WIDTH, Game.GAME_WIDTH - 2 * WALL_WIDTH, WALL_WIDTH);
		
		battlefieldMap.addWall(leftWall);
		battlefieldMap.addWall(rightWall);
		battlefieldMap.addWall(topWall);
		battlefieldMap.addWall(bottomWall);

		
		battlefieldMap.addWall(new Wall(100, 100, 600, WALL_WIDTH));
		battlefieldMap.addWall(new Wall(100, 400, 600, WALL_WIDTH));
		
		battlefieldMap.addWall(new Wall(150, 130, WALL_WIDTH, 240));
		battlefieldMap.addWall(new Wall(600, 130, WALL_WIDTH, 240));
		
		battlefieldMap.addWall(new Wall(300, 300, 100, WALL_WIDTH));
		
		
		battlefieldMap.addPowerUp(new HealthPowerUp(200, 200));
		battlefieldMap.addPowerUp(new HealthPowerUp(400, 300));

		battlefieldMap.addPowerUp(new RocketLauncherPowerUp(250, 250));
		battlefieldMap.addPowerUp(new LaserGunPowerUp(430, 70));

		String mapAsJson = gson.toJson(battlefieldMap);
		writeMap("labyrinth.json", mapAsJson);
	}

	@Test
	public void defaultMap() throws Exception {
		BattlefieldMap battlefieldMap = new BattlefieldMap(Game.GAME_WIDTH, Game.GAME_HEIGHT);
		battlefieldMap.addWall(new Wall(5, 5, 20, 100));
		battlefieldMap.addWall(new Wall(50, 89, 200, 10));
		battlefieldMap.addWall(new Wall(600, 411, 50, 50));
		battlefieldMap.addWall(new Wall(555, 44, 80, 20));

		battlefieldMap.addPowerUp(new HealthPowerUp(200, 200));
		battlefieldMap.addPowerUp(new HealthPowerUp(400, 300));

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
}
