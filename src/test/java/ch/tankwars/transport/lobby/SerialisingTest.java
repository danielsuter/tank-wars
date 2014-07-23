package ch.tankwars.transport.lobby;

import static org.junit.Assert.*;

import java.lang.reflect.Type;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class SerialisingTest {
	private Gson gson = new Gson();

	@Test
	public void untypedStringPayloadWithAdapter() throws Exception {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Holder2.class, new Holder2Deserializer());
		Gson gson2 = gsonBuilder.create();

		Holder2 holder = new Holder2();
		holder.payload = new Payload2();

		String json = gson.toJson(holder);

		System.out.println(gson2.fromJson(json, Holder2.class));
	}

	private class Holder2Deserializer implements JsonDeserializer<Holder2> {
		public Holder2 deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
			Holder2 holder2 = new Holder2();

			JsonObject jsonObject = json.getAsJsonObject();
			holder2.type = jsonObject.get("type").getAsString();
			JsonElement payload = jsonObject.get("payload");
			Payload2 payload2 = context.deserialize(payload, Payload2.class);
			holder2.payload = payload2;
			
			return holder2;
		}
	}

	@Test
	public void untypedStringPayload() {
		Holder holder = new Holder();
		holder.payload = "{\"attribute2\":\"test\"}";

		String json = gson.toJson(holder);
		System.out.println(json);

		Holder holder2 = gson.fromJson(json, Holder.class);
		System.out.println(holder2.payload);
		Payload2 payload2 = gson.fromJson(holder2.payload, Payload2.class);

		assertEquals("test", payload2.attribute2);
	}

	class Holder2 {
		String type = "COMMAND";
		Object payload;
		
		@Override
		public String toString() {
			return "Holder2 [type=" + type + ", payload=" + payload + "]";
		}
		
	}

	class Holder {
		String type;
		String payload;
	}

	class Payload1 {
		String attribute1 = "test";
	}

	class Payload2 {
		String attribute2 = "test";
	}
}
