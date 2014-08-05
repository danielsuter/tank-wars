package ch.tankwars.transport.game.mapper;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonFactory {


	public static Gson create() {
		GsonBuilder gsonBuilder = new GsonBuilder();

		gsonBuilder.registerTypeAdapter(ActorListDeserializer.TYPE, new ActorListDeserializer());
		
		gsonBuilder.setExclusionStrategies(new ExclusionStrategy() {
			
			@Override
			public boolean shouldSkipField(FieldAttributes fieldAttributes) {
				return "actorListener".equals(fieldAttributes.getName());
			}
			
			@Override
			public boolean shouldSkipClass(Class<?> arg0) {
				return false;
			}
		});
		
		return gsonBuilder.create();
	}

}
