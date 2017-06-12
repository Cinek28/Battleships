package View;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
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
import javafx.scene.layout.VBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.TilePane;


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
		
	   private Parent setParent(){
		   /** Buttons initialization: */
		   startServer.setPrefSize(150, 10);
		   send.setPrefSize(150, 10);
		   connect.setPrefSize(150, 10);
		   /**TextField initialization: */
		   chatField.setPrefSize(150, 10);
		   hostIpField.setPrefSize(150, 10);
		   hostPortField.setPrefSize(150, 10);
		   serverPort.setPrefSize(150, 10);
		   chatArea.setPrefSize(300, 100);
		   
		   TilePane hostConnect = new TilePane(Orientation.HORIZONTAL);
		   hostConnect.setHgap(1.0);
		   hostConnect.setVgap(1.0);
		   hostConnect.getChildren().addAll(hostPortLabel, hostPortField, connect);
		   TilePane hostIp = new TilePane(Orientation.HORIZONTAL);
		   hostIp.setHgap(1.0);
		   hostIp.setVgap(1.0);
		   hostIp.getChildren().addAll(hostIpLabel, hostIpField);
		   TilePane ip = new TilePane(Orientation.HORIZONTAL);
		   ip.setHgap(1.0);
		   ip.setVgap(1.0);
		   ip.getChildren().addAll(serverPortLabel, serverPort, startServer);
		   TilePane configs = new TilePane(Orientation.VERTICAL);
		   configs.setHgap(1.0);
		   configs.setVgap(1.0);
		   configs.getChildren().addAll(hostIp, hostConnect, ip);
		   configs.setMaxWidth(200);
		   TilePane messageSend = new TilePane(Orientation.HORIZONTAL);
		   messageSend.setHgap(1.0);
		   messageSend.setVgap(1.0);
		   messageSend.getChildren().addAll(chatField, send);
		   TilePane chat = new TilePane(Orientation.VERTICAL);
		   chat.setHgap(1.0);
		   chat.setVgap(1.0);
		   chat.getChildren().addAll(chatArea,messageSend);
		   GridPane myBoard = new GridPane();
		   myBoard.getColumnConstraints().add(new ColumnConstraints(7));
		   myBoard.getRowConstraints().add(new RowConstraints(5));
		   int width = 20;
		   int height = 20;
		   for (int r = 0; r < 15; r++) {
		        for (int c = 0; c < 15; c++) {
		        	Rectangle rectangle = new Rectangle(r*width, c*height, width, height);
		        	rectangle.setFill(Color.GREEN);
		        	rectangle.setStroke(Color.BLACK);
		            myBoard.add(rectangle, c, r);
		        }
		    }
		   
		   GridPane enemyBoard = new GridPane();
		   enemyBoard.getColumnConstraints().add(new ColumnConstraints(7));
		   enemyBoard.getRowConstraints().add(new RowConstraints(5));
		   for (int r = 0; r < 15; r++) {
		        for (int c = 0; c < 15; c++) {
		        	Rectangle rectangle = new Rectangle(r*width, c*height, width, height);
		        	rectangle.setFill(Color.GRAY);
		        	rectangle.setStroke(Color.BLACK);
		            enemyBoard.add(rectangle, c, r);
		        }
		   }
		   
		   GridPane root = new GridPane();
		   root.getColumnConstraints().add(new ColumnConstraints(500));
		   root.getRowConstraints().add(new RowConstraints(350)); 
		   GridPane.setConstraints(configs, 0, 1);
		   GridPane.setConstraints(chat,1,1);
		   GridPane.setConstraints(myBoard, 0, 0, 10, 10, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS,new Insets(30,0,0,80));
		   GridPane.setConstraints(enemyBoard, 1, 0, 10, 10, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS,new Insets(30,0,0,80));
		   root.getChildren().addAll(configs,chat,myBoard,enemyBoard);
		   root.setGridLinesVisible(true);
		   root.setPrefSize(1000, 600);
		   root.setMaxSize(1000, 600);
		   root.setMinSize(1000, 600);
		   return root;
	   }
	   
	   
	   @Override     
	   public void start(Stage primaryStage) throws Exception {            
		      //creating a Group object 
//		      Group group = new Group(); 
		       
		      //Creating a Scene by passing the group object, height and width   
//		      Scene scene = new Scene(group ,800, 600); 
		      
		      //setting color to the scene 
//		      scene.setFill(Color.GRAY);  
		      
		      //Setting the title to Stage. 
		      primaryStage.setTitle("Battleships"); 
		   
		      //Adding the scene to Stage 
		      primaryStage.setScene(new Scene(setParent())); 
		       
		      //Displaying the contents of the stage 
		      primaryStage.show(); 
		   }      
	   public static void main(String args[]){           
	      launch(args);      
	   } 
}
