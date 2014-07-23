package ch.tankwars.transport.lobby;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.gson.Gson;

public class SerialisingTest {
	private Gson gson = new Gson();
	
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
