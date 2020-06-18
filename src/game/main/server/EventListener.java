package game.main.server;

import game.main.Config;
import game.main.GameManager;
import game.main.Player;
import game.main.packet.AddConnectionRequestPacket;
import game.main.packet.AddConnectionResponsePacket;
//import game.main.packet.AddPositionPlayerPacket;
import game.main.packet.RemoveConnectionPacket;
import game.main.packet.StartGameRequestPacket;
import game.main.packet.StartGameResponsePacket;
import game.main.packet.TickRequestPacket;
import game.main.packet.UpdateIngameInfoPacket;

import java.util.ArrayList;
import java.util.Map;

public class EventListener {

	public void received(Object p, Connection connection) {
		if (p instanceof AddConnectionRequestPacket) {
			
				AddConnectionRequestPacket addConnectionRequestPacket = (AddConnectionRequestPacket) p;
				ConnectionHandler.handleAddConnectionRequestPacket(addConnectionRequestPacket, connection);
		
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
		
		else if (p instanceof StartGameRequestPacket) {
            System.out.println("Server - EventLister - Start Game");
            ArrayList<Player> playerInGames = new ArrayList<>();
            int nPlayers = Room.clients.size();
            for (int i=0; i < nPlayers; i++) {
                int distance = (Config.GAME_WIDTH ) / nPlayers;
                int position = i;
                Player playerInGame = new Player(33 + distance / 2 + (position*distance),
                		Config.GAME_WIDTH + 20,
                        Room.clients.get(i).id,
                        position);
                playerInGames.add(playerInGame);
            }
            for(Map.Entry<Integer, Connection> entry : ConnectionHandler.connections.entrySet()) {
                Connection c = entry.getValue();
                c.sendObject(new StartGameResponsePacket(playerInGames));
            }
		}
		else if (p instanceof UpdateIngameInfoPacket) {
			UpdateIngameInfoPacket updateIngameInfopacket = (UpdateIngameInfoPacket) p;
        	GameManager.onUpdateIngameInfoEvent(updateIngameInfopacket);
        }
		else if(p instanceof TickRequestPacket) {
			
		}
		
	}
	
}
