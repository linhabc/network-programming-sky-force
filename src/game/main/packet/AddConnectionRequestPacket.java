package game.main.packet;

import java.io.Serializable;

public class AddConnectionRequestPacket implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public int id;

	public String playerName;

    public boolean isMaster;

	public AddConnectionRequestPacket() {
	}

	public AddConnectionRequestPacket(int id, String playerName) {
		this.id = id;
		this.playerName = playerName;
	}

	public AddConnectionRequestPacket(String playerName) {
		this.playerName = playerName;
	}
	
    public AddConnectionRequestPacket(String playerName, boolean isMaster) {
        this.playerName = playerName;
        this.isMaster = isMaster;
    }
}
