package game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameMain extends GameEngine {
	private Snake snake1;
	private Snake snake2;
	private ArrayList<Node> allNodes;
	
	public GameMain() 
	{
		initializeGame();
	}
	
	/** Application main method	 */
	public static void main(String[] args) { Application.launch(args); }
	
	public ArrayList<Node> gameStep() throws Exception
	{
		snake1.move(allNodes);
		snake2.move(allNodes);
		
		ArrayList<Node> allAddedNodes1 = snake1.getAllParts();
		ArrayList<Node> allAddedNodes2 = snake2.getAllParts();
		
		for (Node x : allAddedNodes1){
			   if (!allNodes.contains(x))
			      allNodes.add(x);
		}
		for (Node x : allAddedNodes2){
			   if (!allNodes.contains(x))
			      allNodes.add(x);
		}
		return allNodes;
	}
	
	public void welcomeScreen(Stage primaryStage, Scene scene, StackPane root) {
		primaryStage.setScene(scene);
		primaryStage.setTitle(TITLE);
		primaryStage.setResizable(false);
		primaryStage.show();
		
		Text welcomeText = new Text();
		welcomeText.setText("B L O C K A D E");
		// Setting font to the text
		welcomeText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		welcomeText.setFill(Color.RED);
		
		Button btnStart = new Button("Start Game");
		
		btnStart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gameScreen();
            }
        });
		
		Text author = new Text();
		author.setText("Boyan Naydenov");
		// Setting font to the text
		author.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
		author.setFill(Color.BLACK);
		
		VBox vbox = new VBox();

		vbox.setSpacing(10);
		vbox.setPadding(new Insets(5));
		vbox.setAlignment(Pos.CENTER);

		vbox.getChildren().addAll(welcomeText, btnStart, author);
		     
	     
		root.getChildren().setAll(vbox);
		StackPane.setAlignment(root, Pos.CENTER);
	}
	public void initializeGame() {
		Position startPos1 = new Position((new Random().nextInt((int)GRID_SIZE-2)+1)*24, WINDOW_SIZE/2);
		Position startPos2 = new Position((new Random().nextInt((int)GRID_SIZE-2)+1)*24, WINDOW_SIZE/2);
		snake1 = new Snake(startPos1, Color.AQUAMARINE, "R");
		snake2 = new Snake(startPos2, Color.GREEN, "L");
		
		this.allNodes = new ArrayList<Node>();
	}
	public void finalScreen(Stage primaryStage, Scene scene, StackPane root) {
		primaryStage.setScene(scene);
		primaryStage.setTitle(TITLE);
		primaryStage.setResizable(false);
		primaryStage.show();
		// Creating a Text object
		Rectangle backgroundFill = new Rectangle(WINDOW_SIZE, WINDOW_SIZE, Color.ALICEBLUE);

		Text text = new Text();
		text.setText("Your snake has crashed!");
		// Setting font to the text
		text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		text.setFill(Color.RED);

		Button buttonExit = new Button("Exit");
		buttonExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
        		Platform.exit();
        		System.exit(0);
            }
        });
		Button buttonStartAgain = new Button("Start Again");
		
		buttonStartAgain.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	initializeGame();
                welcomeScreen(primaryStage, scene, root);
            }
        });

		Text author = new Text();
		author.setText("Boyan Naydenov");
		// Setting font to the text
		author.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
		author.setFill(Color.BLACK);
		
		VBox vbox = new VBox();

		vbox.setSpacing(10);
		vbox.setPadding(new Insets(5));
		vbox.setAlignment(Pos.CENTER);

		vbox.getChildren().addAll(text,buttonExit, buttonStartAgain, author);

		root.getChildren().add(backgroundFill);
		root.getChildren().add(vbox);
		StackPane.setAlignment(root, Pos.CENTER);
	}

}
