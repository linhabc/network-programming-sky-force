package game.main.packet;

import java.io.Serializable;

public class PlayerIngameActionPacket implements Serializable {
    public enum  Action {
        LEFT_PRESSED,
        RIGHT_PRESSED,
        FIRE_PRESSED,
        LEFT_RELEASED,
        RIGHT_RELEASED,
        FIRE_RELEASED
    }

    public Action action;
    public int playerId;

    public PlayerIngameActionPacket(Action action) {
        this.action = action;
    }
}
