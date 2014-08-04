package ch.tankwars.transport.game;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	public final static Type ACTOR_LIST_TYPE = new TypeToken<List<Actor>>() {
	}.getType();

	private final static Map<Integer, Actor> CACHE = new HashMap<Integer, Actor>();

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
					int identifier = actor.getActorType().getIdentifier();

					Actor cachedActor = CACHE.get(identifier);

					JsonObject actorJson = new JsonObject();
					actorJson.addProperty("t", identifier);
					actorJson.addProperty("i", actor.getId());

					if (cachedActor == null || cachedActor.getX() != actor.getX()) {
						actorJson.addProperty("x", actor.getX());
					}

					if (cachedActor == null || cachedActor.getY() != actor.getY()) {
						actorJson.addProperty("y", actor.getY());
					}

					if (cachedActor == null || cachedActor.getWidth() != actor.getWidth()) {
						actorJson.addProperty("w", actor.getWidth());
					}
					if (cachedActor == null || cachedActor.getHeight() != actor.getHeight()) {
						actorJson.addProperty("h", actor.getHeight());
					}

					if (cachedActor == null || cachedActor.getDirection() != actor.getDirection()) {
						actorJson.addProperty("d", actor.getDirection().getIdentifier());
					}

					if (cachedActor == null || cachedActor.getVelocity() != actor.getVelocity()) {
						actorJson.addProperty("v", actor.getVelocity());
					}

					CACHE.put(identifier, actor);
					return actorJson;
				}
			}).forEach(json -> returnValue.add(json));

			return returnValue;
		}

	}
}
