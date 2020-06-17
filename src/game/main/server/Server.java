package game.main.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Server implements Runnable{
	private int port;
	private ServerSocket serverSocket;
	private boolean running = false;
	private int id = 0;
	
	public Server(int port) {
		super();
		this.port = port;
	    try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	@Override
	public void run() {
		running = true;
		System.out.println("Server started on port: " + port);
		while(running) {
			try {
				Socket socket = serverSocket.accept();
				initSocket(socket);
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		shutDown();
	}
	
	public void start() {
		new Thread(this).start();
	}

	private void initSocket(Socket socket) {
		Connection connection = new Connection(socket, id);
		ConnectionHandler.connections.put(id, connection);
		new Thread(connection).start();
		id++;
	}
	
	public void shutDown() {
		running = false;
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}