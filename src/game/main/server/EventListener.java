package game.main.server;

import game.main.Player;
import game.main.packet.AddConnectionRequestPacket;
import game.main.packet.AddConnectionResponsePacket;
import game.main.packet.RemoveConnectionPacket;
import game.main.packet.StartGameRequestPacket;
import game.main.packet.StartGameResponsePacket;

import java.util.ArrayList;
import java.util.Map;

public class EventListener {

	public void received(Object p, Connection connection) {
		if (p instanceof AddConnectionRequestPacket) {
			if (ConnectionHandler.connections.size() <= ConnectionHandler.connections.size()) {
				AddConnectionRequestPacket addConnectionRequestPacket = (AddConnectionRequestPacket) p;
				ConnectionHandler.addConnection(addConnectionRequestPacket, connection);
				} 
			else {
                AddConnectionResponsePacket addConnectionResponsePacket = new AddConnectionResponsePacket(
                        -1,
                        false,
                        "Room is full!");

                connection.sendObject(addConnectionResponsePacket);
                connection.close();
            }

		} 
		
		else if (p instanceof RemoveConnectionPacket) {
			RemoveConnectionPacket packet = (RemoveConnectionPacket) p;
			System.out.println("Client: " + packet.id + " with name " + packet.playerName + " has disconnected");
			// close connection
			ConnectionHandler.connections.get(packet.id).close();
			for(Map.Entry<Integer, Connection> entry : ConnectionHandler.connections.entrySet()) {
				Connection c = entry.getValue();
				c.sendObject(packet);
			}
		}
		
		else if(p instanceof StartGameRequestPacket) {
			StartGameRequestPacket startGameRequestPacket = (StartGameRequestPacket) p;
			ConnectionHandler.startGame();
		}
		
	}
	
}
