package ch.tankwars.transport.game.mapper;

import java.lang.reflect.Type;

import com.google.gson.Gson;

public class ResponseMapper {
	private Gson gson;
	
	public ResponseMapper() {
		gson = GsonFactory.create();
	}
	
	public String map(Object objectToMap) {
		return gson.toJson(objectToMap);
	}
	
	public String map(Object objectToMap, Type type) {
		return gson.toJson(objectToMap, type);
	}
}
