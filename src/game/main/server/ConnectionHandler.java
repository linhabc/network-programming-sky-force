package game.main.server;

import java.util.HashMap;
import java.util.Map;

import game.main.packet.AddConnectionRequestPacket;
import game.main.packet.AddConnectionResponsePacket;
import game.main.packet.StartGameResponsePacket;

public class ConnectionHandler {

	public static final int MAX_SIZE = 4;

	public static HashMap<Integer, Connection> connections = new HashMap<Integer, Connection>(MAX_SIZE);

	public static void addConnection(AddConnectionRequestPacket addConnectionRequestPacket, Connection connection) {
		addConnectionRequestPacket.id = connection.id;
		connection.playerName = addConnectionRequestPacket.playerName;
		for (Map.Entry<Integer, Connection> entry : ConnectionHandler.connections.entrySet()) {
			Connection c = entry.getValue();
			AddConnectionResponsePacket addConnectionResponsePacket = new AddConnectionResponsePacket(
					connection.id,
					true, 
					addConnectionRequestPacket.playerName, 
					"Client " + connection.id + ": Connect successfully to server!");

			c.sendObject(addConnectionResponsePacket);
		}
//		System.out.println("Client " + addConnectionRequestPacket.id + " with name "
//				+ addConnectionRequestPacket.playerName + " is connected!");
	}
	
	public static void startGame() {
		String title = "Sky Force game";
		int width = 500;
		int height = 600;
		for(Map.Entry<Integer, Connection> entry: ConnectionHandler.connections.entrySet()) {
			StartGameResponsePacket startGameResponsePacket = new StartGameResponsePacket(title, width, height);
			Connection c = entry.getValue();
			c.sendObject(startGameResponsePacket);
		}
	}

}
