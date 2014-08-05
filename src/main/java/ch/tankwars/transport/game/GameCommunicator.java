package ch.tankwars.transport.game;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.tankwars.performance.PerformanceCounter;
import ch.tankwars.transport.game.mapper.ResponseMapper;

public class GameCommunicator {
	private final static Logger LOGGER = LoggerFactory.getLogger(GameCommunicator.class);

	private final ResponseMapper responseMapper;
	
	private PerformanceCounter perf = new PerformanceCounter(10);
	
	public GameCommunicator() {
		responseMapper = new ResponseMapper();
	}

	public void sendMessage(Object objectToBroadcast, Set<PlayerPeer> peers, Type type) {
		perf.start();
		
		String gameAsJson = responseMapper.map(objectToBroadcast, type);
		
		perf.lap("JSON TIME");
		
		peers.parallelStream().forEach(peer -> sendResponse(peer, gameAsJson));
		
		perf.stop("COMPLETE TIME ({0,number} characters)", gameAsJson.length());
	}

	public void sendMessage(Object objectToBroadcast, PlayerPeer playerPeer) {
		sendResponse(playerPeer, responseMapper.map(objectToBroadcast));
	}
	
	public void sendMessage(Object objectToBroadcast, Set<PlayerPeer> peers) {
		peers.parallelStream().forEach(peer -> sendResponse(peer, responseMapper.map(objectToBroadcast)));
	}
	
	private void sendResponse(PlayerPeer peer, String response) {
		try {
			peer.getSession().getBasicRemote().sendText(response);
		} catch (IOException | IllegalStateException e) {
			LOGGER.error(e.toString(), e);
		}
	}

}
