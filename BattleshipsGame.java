import Controller.GameController;
import Model.ActualPlayer;
import Model.GameStatus;
import Model.ShotStatus;
import Model.NetworkInterface.GameEvent;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.application.Platform;
import javafx.event.*;

public class BattleshipsGame extends Application {
	private final static GameController mainGame = new GameController();

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Setting the title to Stage.
		primaryStage.setTitle("Battleships");

		final Scene scene = new Scene(mainGame.view.setParent());

		// Adding the scene to Stage
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);

		// Adding icon to stage:
		primaryStage.getIcons().add(new Image("/View/battleship.jpg"));

		// Displaying the contents of the stage
		primaryStage.show();

		// Handling exit:
		primaryStage.setOnCloseRequest(new EventHandler<javafx.stage.WindowEvent>() {
			@Override
			public void handle(javafx.stage.WindowEvent event) {
				Platform.exit();
				System.exit(0);
			}
		});
	}

	public static void main(String args[]) {
		mainGame.view.setBoards();
		mainGame.initGame();
		new Thread() {
			@Override
			public void run() {
				while (true) {
					if (mainGame.model.client != null && mainGame.model.client.isAlive()) {
						GameEvent ge;
						while (mainGame.model.client != null && mainGame.model.client.isAlive()
								&& (ge = mainGame.model.client.receiveMessage()) != null) {
							switch (ge.getType()) {
							case GameEvent.SB_CHAT_MSG:
								if (mainGame.model.getID().compareTo(ge.getPlayerId()) == 0) {
									mainGame.addToChat("YOU > ");
								} else {
									mainGame.addToChat("ENEMY > ");
								}

								mainGame.addToChat(ge.getMessage() + "\n");
								break;

//							case GameEvent.SB_LOGIN:
//								if (mainGame.model.getID().compareTo(ge.getMessage()) != 0) {
//									mainGame.view.setStatus("Player " + ge.getPlayerId() + "joined");
//								}
//								break;

							case GameEvent.SB_ALL_READY:
								if (mainGame.model.getWhoseTurn() == ActualPlayer.PLAYER) {
									mainGame.view.setStatus("Game started. Your turn.");
									mainGame.model.setGameStatus(GameStatus.STARTED);
								} else {
									mainGame.view.setStatus("Game started. Enemy's turn.");
									mainGame.model.setGameStatus(GameStatus.STARTED);
								}
								break;

							case GameEvent.SB_SHOT:
								if (mainGame.model.getID().compareTo(ge.getPlayerId()) != 0) {
									System.out.println("SB_SHOT:" + ge.getPlayerId());
									String s = ge.getMessage();
									int idx1 = s.indexOf('|');
									String a = s.substring(0, idx1);
									String b = s.substring(idx1 + 1);
									try {
										int x = Integer.parseInt(a);
										int y = Integer.parseInt(b);
										ShotStatus w = mainGame.model.gameBoard.checkShot(x, y);
										if(w != ShotStatus.CHECKED){
										GameEvent geOut = new GameEvent(GameEvent.C_SHOT_RESULT, "", mainGame.model.getID());
										geOut.setMessage(x + "|" + y + "|" + w.ordinal());
										mainGame.model.client.sendMessage(geOut);
										}
									} catch (NumberFormatException ex) {
									}

								}
								break;

							case GameEvent.SB_SHOT_RESULT: {
								String s = ge.getMessage();
								int idx1 = s.indexOf('|');
								int idx2 = s.indexOf('|', idx1 + 1);
								String a = s.substring(0, idx1);
								String b = s.substring(idx1 + 1, idx2);
								String c = s.substring(idx2 + 1);

								try {
									int x = Integer.parseInt(a);
									int y = Integer.parseInt(b);
									int n = Integer.parseInt(c);
									ShotStatus w = ShotStatus.values()[n];

									if (mainGame.model.getID().compareTo(ge.getPlayerId()) != 0) {
										mainGame.model.gameBoard.setShot(x, y, w, ActualPlayer.ENEMY);
									} else {
										mainGame.model.gameBoard.setShot(x, y, w, ActualPlayer.PLAYER);
									}

									if (w == ShotStatus.MISSED) {//MISSED
										if (mainGame.model.getID().compareTo(ge.getPlayerId()) != 0) {
											mainGame.view.setStatus("You missed");
										} else {
											mainGame.view.setStatus("Enemy missed");
										}
										mainGame.model.changeTurn();
									} else {//SHOT
										if (w == ShotStatus.SHOT) {
											if (mainGame.model.getID().compareTo(ge.getPlayerId()) != 0) {
												mainGame.view.setStatus("You shot the ship, it is not destroyed");
											} else {
												mainGame.view.setStatus("Enemy shot your ship, it is not destroyed");
											}
										} else { // SHOT_AND_DESTROYED
											if (mainGame.model.getID().compareTo(ge.getPlayerId()) != 0) {
												mainGame.view.setStatus("You destroyed enemy ship");
												if(mainGame.model.gameBoard.getNoOfShipsActive() == 0){
													mainGame.view.setStatus("You won");
													mainGame.initGame();
												}
											} else {
												mainGame.view.setStatus("Enemy destroyed your ship.");
												if(mainGame.model.gameBoard.getNoOfShipsActive() == 0){
													mainGame.view.setStatus("You lost");
													mainGame.initGame();
												}
											}
										}
									}
									mainGame.repaintBoard(0);
									mainGame.repaintBoard(1);
								} catch (NumberFormatException ex) {
								}
							}
								break;

							 case GameEvent.SB_PLAYER_QUIT:
							 mainGame.initGame();
							 mainGame.repaintBoard(0);
							 mainGame.repaintBoard(1);
							 mainGame.model.client.stop();
							 if(mainGame.model.server.isRunning()){
								 mainGame.model.server.stop();
								 mainGame.view.connect.setDisable(false);
								 mainGame.view.startServer.setText("Start server");
							 }else{
								 mainGame.view.startServer.setDisable(false);
								 mainGame.view.connect.setText("Connect");
							 }
							 break;
							}
						}
					} else if (!mainGame.model.client.isAlive()) {
						mainGame.model.client.stop();
						mainGame.view.connect.setText("Connect");
						if (mainGame.model.server.isRunning()) {
							mainGame.model.server.stop();
							mainGame.view.connect.setDisable(false);
							mainGame.view.startServer.setText("Start server");
						} else {
							mainGame.view.startServer.setDisable(false);
						}
						mainGame.view.setStatus("Broken connection");
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException ex) {
					}
				}
			}
		}.start();
		// Setting up a view thread:
		launch();
	}
}
