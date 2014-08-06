package ch.tankwars.maps;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.junit.Before;
import org.junit.Test;

import ch.tankwars.game.BattlefieldMap;
import ch.tankwars.transport.game.mapper.GsonFactory;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class MapReaderTest {
	
	private static final String MAP_PATH = "src/main/resources/maps/";
	private Gson gson;
	
	@Before
	public void init() {
		gson = GsonFactory.create();
	}
	
	@Test
	public void readDefaultPlayground() throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		File file = new File(MAP_PATH, "default.json");
		
		BattlefieldMap battlefieldMap = gson.fromJson(new FileReader(file), BattlefieldMap.class);
		
		assertEquals(600, battlefieldMap.getFieldHeight());
		assertEquals(800, battlefieldMap.getFieldWidth());
		assertTrue(battlefieldMap.getPowerUps().size() > 0);
	}
}
