package game.main.server;

import java.io.Serializable;

public class ClientInGame implements Serializable {
    private static final long serialVersionUID = 1L;
    public int id;
    public String name;

    public ClientInGame() {
    }

    public ClientInGame(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
