import Controller.GameController;
import Model.ActualPlayer;
import Model.GameModel;
import Model.ShotStatus;
import Model.NetworkInterface.GameEvent;
import View.GameWindow;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.application.Platform;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
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
								System.out.println("Cyka");

								if (mainGame.model.getID().compareTo(ge.getPlayerId()) == 0) {
									mainGame.addToChat("YOU > ");
								} else {
									mainGame.addToChat("ENEMY > ");
								}

								mainGame.addToChat(ge.getMessage() + "\n");
								break;

							case GameEvent.SB_LOGIN:
								if (mainGame.model.getID().compareTo(ge.getMessage()) != 0) {
									mainGame.view.setStatus("Player " + ge.getPlayerId() + "joined");
								}
								break;

							// case GameEvent.SB_CAN_JOIN_GAME:
							// nowaGra.setEnabled(true);
							// break;

							// case GameEvent.SB_PLAYER_JOINED:
							// if (model.getID().compareTo(ge.getMessage())
							// == 0) {
							// zmienStatus("Oczekiwanie na gotowoæ
							// przeciwnika...",
							// RodzajWiadomosci.WIADOMOSC_POZYTYWNA);
							// } else if (nowaGra.isEnabled()) {
							// if (losuj.isEnabled()) {
							// zmienStatus(
							// "Przeciwnik jest ju¿ gotowy\nUstaw swoje
							// statki a
							// nastêpnie nacinij przycisk \"Rozpocznij
							// grê\"",
							// RodzajWiadomosci.WIADOMOSC_NEUTRALNA);
							// } else {
							// zmienStatus(
							// "Przeciwnik jest ju¿ gotowy\nNacinij
							// przycisk \"Nowa
							// gra\" i ustaw swoje statki, a nastêpnie
							// nacinij przycisk
							// \"Rozpocznij grê\"",
							// RodzajWiadomosci.WIADOMOSC_NEUTRALNA);
							// }
							// }
							// break;
							case GameEvent.SB_START_GAME:
								if (mainGame.model.getWhoseTurn() == ActualPlayer.PLAYER) {
									mainGame.view.setStatus("Game started. Your turn.");
								} else {
									mainGame.view.setStatus("Game started. Enemy's turn.");
								}
								break;

							case GameEvent.SB_SHOT:
								if (mainGame.model.getID().compareTo(ge.getPlayerId()) != 0) {
									String s = ge.getMessage();
									int idx1 = s.indexOf('|');
									String a = s.substring(0, idx1);
									String b = s.substring(idx1 + 1);
									System.out.println(a + " " + b);
									try {
										int x = Integer.parseInt(a);
										int y = Integer.parseInt(b);
										ShotStatus w = mainGame.model.gameBoard.checkShot(x, y);
										GameEvent geOut = new GameEvent(GameEvent.C_SHOT_RESULT);
										geOut.setMessage(x + "|" + y + "|" + w.ordinal());
										mainGame.model.client.sendMessage(geOut);
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

									if (w == ShotStatus.MISSED) {
										if (mainGame.model.getID().compareTo(ge.getPlayerId()) != 0) {
											mainGame.view.setStatus("You missed");
										} else {
											mainGame.view.setStatus("Enemy missed");
										}
										mainGame.model.changeTurn();
										System.out.println(mainGame.model.getWhoseTurn());
									} else {
										if (w == ShotStatus.SHOT) {
											if (mainGame.model.getID().compareTo(ge.getPlayerId()) != 0) {
												mainGame.view.setStatus("You shot the ship, it is not destroyed");
											} else {
												mainGame.view.setStatus("Enemy shot your ship, it is not destroyed");
											}
										} else { // SHOT_AND_DESTROYED
											if (mainGame.model.getID().compareTo(ge.getPlayerId()) != 0) {
												mainGame.view.setStatus("You destroyed enemy ship");
											} else {
												mainGame.view.setStatus("Enemy destroyed your ship.");
											}
										}
									}
								} catch (NumberFormatException ex) {
								}
							}
								break;

							// case GameEvent.SB_PLAYER_QUIT:
							// zerwanePolaczenie();
							// break;

							// case GameEvent.S_TOO_MANY_CONNECTIONS:
							// if (client != null)
							// client.stop();
							// client = null;
							// ustawOdNowa();
							// zmienStatus(
							// "Próba po³¹czenia zakoñczona
							// niepowodzeniem!\nW grze
							// zanjduje siê ju¿ 2 graczy",
							// RodzajWiadomosci.WIADOMOSC_NEGATYWNA);
							// break;
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
						Thread.sleep(100);
					} catch (InterruptedException ex) {
					}
				}
			}
		}.start();
		// Setting up a view thread:
		launch();
	}
}
