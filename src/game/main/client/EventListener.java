package game.main.client;

import game.main.packet.AddConnectionResponsePacket;
import game.main.packet.AddPositionPlayerResponsePacket;
import game.main.packet.RemoveConnectionPacket;
import game.main.packet.UpdateRoomInfoPacket;

public class EventListener {
	
	public void received(Object p, Client client) {
		if (p instanceof AddConnectionResponsePacket) {
            AddConnectionResponsePacket addConnectionResponsePacket = (AddConnectionResponsePacket)p;
            ConnectionHandler.handleAddConnectionResponsePacket(addConnectionResponsePacket, client);
        } else if(p instanceof RemoveConnectionPacket) {
			RemoveConnectionPacket packet = (RemoveConnectionPacket)p;
			ConnectionHandler.handleRemoveConnectionPacket(packet, client);
			System.out.println("Player: " + packet.playerName + " has disconnected");
		} else if (p instanceof AddConnectionResponsePacket) {
			AddConnectionResponsePacket addConnectionResponsePacket = (AddConnectionResponsePacket) p;
			ConnectionHandler.handleAddConnectionResponsePacket(addConnectionResponsePacket, client);
        } else if (p instanceof UpdateRoomInfoPacket) {
            UpdateRoomInfoPacket updateRoomInfoPacket = (UpdateRoomInfoPacket) p;
            System.out.println("Room Size: " + updateRoomInfoPacket.clients.size());
            handleUpdateRoomInfoPacket(updateRoomInfoPacket, client);
        }
		else if(p instanceof AddPositionPlayerResponsePacket) {
			AddPositionPlayerResponsePacket packet = (AddPositionPlayerResponsePacket) p;
			System.out.println("Player(x, y): (" + packet.x + ", " + packet.y);
			
		}
	}
	
	
	private void handleUpdateRoomInfoPacket(UpdateRoomInfoPacket updateRoomInfoPacket, Client client) {
        updateRoomInfoPacket.clients.forEach(clientInRoom -> System.out.println(new StringBuilder()
                .append("--------------------Room Info--------------------\n")
                .append("Position: ").append(updateRoomInfoPacket.clients.indexOf(clientInRoom) + 1).append("\n")
                .append("ID: ").append(clientInRoom.id).append("\n")
                .append("Player name: ").append(clientInRoom.playerName).append("\n")
                .append("Ready? ").append(clientInRoom.isReady).append("\n")
                .append("Master? ").append(clientInRoom.isMaster).append("\n")
                .toString()
        ));
        System.out.print("Enter y to start game, x to exit game: ");
    }

}