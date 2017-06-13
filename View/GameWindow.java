package View;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene; 
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.TilePane;

import Controller.*;


public class GameWindow extends Application{
	   private Label hostIpLabel = new Label("Host IP:");
	   private Label hostPortLabel = new Label("Host port:");
	   private Label serverPortLabel = new Label("Server port:");
	   private TextArea chatArea = new TextArea();
	   private TextField hostIpField = new TextField();
	   private TextField hostPortField = new TextField();
	   private TextField chatField = new TextField();
	   private TextField serverPort = new TextField();
	   private Button send = new Button("Send message");
	   private Button startServer = new Button("Start server");
 	   private Button connect = new Button("Connect to server");
	   private Rectangle enemyBoard [][] = new Rectangle[10][10];
	   private Rectangle myBoard [][] = new Rectangle[10][10];
 	   /**Initializing window: */
	   private Parent setParent(){
		   /** Buttons initialization: */
		   startServer.setPrefSize(150, 10);
		   send.setPrefSize(150, 10);
		   connect.setPrefSize(150, 10);
		   //Overriding button actions:
		   
		   startServer.setOnAction(new EventHandler<ActionEvent>() {
			    @Override 
			    public void handle(ActionEvent e) {
//			    	String hostName = serverIpField.getText();
			    	String serverPortStr = serverPort.getText();
			    	GameController.startServer(serverPortStr);
			    }
		   });
		   
		   connect.setOnAction(new EventHandler<ActionEvent>() {
			    @Override 
			    public void handle(ActionEvent e) {
			    	String hostIpStr = hostIpField.getText();
			    	String hostPortStr = hostPortField.getText();
			    	
			    }
		   });
		   
		   send.setOnAction(new EventHandler<ActionEvent>() {
			    @Override 
			    public void handle(ActionEvent e) {
			    	String messageStr = chatField.getText();
			    	
			    	}
		   });
		   /**TextField initialization: */
		   chatField.setPrefSize(150, 10);
		   hostIpField.setPrefSize(150, 10);
		   hostPortField.setPrefSize(150, 10);
		   serverPort.setPrefSize(150, 10);
		   chatArea.setPrefSize(300, 100);
		   chatArea.setMaxSize(300, 100);
		   chatArea.setMinSize(300, 100);
		   chatArea.setEditable(false);
		   
		   Group myBoardGroup = new Group();
		   Group enemyBoardGroup = new Group();
		   
		   TilePane hostConnect = new TilePane(Orientation.HORIZONTAL);
		   hostConnect.setAlignment(Pos.CENTER_RIGHT);
		   hostConnect.setHgap(1.0);
		   hostConnect.setVgap(1.0);
		   hostConnect.getChildren().addAll(hostPortLabel, hostPortField);
		   TilePane hostIp = new TilePane(Orientation.HORIZONTAL);
		   hostIp.setAlignment(Pos.CENTER_RIGHT);
		   hostIp.setHgap(1.0);
		   hostIp.setVgap(1.0);
		   hostIp.getChildren().addAll(hostIpLabel, hostIpField);
		   TilePane ip = new TilePane(Orientation.HORIZONTAL);
 		   ip.setAlignment(Pos.CENTER_RIGHT);
		   ip.setHgap(1.0);
		   ip.setVgap(1.0);
		   ip.getChildren().addAll(serverPortLabel, serverPort);
		   TilePane configs = new TilePane(Orientation.VERTICAL);
		   configs.setHgap(1.0);
		   configs.setVgap(1.0);
		   startServer.setAlignment(Pos.CENTER_RIGHT);
		   connect.setAlignment(Pos.CENTER_RIGHT);
		   HBox ss = new HBox(startServer,connect);
		   ss.setAlignment(Pos.BOTTOM_RIGHT);
		   configs.setAlignment(Pos.CENTER_RIGHT);
		   configs.getChildren().addAll(hostIp, hostConnect, ip, ss);
		   configs.setMaxWidth(200);
		   TilePane messageSend = new TilePane(Orientation.HORIZONTAL);
		   messageSend.setHgap(1.0);
		   messageSend.setVgap(1.0);
		   messageSend.getChildren().addAll(chatField, send);
		   HBox chatt = new HBox(chatArea);
		   TilePane chat = new TilePane(Orientation.VERTICAL);
		   chat.setAlignment(Pos.CENTER_LEFT);
		   chat.setHgap(1.0);
		   chat.setVgap(1.0);
		   chat.getChildren().addAll(chatt,messageSend);
		   
		   GridPane root = new GridPane();
		   root.getColumnConstraints().add(new ColumnConstraints(400));
		   root.getRowConstraints().add(new RowConstraints(350)); 
		   GridPane.setConstraints(configs, 0, 1, 1, 1, HPos.RIGHT, VPos.CENTER );
		   GridPane.setConstraints(chat,1,1,1,1,HPos.RIGHT,VPos.CENTER);
		   root.getChildren().addAll(configs,chat);
		   
		   //Adding boards:
		   
		   int width = 35;
		   int height = 35;
		   for (int r = 0; r < 10; r++) {
		        for (int c = 0; c < 10; c++) {
		        	myBoard[r][c] = new Rectangle(20 + r*width,20+ c*height, width, height);
		        	myBoard[r][c].setFill(Color.GREEN);
		        	myBoard[r][c].setStroke(Color.BLACK);
		        	myBoardGroup.getChildren().addAll(myBoard[r][c]);
		            }
		    }
		  
		   for (int r = 0; r < 10; r++) {
		        for (int c = 0; c < 10; c++) {
		        	enemyBoard[r][c] = new Rectangle(20 + r*width,20 + c*height, width, height);
		        	enemyBoard[r][c].setFill(Color.GRAY);
		        	enemyBoard[r][c].setStroke(Color.BLACK);
		        	enemyBoardGroup.getChildren().addAll(enemyBoard[r][c]);
		        }
		   }
		   GridPane.setConstraints(myBoardGroup, 0,  0, 1 , 1,HPos.CENTER, VPos.CENTER);
		   root.getChildren().addAll(myBoardGroup);
		   GridPane.setConstraints(enemyBoardGroup, 1,  0, 1 , 1,HPos.LEFT, VPos.CENTER);
		   root.getChildren().addAll(enemyBoardGroup);
		   root.setGridLinesVisible(true);
		   root.setPrefSize(800, 600);
		   root.setMaxSize(800, 600);
		   root.setMinSize(800, 600);
		   return root;
	   }
	   
	   
	   @Override     
	   public void start(Stage primaryStage) throws Exception {            
		      
		      //Setting the title to Stage. 
		      primaryStage.setTitle("Battleships"); 
		   
		      //Adding the scene to Stage 
		      primaryStage.setScene(new Scene(setParent()));
		      primaryStage.setResizable(false);
		       
		      //Displaying the contents of the stage 
		      primaryStage.show(); 
		   }
	   
	   public void setBoardColor(Color color, int row, int column, int whichBoard){
		   if(whichBoard == 0){
			   myBoard[row][column].setFill(color);;
		   }
		   else{
			   enemyBoard[row][column].setFill(color);;
		   }
	   }
	   
	   public static void main(String args[]){           
	      launch(args);      
	   } 
}
