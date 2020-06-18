package game.main.client;

import game.main.packet.AddConnectionRequestPacket;
import game.main.packet.AddConnectionResponsePacket;
import game.main.packet.RemoveConnectionPacket;
import game.main.packet.StartGameRequestPacket;
import game.main.packet.StartGameResponsePacket;

public class EventListener {
	
	public void received(Object p, Client client) {
		
		
		if(p instanceof RemoveConnectionPacket) {
			RemoveConnectionPacket packet = (RemoveConnectionPacket)p;
			ConnectionHandler.removeConnection(packet);
			System.out.println("Player: " + packet.playerName + " has disconnected");
		} 
		
		
		else if (p instanceof AddConnectionResponsePacket) {
            AddConnectionResponsePacket packet = (AddConnectionResponsePacket) p;
            client.setId(packet.id);
            System.out.println(packet.message);

            if (!packet.isConnectSuccess) {
                client.close();
            } 
            else {
    			ConnectionHandler.addConnection(packet);
            }
            
        }
		
		else if(p instanceof StartGameResponsePacket) {
			StartGameResponsePacket startGameResponsePacket = (StartGameResponsePacket) p;
			
		}
		
	}
	
}
