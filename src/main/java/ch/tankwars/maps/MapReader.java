package ch.tankwars.maps;

import java.io.InputStream;
import java.io.InputStreamReader;

import ch.tankwars.game.BattlefieldMap;
import ch.tankwars.transport.game.mapper.GsonFactory;

import com.google.gson.Gson;

public class MapReader {
	private Gson gson;
	
	public MapReader() {
		gson = GsonFactory.create();
	}
	
	public BattlefieldMap load(String mapName) {
		InputStream inputStream = MapReader.class.getResourceAsStream("/maps/" + mapName);
		return loadPlayground(inputStream);
	}

	private BattlefieldMap loadPlayground(InputStream inputStream) {
		InputStreamReader reader = new InputStreamReader(inputStream);
		return gson.fromJson(reader, BattlefieldMap.class);
	}
}
