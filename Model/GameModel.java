package Model;

import Model.NetworkInterface.Client;
import Model.NetworkInterface.Server;

public class GameModel {
	private GameStatus Status = GameStatus.INIT;
	private ActualPlayer WhoseTurn = ActualPlayer.ENEMY;
	public Client client = new Client("Default", "127.0.0.1", 8080);
	public Server server = new Server(8080);

	public Board gameBoard = new Board();

	public GameStatus getGameStatus() {
		return Status;
	}

	public ActualPlayer getWhoseTurn() {
		return WhoseTurn;
	}

	public void changeTurn() {
		if (WhoseTurn == ActualPlayer.ENEMY) {
			WhoseTurn = ActualPlayer.PLAYER;
		}else{
			WhoseTurn = ActualPlayer.ENEMY;
		}
	}

	public void setStartingPlayer() {

	}

	public String getID() {
		return client.getPlayerID();
	}

	public void resetNumberOfShips() {
		gameBoard.setNoOfShipsActive(8);
	}

}
