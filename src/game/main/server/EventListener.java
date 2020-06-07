package game.main.server;

import game.main.packet.AddConnectionPacket;
import game.main.packet.AddConnectionResponsePacket;
import game.main.packet.AddPositionPlayerPacket;
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
					} else {
                        AddConnectionResponsePacket addConnectionResponsePacket = new AddConnectionResponsePacket(
                                connection.id,
                                true,
                                addConnectionPacket.playerName,
                                "Connect successfully to server!");

                        c.sendObject(addConnectionResponsePacket);
                    }
				}
				System.out.println("Client " + addConnectionPacket.id + " with name " + addConnectionPacket.playerName + " is connected!");
			} else {
                AddConnectionResponsePacket addConnectionResponsePacket = new AddConnectionResponsePacket(
                        -1,
                        false,
                        "Room is full!");

                connection.sendObject(addConnectionResponsePacket);
                connection.close();
            }

		} else if (p instanceof RemoveConnectionPacket) {
			RemoveConnectionPacket packet = (RemoveConnectionPacket) p;
			System.out.println("Client: " + packet.id + " with name " + packet.playerName + " has disconnected");
			// close connection
			ConnectionHandler.connections.get(packet.id).close();
			for(Map.Entry<Integer, Connection> entry : ConnectionHandler.connections.entrySet()) {
				Connection c = entry.getValue();
				c.sendObject(packet);
			}
		}
		else if(p instanceof AddPositionPlayerPacket) {
			AddPositionPlayerPacket packet = (AddPositionPlayerPacket) p;
			System.out.println("Sending the position(x, y): (" + packet.x + ", " + packet.y + ")");
			for(Map.Entry<Integer, Connection> entry: ConnectionHandler.connections.entrySet()) {
				Connection c = entry.getValue();
				c.sendObject(packet);
			}
		}
	}
	
}
