package game.main.server;

import game.main.packet.AddConnectionRequestPacket;
import game.main.packet.AddConnectionResponsePacket;
import game.main.packet.AddPositionPlayerPacket;
import game.main.packet.RemoveConnectionPacket;

import java.util.Map;

public class EventListener {

	public void received(Object p, Connection connection) {
		if (p instanceof AddConnectionRequestPacket) {
            AddConnectionRequestPacket addConnectionRequestPacket = (AddConnectionRequestPacket) p;
            ConnectionHandler.handleAddConnectionRequestPacket(addConnectionRequestPacket, connection);
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
