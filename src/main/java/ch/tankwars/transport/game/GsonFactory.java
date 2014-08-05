package ch.tankwars.transport.game;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import ch.tankwars.game.Actor;
import ch.tankwars.game.Projectile;

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

		gsonBuilder.registerTypeAdapter(ACTOR_LIST_TYPE, new ActorListDeserializer());

		return gsonBuilder.create();
	}

	private static class ActorListDeserializer implements JsonSerializer<List<Actor>> {

		private static final String VELOCITY = "v";
		private static final String DIRECTION = "d";
		private static final String HEIGHT = "h";
		private static final String WIDTH = "w";
		private static final String RADIUS = "r";
		private static final String Y_POSITION = "y";
		private static final String X_POSITION = "x";
		private static final String ID = "i";
		private static final String ACTOR_TYPE = "t";
		
		private static final int GAME_UPDATE = 0;

		@Override
		public JsonElement serialize(List<Actor> toSerialize, Type type, JsonSerializationContext context) {
			JsonArray returnValue = new JsonArray();

			returnValue.add(new JsonPrimitive(GAME_UPDATE));
			toSerialize.parallelStream().map(new Function<Actor, JsonObject>() {
				@Override
				public JsonObject apply(Actor actor) {
					Actor cachedActor = CACHE.get(actor.getId());

					JsonObject actorJson = new JsonObject();
					
					if (cachedActor == null) {
						actorJson.addProperty(ACTOR_TYPE, actor.getActorType().getIdentifier());
					}
					
					actorJson.addProperty(ID, actor.getId());

					if (cachedActor == null || cachedActor.getX() != actor.getX()) {
						actorJson.addProperty(X_POSITION, actor.getX());
					}

					if (cachedActor == null || cachedActor.getY() != actor.getY()) {
						actorJson.addProperty(Y_POSITION, actor.getY());
					}
					
					if (actor instanceof Projectile) {
						Projectile projectile = (Projectile) actor;
						Projectile cachedProjectile = (Projectile) cachedActor;
						if (cachedProjectile == null || cachedProjectile.getProjectileDimension() != projectile.getProjectileDimension()) {
							actorJson.addProperty(RADIUS, projectile.getProjectileDimension());
						}
					} else {
						if (cachedActor == null || cachedActor.getWidth() != actor.getWidth()) {
							actorJson.addProperty(WIDTH, actor.getWidth());
						}
						if (cachedActor == null || cachedActor.getHeight() != actor.getHeight()) {
							actorJson.addProperty(HEIGHT, actor.getHeight());
						}
					}

					if (cachedActor == null || cachedActor.getDirection() != actor.getDirection()) {
						actorJson.addProperty(DIRECTION, actor.getDirection().getIdentifier());
					}

					if (cachedActor == null || cachedActor.getVelocity() != actor.getVelocity()) {
						actorJson.addProperty(VELOCITY, actor.getVelocity());
					}
					
					putToCache(actor);
					
					return actorJson;
				}

				private void putToCache(Actor actor) {
					try {
						CACHE.put(actor.getId(), actor.clone());
					} catch (CloneNotSupportedException e) {
						throw new RuntimeException(e);
					}
				}
			}).forEach(json -> returnValue.add(json));

			return returnValue;
		}

	}
}