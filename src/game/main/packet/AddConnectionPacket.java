package game.main.packet;

import java.io.Serializable;

public class AddConnectionPacket implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public int id;

	public String playerName;

	public AddConnectionPacket() {
	}

	public AddConnectionPacket(int id, String playerName) {
		this.id = id;
		this.playerName = playerName;
	}

	public AddConnectionPacket(String playerName) {
		this.playerName = playerName;
	}
}
