package ch.tankwars.transport.game;

import java.lang.reflect.Type;
import java.util.List;
import java.util.function.Function;

import ch.tankwars.game.Actor;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

public class GsonFactory {
	public final static Type ACTOR_LIST_TYPE = new TypeToken<List<Actor>>() {}.getType();
	
	public static Gson create() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.addSerializationExclusionStrategy(new ExclusionStrategy() {
			
			@Override
			public boolean shouldSkipField(FieldAttributes fieldAttributes) {
				return "actorListener".equals(fieldAttributes.getName());
			}
			
			@Override
			public boolean shouldSkipClass(Class<?> clazz) {
				return false;
			}
		});
		
		gsonBuilder.registerTypeAdapter(ACTOR_LIST_TYPE, new ActorListDeserializer());
		
		return gsonBuilder.create();
	}
	
	
	private static class ActorListDeserializer implements JsonSerializer<List<Actor>> {

		private static final int GAME_UPDATE = 0;

		@Override
		public JsonElement serialize(List<Actor> toSerialize, Type type, JsonSerializationContext context) {
			JsonArray returnValue = new JsonArray();
			
			returnValue.add(new JsonPrimitive(GAME_UPDATE));
			
			toSerialize.parallelStream().map(new Function<Actor, JsonObject>() {
				@Override
				public JsonObject apply(Actor actor) {
					JsonObject actorJson = new JsonObject();
					actorJson.addProperty("t", actor.getActorType().getIdentifier());
					actorJson.addProperty("i", actor.getId());
					
					actorJson.addProperty("x", actor.getX());
					actorJson.addProperty("y", actor.getY());
					
					actorJson.addProperty("w", actor.getWidth());
					actorJson.addProperty("h", actor.getHeight());
					actorJson.addProperty("d", actor.getDirection().getIdentifier());
					actorJson.addProperty("v", actor.getVelocityX()); // TODO not working
					
					return actorJson;
				}
			}).forEach(json -> returnValue.add(json));
			
			return returnValue;
		}
		
	}
}
