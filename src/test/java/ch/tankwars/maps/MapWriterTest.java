package ch.tankwars.maps;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import ch.tankwars.game.BattlefieldMap;
import ch.tankwars.game.Game;
import ch.tankwars.game.Wall;
import ch.tankwars.transport.game.mapper.GsonFactory;

import com.google.gson.Gson;

public class MapWriterTest {

	private static final int WALL_WIDTH = 10;
	private static final String MAP_PATH = "src/main/resources/maps/";
	private Gson gson;
	private BattlefieldMap battlefieldMap;

	@Before
	public void init() {
		battlefieldMap = new BattlefieldMap(Game.GAME_WIDTH, Game.GAME_HEIGHT);
		gson = GsonFactory.create();
	}

	@Test
	public void labyrinth() throws Exception {
		// OUTER WALLS
		addWallToBattleField(0, 0, WALL_WIDTH, Game.GAME_HEIGHT);
		addWallToBattleField(Game.GAME_WIDTH - WALL_WIDTH, 0, WALL_WIDTH, Game.GAME_HEIGHT);
		addWallToBattleField(WALL_WIDTH, 0, Game.GAME_WIDTH - 2 * WALL_WIDTH, WALL_WIDTH);
		addWallToBattleField(WALL_WIDTH, Game.GAME_HEIGHT - WALL_WIDTH, Game.GAME_WIDTH - 2 * WALL_WIDTH, WALL_WIDTH);

		addWallToBattleField(100, 100, 600, WALL_WIDTH);
		addWallToBattleField(100, 400, 600, WALL_WIDTH);

		addWallToBattleField(150, 130, WALL_WIDTH, 220);
		addWallToBattleField(600, 130, WALL_WIDTH, 220);

		addWallToBattleField(300, 300, 100, WALL_WIDTH);

		String mapAsJson = gson.toJson(battlefieldMap);
		writeMap("labyrinth.json", mapAsJson);
	}

	private void addWallToBattleField(int x, int y, int width, int height) {
		battlefieldMap.addWall(new Wall(x, y, width, height));
	}

	@Test
	public void defaultMap() throws Exception {
		addWallToBattleField(5, 5, 20, 100);
		addWallToBattleField(50, 89, 200, 10);
		addWallToBattleField(600, 411, 50, 50);
		addWallToBattleField(555, 44, 80, 20);

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