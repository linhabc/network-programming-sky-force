package game.main;


import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Map;

import game.main.client.Client;
import game.main.packet.TickRequestPacket;
import game.main.packet.UpdateIngameInfoPacket;
import game.main.server.Connection;
import game.main.server.ConnectionHandler;

public class GameSetup implements Runnable {
	
	private ArrayList<Player> players = new ArrayList<Player>();
	
    private String title;
    private int width;
    private int height;
    private Client client;
    
    private Thread thread;
    private boolean running;

    public static final int GAME_WIDTH = 400;
    public static final int GAME_HEIGHT = 400;

    private Display display;
    private GameManager manager;
    private BufferStrategy buffer;
    private Graphics g;

    public GameSetup(String title, int width, int height, ArrayList<Player> players, Client client) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.players.addAll(players);
        this.client = client;
    }

    public void run() {
        LoadImage.init();
        display = new Display(title, width, height);
        manager = new GameManager(this.players.size());

        manager.init();

        int fps = 30;
        double timePerTick = 1000000000 / fps;
        double delta = 0;
        long current = System.nanoTime();

        while (running) {
            delta = delta + (System.nanoTime() - current) / timePerTick;
            current = System.nanoTime();
            if (delta >= 1) {
            	TickRequestPacket tickRequestPacket = new TickRequestPacket();
            	client.sendObject(tickRequestPacket);
            	
                tick();
                render();
                delta--;
            }
        }
    }

    public synchronized void start() {
        if (running) {
            return;
        }

        running = true;
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    public synchronized void stop() {
        if (running) {
            running = false;
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void tick() {
        manager.tick();
        sendNewIngameStateToClients();
    }
    
    private void sendNewIngameStateToClients() {
        UpdateIngameInfoPacket updateIngameInfoPacket = new UpdateIngameInfoPacket(
        		GameManager.players,
        		GameManager.numberOfPlayer,
                GameManager.bullets,
                GameManager.enemies);
        
        System.out.println(updateIngameInfoPacket);
        
        System.out.println("Number of players: " + GameManager.numberOfPlayer);
        
        System.out.println("Connection entry set: " + ConnectionHandler.connections.entrySet());
        
        for(Map.Entry<Integer, Connection> entry : ConnectionHandler.connections.entrySet()) {
            Connection c = entry.getValue();
            c.sendObject(updateIngameInfoPacket);
        }
    }

    public void render() {
        buffer = display.getCanvas().getBufferStrategy();
        if (buffer == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        
        g = buffer.getDrawGraphics();
        g.clearRect(0,0,width, height);

        g.drawImage(LoadImage.image, 50 ,50, GAME_WIDTH, GAME_HEIGHT, null);
        
        manager.render(g);

        buffer.show();
        g.dispose();
    }
}