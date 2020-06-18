package game.main.server;

import java.util.HashMap;
import java.util.Map;

import game.main.packet.AddConnectionRequestPacket;
import game.main.packet.AddConnectionResponsePacket;
import game.main.packet.StartGameResponsePacket;
import game.main.packet.RemoveConnectionPacket;
import game.main.packet.UpdateRoomInfoPacket;

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
	

	public static void handleAddConnectionRequestPacket(AddConnectionRequestPacket packet, Connection connection) {
	        if (ConnectionHandler.connections.size() <= MAX_SIZE) {

	            packet.id = connection.id;
	            connection.playerName = packet.playerName;

	            ClientInRoom client = new ClientInRoom(packet.id,
	                    packet.playerName,
	                    true,
	                    packet.isMaster);
	            Room.clients.add(client);
	            UpdateRoomInfoPacket updateRoomInfoPacket = new UpdateRoomInfoPacket(Room.clients, Room.getLevel());

	            for(Map.Entry<Integer, Connection> entry : connections.entrySet()) {
	                Connection c = entry.getValue();
	                if (c != connection) {
	                    c.sendObject(packet);
	                } else {
	                    AddConnectionResponsePacket addConnectionResponsePacket = new AddConnectionResponsePacket(
	                            connection.id,
	                            true,
	                            packet.playerName,
	                            "Connect successfully to server!");

	                    c.sendObject(addConnectionResponsePacket);
	                }
	                c.sendObject(updateRoomInfoPacket);
	            }
	            System.out.println("Client " + packet.id + " with name " + packet.playerName + " is connected!");
	        } else {
	            AddConnectionResponsePacket addConnectionResponsePacket = new AddConnectionResponsePacket(
	                    -1,
	                    false,
	                    "Room is full!");

	            connection.sendObject(addConnectionResponsePacket);
	            connection.close();
	        }
	    }
	
    private void handleRemoveConnectionPacket(RemoveConnectionPacket packet, Connection connection) {
        System.out.println("Client: " + packet.id + " with name " + packet.playerName + " has disconnected");
        ConnectionHandler.connections.get(packet.id).close();
//        for(Map.Entry<Integer, Connection> entry : ConnectionHandler.connections.entrySet()) {
//            Connection c = entry.getValue();
//            if (c.id != connection.id)
//                c.sendObject(packet);
//        }
    }
}
