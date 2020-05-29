package game.main.client;

import game.main.packet.AddConnectionPacket;
import game.main.packet.RemoveConnectionPacket;
import game.main.packet.StartGamePacket;

public class EventListener {
	
	public void received(Object p, Client client) {
		if(p instanceof AddConnectionPacket) {
			AddConnectionPacket packet = (AddConnectionPacket)p;
			ConnectionHandler.addConnection(packet);
			client.setId(packet.id);
			System.out.println("Player " + packet.playerName + " has connected");
		} else if(p instanceof StartGamePacket) {
			RemoveConnectionPacket packet = (RemoveConnectionPacket)p;
			ConnectionHandler.removeConnection(packet);
			System.out.println("Player: " + packet.playerName + " has disconnected");
		}
	}
	
}
