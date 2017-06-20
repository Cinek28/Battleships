package Controller;

import Model.*;
import Model.NetworkInterface.Client;
import Model.NetworkInterface.GameEvent;
import Model.NetworkInterface.Server;
import View.*;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

@SuppressWarnings("rawtypes")
public class GameController implements EventHandler {
	public final GameModel model = new GameModel();
	public final GameWindow view = new GameWindow(this);

	@Override
	public void handle(Event event) {
		// Server start button:
		Object eventSource = event.getSource();
		if (this.view.startServer.equals(eventSource)) {
			// Starting server if not running:
			String serverPort = this.view.getServerPort();
			if (!this.model.server.isRunning()) {
				if (!serverPort.equals("")) {
					this.view.setStatus("Setting up server on port: " + serverPort);
					this.model.server.setPort(Integer.parseInt(serverPort));
					this.model.client = new Client("Server", "127.0.0.1", Integer.parseInt(serverPort));
				} else {
					this.view.setStatus("Setting up server on default port: 8081");
					this.model.server.setPort(8081);
					this.model.client = new Client("Server", "127.0.0.1", 8081);
				}
				if (this.model.server.start()) {
					if (this.model.client.start()) {
						GameEvent ge = new GameEvent(GameEvent.C_READY, "", this.model.getID());
						this.model.client.sendMessage(ge);
						this.view.connect.setDisable(true);
						this.view.startServer.setText("Disconnect");
						System.out.println(ge.getPlayerId());
					} else {
						this.view.setStatus("Problem starting client, disconnecting server");
						this.model.server.stop();
					}
				} else {
					this.view.setStatus("Can't start server port " + serverPort);

				}
				// Stopping running server if running:
			} else {
				GameEvent ge = new GameEvent(GameEvent.C_QUIT_GAME);
				this.model.client.sendMessage(ge);
				this.model.client.stop();
				this.model.server.stop();
				this.view.connect.setDisable(false);
				this.view.startServer.setText("Start server");
				this.view.setStatus("Server disconnected");
			}

			// Connect button:
		} else if (this.view.connect.equals(eventSource)) {
			String hostIP = this.view.getHostIp();
			String hostPort = this.view.getHostPort();
			if (!(this.model.client.isAlive())) {
				if (!(hostIP.equals("") && hostPort.equals(""))) {
					this.view.setStatus("Connecting to: " + hostIP + " on port " + hostPort);
					this.model.client = new Client("Client", hostIP, Integer.parseInt(hostPort));
					this.model.server = new Server(Integer.parseInt(hostPort));
				} else {
					this.view.setStatus("Connecting to local server: 127.0.0.1. on port 8081");
					this.model.client = new Client("Client", hostIP, 8081);
					this.model.server = new Server(8081);
				}
				if (this.model.client.start()) {
					this.view.connect.setText("Disconnect");
					this.view.startServer.setDisable(true);
					this.view.setStatus("Connected to server");
					GameEvent ge = new GameEvent(GameEvent.C_READY, "", this.model.getID());
					System.out.println(ge.getPlayerId());
					this.model.client.sendMessage(ge);
					this.model.setStartingPlayer();

				} else {
					this.view.setStatus("Can't connect to server");
				}

			} else {
				GameEvent ge = new GameEvent(GameEvent.C_QUIT_GAME);
				this.model.client.sendMessage(ge);
				this.model.client.stop();
				this.view.connect.setText("Connect");
				this.view.startServer.setDisable(false);
				this.view.setStatus("Disconnected from server");
			}
			// Handling Send button:
		} else if (this.view.send.equals(eventSource)) {
			String message = this.view.getChatMsg();
			if (!message.equals("") && this.model.client.isAlive()) {
				GameEvent ge = new GameEvent(GameEvent.C_CHAT_MSG, message, model.client.getPlayerID());
				try {
					this.model.client.sendMessage(ge);
				} catch (Exception e) {
					this.view.setStatus("Message not sent");
				}
				this.view.clearChatField();
				this.view.setStatus("Message sent");
			}
		}
		repaintBoard(0);
		repaintBoard(1);
	}

	public void handleMouseEvent(MouseEvent event) {
		// Handling mouse click on enemy board:
		int x = ((int) event.getX()) / 30;
		int y = ((int) event.getY()) / 30;
		if (this.model.getGameStatus() == GameStatus.STARTED) {
			if (this.model.getWhoseTurn() == ActualPlayer.PLAYER) {
				Status check = this.model.gameBoard.getEnemyStatus(y, x);
				if (check == Status.UNKNOWN) {
					GameEvent ge = new GameEvent(GameEvent.C_SHOT, y + "|" + x, this.model.getID());
					if (this.model.client.isAlive()) {
						this.model.client.sendMessage(ge);
						this.view.setStatus("Shot sent");
					} else {
						this.view.setStatus("Client inactive");
					}
				}else{
					this.view.setStatus("You already checked this field");
				}
			} else {
				this.view.setStatus("Not your turn");
			}
		} else {
			this.view.setStatus("Game not started");
		}
		repaintBoard(0);
		repaintBoard(1);
	}

	public void initGame() {
		this.model.gameBoard.clearEnemyBoard();
		this.model.gameBoard.clearBoard();
		this.model.gameBoard.setBoardRandom();
		this.model.resetNumberOfShips();
		this.model.setGameStatus(GameStatus.INIT);
		repaintBoard(0);
		repaintBoard(1);
	}

	public void repaintBoard(int whichBoard) {
		Status checkStatus;
		for (int row = 0; row < 10; ++row) {
			for (int col = 0; col < 10; ++col) {
				if (whichBoard == 0) {
					checkStatus = this.model.gameBoard.getStatus(row, col);
				} else {
					checkStatus = this.model.gameBoard.getEnemyStatus(row, col);
				}
				if (checkStatus == Status.MISSED)
					this.view.setBoardColor(Color.YELLOW, row, col, whichBoard);
				else if (checkStatus == Status.ISSHIP)
					this.view.setBoardColor(Color.RED, row, col, whichBoard);
				else if (checkStatus == Status.ISNOTSHIP) 
					this.view.setBoardColor(Color.BLUE, row, col, whichBoard);
				else if (checkStatus == Status.SHIPDESTROYED)
					this.view.setBoardColor(Color.BLACK, row, col, whichBoard);
				else if (checkStatus == Status.UNKNOWN)
					this.view.setBoardColor(Color.GRAY, row, col, whichBoard);
				else if (checkStatus == Status.SHOT)
					this.view.setBoardColor(Color.DARKGREEN, row, col, whichBoard);
			}
		}
	}

	public void addToChat(String message) {
		this.view.addToChat(message);
	}

}
