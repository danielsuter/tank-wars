package ch.tankwars.transport.game.dto;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import ch.tankwars.transport.game.PlayerPeer;

public class PlayersChangedResponse extends Response {
	
	private List<Player> players = new LinkedList<>();
	
	public PlayersChangedResponse(Collection<PlayerPeer> players) {
		super(MessageType.PLAYERS_CHANGED.getType());
		
		for (PlayerPeer player : players) {
			this.players.add(new Player(player.getTank().getId(), player.getName()));
		}
	}
	
	public List<Player> getPlayers() {
		return players;
	}
	
	public class Player {
		Integer id;
		String name;
		
		public Player(Integer id, String name) {
			super();
			this.id = id;
			this.name = name;
		}
		
		public Integer getId() {
			return id;
		}
		public String getName() {
			return name;
		}
		
		
	}

}
