package game.main.client;

import java.util.HashMap;

import game.main.packet.AddConnectionResponsePacket;
import game.main.packet.RemoveConnectionPacket;

public class ConnectionHandler {
	public static HashMap<Integer, Connection> connections = new HashMap<Integer, Connection>(4);

	public static void addConnection(AddConnectionResponsePacket p){
		connections.put(p.id, new Connection(p.id, p.playerName));
	}

	public static void removeConnection(RemoveConnectionPacket p){
		connections.remove(p.id);
	}
}
