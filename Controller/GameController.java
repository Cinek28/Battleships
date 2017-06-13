package Controller;

import Model.*;
import View.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GameController {
	public static GameModel model = new GameModel();

	public void initGame(GameWindow window) {
		model.gameBoard.clearEnemyBoard();
		model.gameBoard.clearBoard();
		model.gameBoard.setBoardRandom();
		repaintBoard(window, 0);
		repaintBoard(window, 1);
	}

	public static void repaintBoard(GameWindow view, int whichBoard) {
		Status checkStatus;
		for (int row = 0; row < 10; ++row) {
			for (int col = 0; col < 10; ++col) {
				if (whichBoard == 0) {
					checkStatus = model.gameBoard.getStatus(row, col);
				} else {
					checkStatus = model.gameBoard.getEnemyStatus(row, col);
				}
				if (checkStatus == Status.MISSED)
					view.setBoardColor(Color.YELLOW, row, col, whichBoard);
				else if (checkStatus == Status.ISSHIP)
					view.setBoardColor(Color.RED, row, col, whichBoard);
				else if (checkStatus == Status.ISNOTSHIP) {
					view.setBoardColor(Color.BLUE, row, col, whichBoard);
				} else if (checkStatus == Status.SHIPDESTROYED)
					view.setBoardColor(Color.BLACK, row, col, whichBoard);
				else if (checkStatus == Status.UNKNOWN)
					view.setBoardColor(Color.GRAY, row, col, whichBoard);
			}
		}
	}

	public static void startServer(String serverPortStr) {

	}

	public static void sendMessage(String messageStr) {

	}

	public static void connectToServer(String hostIp, String hostPort) {

	}

}
