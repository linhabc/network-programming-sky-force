package game.main;

import game.main.packet.UpdateIngameInfoPacket;
import game.main.server.Room;
import game.main.server.Server;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class GameManager {
    private int numberOfPlayer;
    public static ArrayList<Player> players;
    public static ArrayList<Bullet> bullets;
    public static ArrayList<Enemy> enemies;

    private long current;
    private long delay;
    
    public GameManager(int numOfPlayer) {
    	this.numberOfPlayer = numOfPlayer;
    }

    public void init() {
        players = new ArrayList<>();
        for(int i = 0; i< numberOfPlayer; i++){
            int distance = (Config.GAME_WIDTH) / numberOfPlayer;
            int position = i;
            System.out.println(numberOfPlayer);
            Player player = new Player(33 + distance / 2 + (position*distance),
                    Config.GAME_HEIGHT + 20, Room.clients.get(i).id, i);
            player.init();
            players.add(player);
        }

        bullets = new ArrayList<>();
        enemies = new ArrayList<>();

        current = System.nanoTime();
        delay = 800;
    }

    public void tick() {
        for(Player player: players){
            player.tick();
        }

        for (Bullet bullet : bullets) {
            bullet.tick();
        }

        long breaks = (System.nanoTime() - current)/1000000;
        if (breaks > delay) {
            for (int i = 0; i < 2; i++) {
                Random rand = new Random();
                int randX = rand.nextInt(450);
                int randY = rand.nextInt(450);
                enemies.add(new Enemy(randX, -randY));
            }
            current = System.nanoTime();
        }


        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).tick();
        }
        
        removeObjects();
        
    }
    

    private void removeObjects() {
        bullets = bullets.stream().filter(bullet -> bullet.getY() > 50)
                .collect(Collectors.toCollection(ArrayList::new));

        Set<Player> playerInGameSet = new HashSet<>();
        Set<Enemy> enemySet = new HashSet<>();
        Set<Bullet> bulletSet = new HashSet<>();

        for (Enemy e : enemies) {
            for (Player playerInGame : players) {
                if (isCollision(playerInGame, e)) {
                    enemySet.add(e);
                    playerInGame.setHealth(playerInGame.getHealth() - 1);
                    System.out.println("P" + playerInGame.getPosition() + " : " + playerInGame.getHealth());
                    if (playerInGame.getHealth() <= 0) {
                        System.out.println("P" + playerInGame.getPosition() + " : " + "Died");
                        playerInGameSet.add(playerInGame);
                    }
                }
            }

            players.removeAll(playerInGameSet);
            if (players.isEmpty()) {
                break;
            }

            for (Bullet b : bullets) {
                if (isCollision(e, b)) {
                    enemySet.add(e);
                    bulletSet.add(b);
                    players.get(0).incScore(); // TODO calculate score of each player
                }
            }
        }

        if (players.isEmpty()) {
            // enemies.clear();
            System.out.println("Loss");
        }

        enemies.removeAll(enemySet);
        bullets.removeAll(bulletSet);
    }

    public void render(Graphics g) {
    	for(Player player: players) {
            player.render(g);
            
            for (int i = 0; i < enemies.size(); i++) {
                Enemy e = enemies.get(i);
                if (isCollision(player, e)) {
                    enemies.remove(i);
                    i--;
                    player.setHealth(player.getHealth() - 1);
                    System.out.println(player.getHealth());
                    if (player.getHealth() <= 0) {
                        System.out.println("Loss");
                        enemies.clear();
                    }
                }

                for (int j = 0; j < bullets.size(); j++) {
                    Bullet b = bullets.get(j);
                    if (isCollision(e, b)) {
                        enemies.remove(i);
                        i--;
                        bullets.remove(j);
                        j--;
                        player.incScore();
                    }
                }
            }
    	}
    	
        for (Bullet bullet : bullets) {
            bullet.render(g);
        }

        for (int i = 0; i < bullets.size(); i++) {
            if (bullets.get(i).getY() <= 50) {
                bullets.remove(i);
                i--;
            }
        }

        for (Enemy e : enemies) {
            if (e.getX() >= 50 && e.getX() <= 450 - 25 && e.getY() <= 450 - 25 && e.getY() >= 50) {
                e.render(g);
            }
        }

//        g.setColor(Color.BLUE);
//        g.drawString("Score: " + player.getScore(), 70, 500);
    }
    
    public static void onUpdateIngameInfoEvent(UpdateIngameInfoPacket event) {
      System.out.println(String.format("IngameScreen - receive update game info event: %d players - %d bullets - %d enemies", event.playerInGames.size(), event.bullets.size(), event.enemies.size()));

      GameManager.players.clear();
      GameManager.bullets.clear();
      GameManager.enemies.clear();

      GameManager.players.addAll(event.playerInGames);
      GameManager.bullets.addAll(event.bullets);
      GameManager.enemies.addAll(event.enemies);
  }

    private boolean isCollision(Enemy e, Bullet b) {
        return e.getX() < b.getX() + 6 &&
                e.getX() + 25 > b.getX() &&
                e.getY() < b.getY() + 6 &&
                e.getY() + 25 > b.getY();
    }

    private boolean isCollision(Player p, Enemy e) {
        return p.getX() < e.getX() + 25 &&
                p.getX() + 30 > e.getX() &&
                p.getY() < e.getY() + 25 &&
                p.getY() + 30 > e.getY();
    }
}