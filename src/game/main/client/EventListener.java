package game.main.client;

import game.main.packet.AddConnectionPacket;
import game.main.packet.AddConnectionResponsePacket;
import game.main.packet.AddPositionPlayerPacket;
import game.main.packet.RemoveConnectionPacket;
import game.main.packet.StartGamePacket;

public class EventListener {
	
	public void received(Object p, Client client) {
		if(p instanceof AddConnectionPacket) {
			AddConnectionPacket packet = (AddConnectionPacket)p;
			ConnectionHandler.addConnection(packet);
			client.setId(packet.id);
			System.out.println("Player " + packet.playerName + " has connected");
		} else if(p instanceof RemoveConnectionPacket) {
			RemoveConnectionPacket packet = (RemoveConnectionPacket)p;
			ConnectionHandler.removeConnection(packet);
			System.out.println("Player: " + packet.playerName + " has disconnected");
		} else if (p instanceof AddConnectionResponsePacket) {
            AddConnectionResponsePacket packet = (AddConnectionResponsePacket) p;
            System.out.println(packet.message);
            client.setId(packet.id);

            if (!packet.isConnectSuccess) {
                client.close();
            } else {
                ConnectionHandler.connections.put(packet.id, new Connection(packet.id, packet.playerName));
            }
        }
		else if(p instanceof AddPositionPlayerPacket) {
			AddPositionPlayerPacket packet = (AddPositionPlayerPacket) p;
			System.out.println("Player(x, y): (" + packet.x + ", " + packet.y);
			
		}
	}
	
}
