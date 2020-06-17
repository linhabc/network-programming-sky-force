package game.main.packet;

import java.io.Serializable;

public class AddPositionPlayerRequestPacket implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public int x;
	public int y;
	
	public AddPositionPlayerRequestPacket() {
		
	}
	
	public AddPositionPlayerRequestPacket(int x, int y) {
		this.x = x;
		this.y = y;
	}

}
