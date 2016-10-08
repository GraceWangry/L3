package pokerBase;

import java.util.ArrayList;
import java.util.UUID;

public class Game {
	UUID gameid;
	ArrayList<Player> gameplayers = new ArrayList<Player>();
	public Game() {
		super();
		gameid = UUID.randomUUID();
				
	}
	public ArrayList<Player> getTableplayers() {
		return gameplayers;
	}
	public void setTableplayers(ArrayList<Player> tableplayers) {
		gameplayers = tableplayers;
	}
	public UUID getGameid() {
		return gameid;
	}
	public void addPlayer (Player P){
		gameplayers.add(P);
	}
	

}
