package Controller;

import Model.*;
import View.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GameController implements EventHandler<Event>{
	private static GameModel model;
    private static GameWindow view; 
    
    public GameController(GameWindow view){
    	GameController.view = view;
    }
    
	public void initGame(GameModel model) {
		GameController.model = model;
		GameController.model.gameBoard.clearEnemyBoard();
		GameController.model.gameBoard.clearBoard();
		GameController.model.gameBoard.setBoardRandom();
//		repaintBoard(0);
//		repaintBoard(1);
		
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
	
    public void handleActionEvent(ActionEvent event){
        //handle buttons events event:
    	Object eventSource = event.getSource();
    	if(GameController.view.startServer.equals(eventSource)){
    		System.out.println("XXXX");
    	}
    	
    }

	public void handleMouseEvent(MouseEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();
		repaintBoard(0);
		repaintBoard(1);
		
	}

	@Override
	public void handle(Event event) {

	}
}
