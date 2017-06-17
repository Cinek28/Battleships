import Model.GameModel;
import Model.ShotStatus;
import Model.NetworkInterface.Client;
import Model.NetworkInterface.Server;
import Model.NetworkInterface.GameEvent;
import View.GameWindow;
import Controller.GameController;

public class BattleshipsGame {
	public static void main(String args[]) {
		//Setting up a view thread:
		new Thread() {
			@Override
			public void run() {
				javafx.application.Application.launch(GameWindow.class);
			}
		}.start();
		GameModel model = new GameModel();
		GameWindow view = new GameWindow();
		GameController mainGame = new GameController(view);
		mainGame.initGame(model);
	}
}
