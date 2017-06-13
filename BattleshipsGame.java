import Model.*;
import View.GameWindow;
import Controller.*;
import javafx.*;
import javafx.stage.Stage;
public class BattleshipsGame {
	public static void main(String args[]){
        new Thread() {
            @Override
            public void run() {
                javafx.application.Application.launch(GameWindow.class);
            }
        }.start();
		GameWindow view = new GameWindow();
		GameController mainGame = new GameController();
		mainGame.initGame(view);
	}
}
