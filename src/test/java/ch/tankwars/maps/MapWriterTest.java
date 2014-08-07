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

	private static final int WALL_THICKNESS = 10;
	private static final int GAME_WIDTH = 800;
	private static final int GAME_HEIGHT = 600;
	private static final String MAP_PATH = "src/main/resources/maps/";
	private Gson gson;
	private BattlefieldMap battlefieldMap;

	@Before
	public void init() {
		gson = GsonFactory.create();
	}
	
	@Test
	public void howlingAbyss() throws Exception {
		int howlingAbyssWidth = 1400;
		int howlingAbyssHeight = 500;
		battlefieldMap = new BattlefieldMap(howlingAbyssWidth, howlingAbyssHeight);
		
		
		// OUTER WALLS
		addWallToBattleField(0, 0, WALL_THICKNESS, howlingAbyssHeight);
		addWallToBattleField(howlingAbyssWidth - WALL_THICKNESS, 0, WALL_THICKNESS, howlingAbyssHeight);
		addWallToBattleField(WALL_THICKNESS, 0, howlingAbyssWidth - 2 * WALL_THICKNESS, WALL_THICKNESS);
		addWallToBattleField(WALL_THICKNESS, howlingAbyssHeight - WALL_THICKNESS, howlingAbyssWidth - 2 * WALL_THICKNESS, WALL_THICKNESS);

		
		addWallToBattleField(400, WALL_THICKNESS, WALL_THICKNESS, 50);
		addWallToBattleField(howlingAbyssWidth - 400, WALL_THICKNESS, WALL_THICKNESS, 50);
		
		addWallToBattleField(400, howlingAbyssHeight - WALL_THICKNESS - 50, WALL_THICKNESS, 50);
		addWallToBattleField(howlingAbyssWidth - 400, howlingAbyssHeight - WALL_THICKNESS - 50, WALL_THICKNESS, 50);
		
		addWallToBattleField(550, 100, WALL_THICKNESS, 100);
		addWallToBattleField(550, howlingAbyssHeight - 100 - 100, WALL_THICKNESS, 100);
		addWallToBattleField(550, 100, 100, WALL_THICKNESS);
		addWallToBattleField(550, howlingAbyssHeight - 100, 100, WALL_THICKNESS);
		
		addWallToBattleField(850, 100, WALL_THICKNESS, 100);
		addWallToBattleField(850, howlingAbyssHeight - 100 - 100, WALL_THICKNESS, 100 + WALL_THICKNESS);
		addWallToBattleField(750, 100, 100, WALL_THICKNESS);
		addWallToBattleField(750, howlingAbyssHeight - 100, 100  + WALL_THICKNESS, WALL_THICKNESS);
		
		
		addWallToBattleField(50, 145, 250, WALL_THICKNESS);
		addWallToBattleField(50, 355, 250, WALL_THICKNESS);
		addWallToBattleField(howlingAbyssWidth - 50 - 250, 145, 250, WALL_THICKNESS);
		addWallToBattleField(howlingAbyssWidth - 50 - 250, 355, 250, WALL_THICKNESS);
		
		addWallToBattleField(150, 225, 50, 50);
		addWallToBattleField(howlingAbyssWidth - 150 -50, 225, 50, 50);

		String mapAsJson = gson.toJson(battlefieldMap);
		writeMap("howlingAbyss.json", mapAsJson);
	}

	@Test
	public void labyrinth() throws Exception {
		battlefieldMap = new BattlefieldMap(GAME_WIDTH, GAME_HEIGHT);
		
		// OUTER WALLS
		addWallToBattleField(0, 0, WALL_THICKNESS, GAME_HEIGHT);
		addWallToBattleField(GAME_WIDTH - WALL_THICKNESS, 0, WALL_THICKNESS, GAME_HEIGHT);
		addWallToBattleField(WALL_THICKNESS, 0, GAME_WIDTH - 2 * WALL_THICKNESS, WALL_THICKNESS);
		addWallToBattleField(WALL_THICKNESS, GAME_HEIGHT - WALL_THICKNESS, GAME_WIDTH - 2 * WALL_THICKNESS, WALL_THICKNESS);

		addWallToBattleField(100, 100, 600, WALL_THICKNESS);
		addWallToBattleField(100, 400, 600, WALL_THICKNESS);
		addWallToBattleField(150, 130, WALL_THICKNESS, 220);
		addWallToBattleField(600, 130, WALL_THICKNESS, 220);
		addWallToBattleField(300, 300, 100, WALL_THICKNESS);

		String mapAsJson = gson.toJson(battlefieldMap);
		writeMap("labyrinth.json", mapAsJson);
	}

	@Test
	public void defaultMap() throws Exception {
				battlefieldMap = new BattlefieldMap(GAME_WIDTH, GAME_HEIGHT);
		addWallToBattleField(5, 5, 20, 100);
		addWallToBattleField(50, 89, 200, 10);
		addWallToBattleField(600, 411, 50, 50);
		addWallToBattleField(555, 44, 80, 20);

		String mapAsJson = gson.toJson(battlefieldMap);
		writeMap("default.json", mapAsJson);
	}
	
	private void addWallToBattleField(int x, int y, int width, int height) {
		battlefieldMap.addWall(new Wall(x, y, width, height));
	}

	private void writeMap(String mapName, String json) throws IOException {
		File file = new File(MAP_PATH, mapName);
		FileWriter writer = new FileWriter(file);
		writer.write(json);
		writer.close();
	}
}