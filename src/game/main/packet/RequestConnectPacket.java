package game.main.packet;

import java.io.Serializable;

public class RequestConnectPacket implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String userName;

	public RequestConnectPacket(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
