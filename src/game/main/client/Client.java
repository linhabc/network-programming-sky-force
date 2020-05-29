package game.main.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import game.main.packet.RemoveConnectionPacket;

public class Client implements Runnable{
	private String host;
	private int port;

	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;

	private int id;
	public String playerName;

	private boolean running = false;
	private EventListener listener;

	public Client(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void connect() {
		try {
			socket = new Socket(host, port);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			listener = new EventListener();
			new Thread(this).start();
		} catch (ConnectException e) {
			System.out.println("Unable to connect to the server");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			running = false;
			RemoveConnectionPacket packet = new RemoveConnectionPacket(this.id, this.playerName);
			sendObject(packet);
			in.close();
			out.close();
			socket.close();
			System.out.println("Disconnected from server!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendObject(Object packet) {
		try {
			out.writeObject(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			running = true;

			while (running) {
				try {
					Object data = in.readObject();
					listener.received(data, this);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SocketException e) {
					close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
