package pokerBase;

import java.util.ArrayList;
import java.util.UUID;

public class Table {
	UUID tableid;
	ArrayList<Player> tableplayers = new ArrayList<Player>();
	public Table() {
		super();
		tableid = UUID.randomUUID();
	}
	public ArrayList<Player> getTableplayers() {
		return tableplayers;
	}
	public void setTableplayers(ArrayList<Player> tableplayers) {
		this.tableplayers = tableplayers;
	}
	public UUID getTableid() {
		return tableid;
	}
	public void addPlayer (Player P){
		tableplayers.add(P);
	}
	

}
