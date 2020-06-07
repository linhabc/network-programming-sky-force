package game.main.server;

import game.main.packet.RemoveConnectionPacket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;

public class Connection implements Runnable {

	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	private EventListener listener;
	private boolean running = false;

	public int id;
	public String playerName;

	public Connection(Socket socket, int id) {
		this.socket = socket;
		this.id = id;
		
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			listener = new EventListener();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			running = true;
			
			while(running) {
				try {
					Object data = in.readObject();
					listener.received(data, this);
				}catch(ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			if (this.playerName != null) {
                for(Map.Entry<Integer, Connection> entry : ConnectionHandler.connections.entrySet()) {
                    Connection c = entry.getValue();
                    if (c.id != this.id) {
                        RemoveConnectionPacket removeConnectionPacket = new RemoveConnectionPacket(this.id, this.playerName);
                        c.sendObject(removeConnectionPacket);
                    }
                }
            }
			running = false;
			in.close();
			out.close();
			socket.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendObject(Object packet) {
		try {
			out.writeObject(packet);
			out.flush();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

}
