package game.main.client;

import game.main.packet.AddConnectionPacket;
import game.main.packet.RemoveConnectionPacket;

import java.util.HashMap;

public class ConnectionHandler {
	public static HashMap<Integer, Connection> connections = new HashMap<Integer, Connection>(4);

	public static void addConnection(AddConnectionPacket p){
		connections.put(p.id, new Connection(p.id, p.playerName));
	}

	public static void removeConnection(RemoveConnectionPacket p){
		connections.remove(p.id);
	}
}
