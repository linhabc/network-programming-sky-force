package game.main.server;

import game.main.packet.AddConnectionPacket;
import game.main.packet.RemoveConnectionPacket;

import java.util.Map;

public class EventListener {

	public void received(Object p, Connection connection) {
		if (p instanceof AddConnectionPacket) {
			if (ConnectionHandler.connections.size() <= ConnectionHandler.connections.size()) {
				AddConnectionPacket addConnectionPacket = (AddConnectionPacket) p;
				addConnectionPacket.id = connection.id;
				connection.playerName = addConnectionPacket.playerName;
				for(Map.Entry<Integer, Connection> entry : ConnectionHandler.connections.entrySet()) {
					Connection c = entry.getValue();
					if (c != connection) {
						c.sendObject(addConnectionPacket);
					}
				}
				System.out.println("Client " + addConnectionPacket.id + " with name " + addConnectionPacket.playerName + " is connected!");
			}

		} else if (p instanceof RemoveConnectionPacket) {
			RemoveConnectionPacket packet = (RemoveConnectionPacket) p;
			System.out.println("Client: " + packet.id + " with name " + packet.playerName + " has disconnected");
			ConnectionHandler.connections.get(packet.id).close();
			for(Map.Entry<Integer, Connection> entry : ConnectionHandler.connections.entrySet()) {
				Connection c = entry.getValue();
				c.sendObject(packet);
			}
		}
	}
	
}
