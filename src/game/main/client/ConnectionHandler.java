package game.main.client;


import java.util.HashMap;

import game.main.packet.AddConnectionRequestPacket;
import game.main.packet.AddConnectionResponsePacket;
import game.main.packet.RemoveConnectionPacket;

public class ConnectionHandler {
	public static HashMap<Integer, Connection> connections = new HashMap<Integer, Connection>(4);
	
    public static void handleAddConnectionRequestPacket(AddConnectionRequestPacket packet, Client client) {
        connections.put(packet.id, new Connection(packet.id, packet.playerName));
        System.out.println("Player " + packet.playerName + " has connected");
    }
    
    
    public static void handleRemoveConnectionPacket(RemoveConnectionPacket packet, Client client) {
        System.out.println("Player: " + packet.playerName + " has disconnected");
        connections.remove(packet.id);
    }

	public static void addConnection(AddConnectionResponsePacket p){
		connections.put(p.id, new Connection(p.id, p.playerName));
	}
    public static void handleAddConnectionResponsePacket(AddConnectionResponsePacket packet, Client client) {
        System.out.println(packet.message);
        client.setId(packet.id);
        if (!packet.isConnectSuccess) {
            client.close();
        } else {
            connections.put(packet.id, new Connection(packet.id, packet.playerName));
        }
    }
}
