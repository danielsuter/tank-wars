package ch.tankwars.maps;

import java.io.InputStream;
import java.io.InputStreamReader;

import ch.tankwars.game.PlayGround;
import ch.tankwars.transport.game.GameController;
import ch.tankwars.transport.game.mapper.GsonFactory;

import com.google.gson.Gson;

public class MapReader {
	private Gson gson;
	
	public MapReader() {
		gson = GsonFactory.create();
	}
	
	public PlayGround load(String mapName) {
		InputStream inputStream = GameController.class.getResourceAsStream("/maps/" + mapName);
		return loadPlayground(inputStream);
	}

	private PlayGround loadPlayground(InputStream inputStream) {
		InputStreamReader reader = new InputStreamReader(inputStream);
		return gson.fromJson(reader, PlayGround.class);
	}
}
