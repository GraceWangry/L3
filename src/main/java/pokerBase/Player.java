package pokerBase;

import java.util.UUID;

public class Player {

	UUID playerid;
	String playerName;
	int playerPosition;
	
	
	
	public Player(UUID playerid, String playerName, int playerPosition) {
		super();
		this.playerid = playerid;
		this.playerName = playerName;
		this.playerPosition = playerPosition;
	}
	public UUID getPlayerid() {
		return playerid;
	}
	public void setPlayerid(UUID playerid) {
		this.playerid = playerid;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public int getPlayerPosition() {
		return playerPosition;
	}
	public void setPlayerPosition(int playerPosition) {
		this.playerPosition = playerPosition;
	}
	

}
