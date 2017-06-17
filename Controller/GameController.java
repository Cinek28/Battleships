package Controller;

import Model.*;
import Model.NetworkInterface.Client;
import Model.NetworkInterface.GameEvent;
import View.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GameController implements EventHandler<Event> {
	public static GameModel model;
	public static GameWindow view;

	public GameController(GameWindow view) {
		GameController.view = view;
	}

	public void initGame(GameModel model) {
		GameController.model = model;
		GameController.model.gameBoard.clearEnemyBoard();
		GameController.model.gameBoard.clearBoard();
		GameController.model.gameBoard.setBoardRandom();
		GameController.model.resetNumberOfShips();
		// repaintBoard(0);
		// repaintBoard(1);

	}

	public void repaintBoard(int whichBoard) {
		Status checkStatus;
		for (int row = 0; row < 10; ++row) {
			for (int col = 0; col < 10; ++col) {
				if (whichBoard == 0) {
					checkStatus = GameController.model.gameBoard.getStatus(row, col);
				} else {
					checkStatus = GameController.model.gameBoard.getEnemyStatus(row, col);
				}
				if (checkStatus == Status.MISSED)
					GameController.view.setBoardColor(Color.YELLOW, row, col, whichBoard);
				else if (checkStatus == Status.ISSHIP)
					GameController.view.setBoardColor(Color.RED, row, col, whichBoard);
				else if (checkStatus == Status.ISNOTSHIP) {
					GameController.view.setBoardColor(Color.BLUE, row, col, whichBoard);
				} else if (checkStatus == Status.SHIPDESTROYED)
					GameController.view.setBoardColor(Color.BLACK, row, col, whichBoard);
				else if (checkStatus == Status.UNKNOWN)
					GameController.view.setBoardColor(Color.GRAY, row, col, whichBoard);
			}
		}
	}

	public void addToChat(String message) {
		GameController.view.addToChat(message);
	}

	public void handleActionEvent(ActionEvent event) {
		// handle buttons events event:

		// Server start button:
		Object eventSource = event.getSource();
		if (GameController.view.startServer.equals(eventSource)) {
			// Starting server if not running:
			String serverPort = GameController.view.getServerPort();
			if (!GameController.model.server.isRunning()) {
				if (!serverPort.equals("")) {
					GameController.view.setStatus("Setting up server on port: " + serverPort);
					GameController.model.server.setPort(Integer.parseInt(serverPort));
					GameController.model.client = new Client("Server", "127.0.0.1", Integer.parseInt(serverPort));
				} else {
					GameController.view.setStatus("Setting up server on default port: 8081");
					GameController.model.server.setPort(8081);
					GameController.model.client = new Client("Server", "127.0.0.1", 8081);
				}
				if (GameController.model.server.start()) {
					if (GameController.model.client.start()) {
						GameController.view.connect.setDisable(true);
						GameController.view.startServer.setText("Disconnect");
					} else {
						GameController.view.setStatus("Problem starting client, disconnecting server");
						GameController.model.server.stop();
					}
				} else {
					GameController.view.setStatus("Can't start server port " + serverPort);

				}
				// Stopping running server if running:
			} else {
				GameController.model.server.stop();
				GameController.view.connect.setDisable(false);
				GameController.view.startServer.setText("Start server");
				GameController.view.setStatus("Server disconnected");
			}

			// Connect button:
		} else if (GameController.view.connect.equals(eventSource)) {
			String hostIP = GameController.view.getHostIp();
			String hostPort = GameController.view.getHostPort();
			if (!(GameController.model.client.isAlive())) {
				if (!(hostIP.equals("") && hostPort.equals(""))) {
					GameController.view.setStatus("Connecting to: " + hostIP + " on port " + hostPort);
					GameController.model.client = new Client("Default", hostIP, Integer.parseInt(hostPort));
				} else {
					GameController.view.setStatus("Connecting to local server: 127.0.0.1. on port 8081");
					GameController.model.client = new Client("Default", hostIP, 8081);
				}
				if (GameController.model.client.start()) {
					GameController.view.connect.setText("Disconnect");
					GameController.view.startServer.setDisable(true);
					GameController.view.setStatus("Connected to server");
				} else {
					GameController.view.setStatus("Can't connect to server");
				}

			} else {
				GameController.model.client.stop();
				GameController.view.connect.setText("Connect");
				GameController.view.startServer.setDisable(false);
				GameController.view.setStatus("Disconnected from server");
			}
			// Handling Send button:
		} else if (GameController.view.send.equals(eventSource)) {
			String message = GameController.view.getChatMsg();
			if (!message.equals("") && GameController.model.client.isAlive()) {
				GameEvent ge = new GameEvent(GameEvent.C_CHAT_MSG, message);
				try {
					GameController.model.client.sendMessage(ge);
				} catch (Exception e) {
					GameController.view.setStatus("Message not sent");
				}
				addToChat(message);
				GameController.view.clearChatField();
			}
		}
	}

	public void handleMouseEvent(MouseEvent event) {
		int x = ((int) event.getX())/30;
		int y = ((int) event.getY())/30;
		if (GameController.model.client.isAlive()) {
			GameEvent ge = new GameEvent(GameEvent.C_SHOT);
			ge.setMessage(x+"|"+y);
			try {
				GameController.model.client.sendMessage(ge);
			} catch (Exception e) {
				GameController.view.setStatus("Shot not sent");
			}
		}
		repaintBoard(0);
		repaintBoard(1);
	}

	@Override
	public void handle(Event event) {

	}
}
