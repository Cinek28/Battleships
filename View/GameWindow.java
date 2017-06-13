package View;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
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
	   private Label yourBoard = new Label("YOUR BOARD");

	   private TextArea chatArea = new TextArea();
	   private TextField hostIpField = new TextField();
	   private TextField hostPortField = new TextField();
	   private TextField chatField = new TextField();
	   private TextField serverPort = new TextField();
	   private TextField statusField = new TextField("Welcome!");
	   private Button send = new Button("Send message");
	   private Button startServer = new Button("Start server");
 	   private Button connect = new Button("Connect to server");
	   private Rectangle enemyBoard [][] = new Rectangle[10][10];
	   private Rectangle myBoard [][] = new Rectangle[10][10];
	   private Group myBoardGroup = new Group();
	   private Group enemyBoardGroup = new Group();
	   
 	   /**Initializing window: */
	   
	   private void setBoards(){
		   int width = 30;
		   int height = 30;
		   for (int row = 0; row < 10; ++row) {
		        for (int col = 0; col < 10; ++col) {
		        	myBoard[row][col] = new Rectangle(20 + col*width,20+ row*height, width, height);
		        	myBoard[row][col].setFill(Color.GREEN);
		        	myBoard[row][col].setStroke(Color.BLACK);
		        	myBoardGroup.getChildren().addAll(myBoard[row][col]);
		            }
		    }
		  
		   for (int row = 0; row < 10; ++row) {
		        for (int col = 0; col < 10; ++col) {
		        	enemyBoard[row][col] = new Rectangle(20 + col*width,20 +row*height, width, height);
		        	enemyBoard[row][col].setFill(Color.GRAY);
		        	enemyBoard[row][col].setStroke(Color.BLACK);
		        	enemyBoardGroup.getChildren().addAll(enemyBoard[row][col]);
		        }
		   }
		   
	   }
	   private Parent setParent(){
		   //Buttons initialization:
		   startServer.setPrefSize(150, 10);
		   send.setPrefSize(150, 10);
		   connect.setPrefSize(150, 10);
		   startServer.setAlignment(Pos.CENTER);
		   send.setAlignment(Pos.CENTER);
		   connect.setAlignment(Pos.CENTER);
		   //Boards:
		   setBoards();
		   GameController.repaintBoard(this, 0);
		   GameController.repaintBoard(this, 1);
		   //Overriding button actions:
		   
		   startServer.setOnAction(new EventHandler<ActionEvent>() {
			    @Override 
			    public void handle(ActionEvent e) {
			    	String serverPortStr = serverPort.getText();
			    	GameController.startServer(serverPortStr);
//			    	setBoards();
			    }
		   });
		   
		   connect.setOnAction(new EventHandler<ActionEvent>() {
			    @Override 
			    public void handle(ActionEvent e) {
			    	String hostIpStr = hostIpField.getText();
			    	String hostPortStr = hostPortField.getText();
			    	GameController.connectToServer(hostIpStr,hostPortStr);
			    }
		   });
		   
		   send.setOnAction(new EventHandler<ActionEvent>() {
			    @Override 
			    public void handle(ActionEvent e) {
			    	String messageStr = chatField.getText();
			    	GameController.sendMessage(messageStr);
			    	}
		   });
		   //TextField initialization:
		   chatField.setPrefSize(150, 10);
		   hostIpField.setPrefSize(150, 10);
		   hostPortField.setPrefSize(150, 10);
		   serverPort.setPrefSize(150, 10);
		   statusField.setEditable(false);
		   statusField.setPrefSize(300, 20);
		   statusField.setMaxSize(300, 20);
		   statusField.setMinSize(300, 20);
		   statusField.setAlignment(Pos.CENTER_RIGHT);
		   chatArea.setPrefSize(300, 100);
		   chatArea.setMaxSize(300, 100);
		   chatArea.setMinSize(300, 100);
		   chatArea.setEditable(false);
		   
		   //Labels:
		   yourBoard.setPrefSize(100,10);
		   hostIpLabel.setTextFill(Color.RED);
		   hostPortLabel.setTextFill(Color.RED);
		   serverPortLabel.setTextFill(Color.RED);
		   yourBoard.setTextFill(Color.YELLOW);
		   
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
		   HBox ss = new HBox(startServer,connect);
		   ss.setAlignment(Pos.BOTTOM_RIGHT);
		   configs.setAlignment(Pos.BOTTOM_RIGHT);
		   configs.getChildren().addAll(hostIp, hostConnect, ip, ss, statusField);
		   configs.setMaxWidth(200);
		   TilePane messageSend = new TilePane(Orientation.HORIZONTAL);
		   messageSend.setHgap(1.0);
		   messageSend.setVgap(1.0);
		   messageSend.getChildren().addAll(chatField, send);
		   HBox chatt = new HBox(chatArea);
		   TilePane chat = new TilePane(Orientation.VERTICAL);
		   chat.setAlignment(Pos.CENTER_RIGHT);
		   chat.setHgap(1.0);
		   chat.setVgap(1.0);
		   chat.getChildren().addAll(chatt,messageSend);
		   BackgroundImage image = new BackgroundImage(new Image("/View/battleship.jpg",800,600,false,true),
				   BackgroundRepeat.NO_REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		   GridPane root = new GridPane();
		   root.getColumnConstraints().add(new ColumnConstraints(400));
		   root.getRowConstraints().add(new RowConstraints(350)); 
		   GridPane.setConstraints(configs, 0, 1, 1, 1, HPos.RIGHT, VPos.CENTER );
		   GridPane.setConstraints(chat,1,1,1,1,HPos.RIGHT,VPos.CENTER);
		   root.getChildren().addAll(configs,chat);
		   
		   GridPane.setConstraints(myBoardGroup, 0,  0, 1 , 1,HPos.CENTER, VPos.CENTER);
		   GridPane.setConstraints(yourBoard, 0, 0, 1, 1, HPos.CENTER, VPos.BOTTOM);
		   root.getChildren().addAll(myBoardGroup,yourBoard);
		   GridPane.setConstraints(enemyBoardGroup, 1,  0, 1 , 1,HPos.LEFT, VPos.CENTER);
		   root.getChildren().addAll(enemyBoardGroup);
		   //For debug:
//		   root.setGridLinesVisible(true);
		   root.setPrefSize(800, 600);
		   root.setMaxSize(800, 600);
		   root.setMinSize(800, 600);
		   root.setBackground(new Background(image));
		   return root;
	   }
	   
	   
	   @Override     
	   public void start(Stage primaryStage) throws Exception {            
		      //Setting the title to Stage. 
		      primaryStage.setTitle("Battleships"); 
		      
		      Scene scene = new Scene(setParent());
		      
		      scene.setOnMousePressed(new EventHandler<MouseEvent>() {
		          @Override
		          public void handle(MouseEvent event) {
		          }
		      });
		      //Adding the scene to Stage 
		      primaryStage.setScene(scene);
		      primaryStage.setResizable(false);
		       
		      //Displaying the contents of the stage 
		      primaryStage.show(); 
		      
		   }
	   public void setBoardColor(Color color, int row, int column, int whichBoard){
		   if(whichBoard == 0){
			   myBoard[row][column].setFill(color);
		   }
		   else{
			   enemyBoard[row][column].setFill(color);
		   }
	   }
	   
	   public static void main(String args[]){           
	      launch(args);      
	   } 
}
