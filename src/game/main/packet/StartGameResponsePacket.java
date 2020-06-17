package game.main.packet;

import java.io.Serializable;
import java.util.ArrayList;

import game.main.Player;

public class StartGameResponsePacket implements Serializable {
    private static final long serialVersionUID = 1L;

    public ArrayList<Player> playerInGames;

    public StartGameResponsePacket(ArrayList<Player> playerInGames) {
        this.playerInGames = new ArrayList<>();
        this.playerInGames.addAll(playerInGames);
    }
}