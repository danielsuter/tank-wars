package ch.tankwars.example;

import org.junit.Test;

import ch.tankwars.transport.lobby.LobbyEndpoint;

import com.google.gson.Gson;

public class GSonTest {
	
	@Test
	public void serialise() throws Exception {
		Gson gson = new Gson();
		LobbyEndpoint lobbyEndpoint = new LobbyEndpoint();
		
		serialiseObject(gson, lobbyEndpoint, 100);
		
		long startMilis = System.currentTimeMillis();
		serialiseObject(gson, lobbyEndpoint, 10000);
		long endMilis = System.currentTimeMillis();
		System.out.println("Time used (ms): " + (endMilis - startMilis));
	}

	private void serialiseObject(Gson gson, LobbyEndpoint lobbyEndpoint, int numberOfTimes) {
		for(int i = 0; i < numberOfTimes; i++) {
			gson.toJson(lobbyEndpoint);
		}
	}
}
