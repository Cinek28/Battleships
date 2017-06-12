package View;

import javafx.application.Application; 
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene; 
import javafx.scene.paint.Color; 
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;


public class GameWindow extends Application{
	   private TextArea chatArea = new TextArea();
	   private TextField hostIPField = new TextField();
	   private TextField hostPortField = new TextField();
	   private TextField chatField = new TextField();
	   private Button send = new Button();
	   private Button startServer = new Button();
//	   private Button  = new Button();
		
	   private Parent setParent(){
		   chatArea.setPrefHeight(100);
		   send.setText("Send");
		   VBox left = new VBox(20,chatArea,chatField);
		   VBox right = new VBox(20,send,hostIPField);
		   HBox root = new HBox(20,left,right);
		   root.setPrefSize(600, 600);
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
