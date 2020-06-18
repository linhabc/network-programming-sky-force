package game.main.packet;

import java.io.Serializable;

public class StartGameResponsePacket implements Serializable {
	
    private static final long serialVersionUID = 1L;

    public String title;
    public int width;
    public int height;

    public StartGameResponsePacket(String title, int width, int height) {
    	this.title = title;
        this.width = width;
        this.height = height;
    }
}