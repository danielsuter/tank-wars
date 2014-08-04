package ch.tankwars.transport.game.mapper;

import ch.tankwars.transport.game.GsonFactory;

import com.google.gson.Gson;

public class ResponseMapper {
	private Gson gson;
	
	public ResponseMapper() {
		gson = GsonFactory.create();
	}
	
	public String map(Object objectToMap) {
		return gson.toJson(objectToMap);
	}
}
