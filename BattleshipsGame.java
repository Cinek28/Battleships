import Model.ActualPlayer;
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
//		while (true) {
//			if (GameController.model.client != null && GameController.model.client.isAlive()) {
//					GameEvent ge;
//					while (model.client != null && model.client.isAlive()
//							&& (ge = model.client.receiveMessage()) != null) {
//						switch (ge.getType()) {
//						case GameEvent.SB_CHAT_MSG:
//							if (model.getID().compareTo(ge.getPlayerId()) == 0) {
//								mainGame.addToChat("YOU > ");
//							} else {
//								mainGame.addToChat("ENEMY > ");
//							}
//
//							mainGame.addToChat(ge.getMessage() + "\n");
//							break;
//
//						case GameEvent.SB_LOGIN:
//							if (model.getID().compareTo(ge.getMessage()) != 0) {
//								GameController.view.setStatus("Player " + ge.getPlayerId() + "joined");
//							}
//							break;
//
//						case GameEvent.SB_CAN_JOIN_GAME:
//							nowaGra.setEnabled(true);
//							break;
//
//						case GameEvent.SB_PLAYER_JOINED:
//							if (model.getID().compareTo(ge.getMessage()) == 0) {
//								zmienStatus("Oczekiwanie na gotowoæ przeciwnika...",
//										RodzajWiadomosci.WIADOMOSC_POZYTYWNA);
//							} else if (nowaGra.isEnabled()) {
//								if (losuj.isEnabled()) {
//									zmienStatus(
//											"Przeciwnik jest ju¿ gotowy\nUstaw swoje statki a nastêpnie nacinij przycisk \"Rozpocznij grê\"",
//											RodzajWiadomosci.WIADOMOSC_NEUTRALNA);
//								} else {
//									zmienStatus(
//											"Przeciwnik jest ju¿ gotowy\nNacinij przycisk \"Nowa gra\" i ustaw swoje statki, a nastêpnie nacinij przycisk \"Rozpocznij grê\"",
//											RodzajWiadomosci.WIADOMOSC_NEUTRALNA);
//								}
//							}
//							break;
//						case GameEvent.SB_START_GAME:
//							if (model.getWhoseTurn() == ActualPlayer.PLAYER) {
//								GameController.view.setStatus("Game started. Your turn.");
//							} else {
//								GameController.view.setStatus("Game started. Enemy's turn.");
//							}
//							break;
//
//						case GameEvent.SB_SHOT:
//							if (model.getID().compareTo(ge.getPlayerId()) != 0) {
//								String s = ge.getMessage();
//								int idx1 = s.indexOf('|');
//								String a = s.substring(0, idx1);
//								String b = s.substring(idx1 + 1);
//
//								try {
//									int x = Integer.parseInt(a);
//									int y = Integer.parseInt(b);
//									ShotStatus w = model.gameBoard.checkShot(x, y);
//									GameEvent geOut = new GameEvent(GameEvent.C_SHOT_RESULT);
//									geOut.setMessage(x + "|" + y + "|" + w.ordinal());
//									model.client.sendMessage(geOut);
//								} catch (NumberFormatException ex) {
//								}
//
//							}
//							break;
//
//						case GameEvent.SB_SHOT_RESULT: {
//							String s = ge.getMessage();
//							int idx1 = s.indexOf('|');
//							int idx2 = s.indexOf('|', idx1 + 1);
//							String a = s.substring(0, idx1);
//							String b = s.substring(idx1 + 1, idx2);
//							String c = s.substring(idx2 + 1);
//
//							try {
//								int x = Integer.parseInt(a);
//								int y = Integer.parseInt(b);
//								int n = Integer.parseInt(c);
//								ShotStatus w = ShotStatus.values()[n];
//
//								if (model.getID().compareTo(ge.getPlayerId()) != 0) {
//									model.gameBoard.setShot(x, y, w, ActualPlayer.ENEMY);
//								} else {
//									model.gameBoard.setShot(x, y, w, ActualPlayer.PLAYER);
//								}
//
//								if (w == ShotStatus.MISSED) {
//									if (model.getID().compareTo(ge.getPlayerId()) != 0) {
//										zmienStatus(
//												"Nie trafi³e\nTeraz strzela przeciwnik",
//												RodzajWiadomosci.WIADOMOSC_NEGATYWNA);
//									} else {
//										zmienStatus(
//												"Przeciwnik nie trafi³\nTeraz twoja kolej, strzelaj!",
//												RodzajWiadomosci.WIADOMOSC_POZYTYWNA);
//										setToken(true);
//									}
//								} else {
//									if (w == ShotStatus.SHOT) {
//										if (model.getID().compareTo(ge.getPlayerId()) != 0) {
//											zmienStatus(
//													"Trafi³e statek przeciwnika, ale nie jest on jeszcze SHOT_AND_DESTROYED\nStrzelaj jeszcze raz!",
//													RodzajWiadomosci.WIADOMOSC_POZYTYWNA);
//											setToken(true);
//										} else {
//											zmienStatus(
//													"Przeciwnik trafi³ w twój statek, ale nie jest on jeszcze SHOT_AND_DESTROYED\nKolejny strza³ nale¿y do przeciwnika",
//													RodzajWiadomosci.WIADOMOSC_NEGATYWNA);
//										}
//									} else { // SHOT_AND_DESTROYED
//										if (model.getID().compareTo(ge.getPlayerId()) != 0) {
//											zmienStatus(
//													"Zatopi³e statek przeciwnika!\nStrzelaj jeszcze raz!",
//													RodzajWiadomosci.WIADOMOSC_POZYTYWNA);
//											statkiPrzeciwnikaPodsumowanie
//													.setText(++trafioneStatkiPrzeciwnika
//															+ "/" + liczbaStatkow);
//											if (trafioneStatkiPrzeciwnika == liczbaStatkow) {
//												zmienStatus(
//														"WYGRA£E!!!\nZatopi³e wszystkie statki przeciwnika!\nJeli chcesz rozpocz¹æ now¹ grê nacinij przycisk\n\"Nowa Gra\"",
//														RodzajWiadomosci.WIADOMOSC_POZYTYWNA);
//												GameEvent geOut = new GameEvent(
//														GameEvent.C_QUIT_GAME);
//												sendMessage(geOut);
//												kolejnaGra();
//											} else {
//												setToken(true);
//											}
//										} else {
//											zmienStatus(
//													"Przeciwnik zatopi³ twój statek!\nKolejny strza³ nale¿y do przeciwnika",
//													RodzajWiadomosci.WIADOMOSC_NEGATYWNA);
//											statkiGraczaPodsumowanie
//													.setText(++trafioneStatkiGracza + "/"
//															+ liczbaStatkow);
//											if (trafioneStatkiGracza == liczbaStatkow) {
//												zmienStatus(
//														"PRZEGRA£E!!!\nPrzeciwnik zatopi³ ca³¹ twoj¹ flotê!\nJeli chesz rozpocz¹æ now¹ grê nacinij przycisk \"Nowa Gra\"",
//														RodzajWiadomosci.WIADOMOSC_POZYTYWNA);
//												kolejnaGra();
//											}
//										}
//									}
//								}
//							} catch (NumberFormatException ex) {
//							}
//						}
//							break;
//
//						case GameEvent.SB_PLAYER_QUIT:
//							zerwanePolaczenie();
//							break;
//
//						case GameEvent.S_TOO_MANY_CONNECTIONS:
//							if (client != null)
//								client.stop();
//							client = null;
//							ustawOdNowa();
//							zmienStatus(
//									"Próba po³¹czenia zakoñczona niepowodzeniem!\nW grze zanjduje siê ju¿ 2 graczy",
//									RodzajWiadomosci.WIADOMOSC_NEGATYWNA);
//							break;		
//						}
//					}
//				}
//			} else if (clientStarted && client != null) {
//				client.stop();
//				client = null;
//				zerwanePolaczenie();
//			}
//			try {
//				Thread.sleep(20);
//			} catch (InterruptedException ex) {
//			}
//		}

}}
