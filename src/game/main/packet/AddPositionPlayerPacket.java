package game.main.packet;

import java.io.Serializable;

public class AddPositionPlayerPacket implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public int x;
	public int y;
	
	public AddPositionPlayerPacket() {
		
	}
	
	public AddPositionPlayerPacket(int x, int y) {
		this.x = x;
		this.y = y;
	}

}
