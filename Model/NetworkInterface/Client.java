package Model.NetworkInterface;

import java.net.Socket;

public class Client {
	private String host;

	private int port;

	private String playerID;

	private Socket socket;

	private Connection connection = null;

	public Client(String ID, String host, int port) {
		this.playerID = ID;
		this.host = host;
		this.port = port;
	}

	public boolean start() {
		try {
			socket = new Socket(host, port);
		} catch (Exception ex) {
			return false;
		}
		connection = new Connection(socket);
		connection.setNick(this.playerID);
		connection.start();

		return true;
	}

	public void stop() {
		if (connection != null)
			connection.close();
	}

	public String getPlayerID() {
		return playerID;
	}

	public void sendMessage(GameEvent ge) {
		connection.sendMessage(ge.toSend());
	}

	public GameEvent receiveMessage() {
		if (connection.messagesQueue.isEmpty()) {
			return null;
		} else {
			GameEvent ge = new GameEvent((String) connection.messagesQueue.getFirst());
			connection.messagesQueue.removeFirst();
			return ge;
		}
	}

	public boolean isAlive() {
		return (connection != null && connection.isAlive());
	}
}
