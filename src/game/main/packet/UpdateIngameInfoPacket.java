package game.main.packet;

import java.io.Serializable;
import java.util.ArrayList;

import game.main.Bullet;
import game.main.Enemy;
import game.main.Player;

public class UpdateIngameInfoPacket implements Serializable {
    public ArrayList<Player> playerInGames;
    public ArrayList<Bullet> bullets;
    public ArrayList<Enemy> enemies;

    public UpdateIngameInfoPacket(ArrayList<Player> playerInGames, ArrayList<Bullet> bullets, ArrayList<Enemy> enemies) {
        this.playerInGames = new ArrayList<>();
        this.bullets = new ArrayList<>();
        this.enemies = new ArrayList<>();

        this.playerInGames.addAll(playerInGames);
        this.bullets.addAll(bullets);
        this.enemies.addAll(enemies);
    }
}