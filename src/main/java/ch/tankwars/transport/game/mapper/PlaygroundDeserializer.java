package ch.tankwars.transport.game.mapper;

import java.lang.reflect.Type;

import ch.tankwars.game.BattlefieldMap;
import ch.tankwars.game.HealthPowerUp;
import ch.tankwars.game.LaserGunPowerUp;
import ch.tankwars.game.RocketLauncherPowerUp;
import ch.tankwars.game.Wall;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class PlaygroundDeserializer implements JsonDeserializer<BattlefieldMap> {

	@Override
	public BattlefieldMap deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context)
			throws JsonParseException {
		JsonObject playgroundJson = jsonElement.getAsJsonObject();
		int fieldHeight = playgroundJson.getAsJsonPrimitive("fieldHeight").getAsInt();
		int fieldWidth = playgroundJson.getAsJsonPrimitive("fieldWidth").getAsInt();
		
		BattlefieldMap battlefieldMap = new BattlefieldMap(fieldWidth, fieldHeight);
		
		JsonArray powerupsJson = playgroundJson.getAsJsonArray("powerUps");
		
		for (JsonElement powerupJson : powerupsJson) {
			JsonObject jsonObject = powerupJson.getAsJsonObject();
			String actorType = jsonObject.getAsJsonPrimitive("actorType").getAsString();
			
			switch(actorType) {
			case "HEALTHPOWERUP":
				battlefieldMap.addPowerUp(context.deserialize(jsonObject, HealthPowerUp.class));
				break;
			case "LASERGUNPOWERUP":
				battlefieldMap.addPowerUp(context.deserialize(jsonObject, LaserGunPowerUp.class));
				break;
			case "ROCKETLAUNCHERPOWERUP":
				battlefieldMap.addPowerUp(context.deserialize(jsonObject, RocketLauncherPowerUp.class));
				break;
			default:
				throw new RuntimeException("Cannot map " + actorType);
			}
		}
		
		JsonArray wallsJson = playgroundJson.getAsJsonArray("walls");
		wallsJson.forEach(wallJson -> battlefieldMap.addWall(context.deserialize(wallJson, Wall.class)));
		
		return battlefieldMap;
	}

}
