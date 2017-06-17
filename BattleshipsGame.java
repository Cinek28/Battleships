import Model.GameModel;
import Model.ShotStatus;
import Model.NetworkInterface.Client;
import Model.NetworkInterface.Server;
import Model.NetworkInterface.GameEvent;
import View.GameWindow;
import Controller.GameController;

public class BattleshipsGame {
	private Client client = new Client("Default", "127.0.0.1", 8080);
	private Server server = new Server(8080);

	public String getID() {
		return client.getPlayerID();
	}

	public static void main(String args[]) {
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
