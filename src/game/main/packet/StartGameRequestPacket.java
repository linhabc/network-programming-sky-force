package game.main.packet;

import java.io.Serializable;

public class StartGameRequestPacket implements Serializable {
	private static final long serialVersionUID = 1L;
	
	int level = 1;

    public StartGameRequestPacket(int level) {
        this.level = level;
    }
}
