package ch.tankwars.transport.game.mapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonFactory {


	public static Gson create() {
		GsonBuilder gsonBuilder = new GsonBuilder();

		gsonBuilder.registerTypeAdapter(ActorListDeserializer.TYPE, new ActorListDeserializer());

		return gsonBuilder.create();
	}

}
