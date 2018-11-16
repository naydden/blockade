package game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javafx.scene.paint.Color;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import scenes.FinalScreen;
import scenes.MainScreen;
import scenes.WelcomeScreenMulti;
import scenes.WelcomeScreenSingle;
import scenes.WelcomeScreens;

public class GameMain extends GameEngine {
	private ArrayList<Node> allNodes;
	private ArrayList<Snake> snakes;
	private Movement movement1;
	private Movement movement2;
	StackPane root;

	public GameMain() {
		this.snakes = new ArrayList<>();
		this.allNodes = new ArrayList<Node>();	
	}

	/** Application main method */
	public static void main(String[] args) {
		Application.launch(args);
	}
	public void initializeSnake(String name, Movement movement) {
		Position startPos = new Position((new Random().nextInt((int) GRID_SIZE - 2) + 1) * 24, WINDOW_SIZE / 2);
		Random rand = new Random();
		float r = rand.nextFloat(); float g = rand.nextFloat(); float b = rand.nextFloat();
		Color randomColor = Color.color(r,g,b);
		Snake snake = new Snake(name, startPos, randomColor, movement);
		this.snakes.add(snake);
	}

	public ArrayList<Node> gameStep() throws Exception {
		for (Snake snake : snakes) {
			snake.move(allNodes);
		}
		for (Snake snake : snakes) {
			ArrayList<Node> allAddedNodes = snake.getAllParts();
			for (Node x : allAddedNodes) {
				if (!allNodes.contains(x))
					allNodes.add(x);
			}
		}	
		return allNodes;
	}

	public void welcomeScreen(StackPane root) {
		this.snakes = new ArrayList<>();
		this.allNodes = new ArrayList<Node>();	
		this.root = root;
		MainScreen ms = new MainScreen(this);
		root.getChildren().setAll(ms);
		StackPane.setAlignment(root, Pos.CENTER);
	}

	public void welcomeSubScreen(WelcomeScreens option) {
		if(option == WelcomeScreens.SINGLE) {
			WelcomeScreenSingle wss = new WelcomeScreenSingle(this);
			root.getChildren().setAll(wss);
		}
		else if (option == WelcomeScreens.MULTI) {
			WelcomeScreenSingle wss = new WelcomeScreenSingle(this);
			root.getChildren().setAll(wss);
		}

		StackPane.setAlignment(root, Pos.CENTER);
	}
	
	public void finalScreen(StackPane root,String message) {
		this.root = root;
		FinalScreen fs = new FinalScreen(this,message,root);
		root.getChildren().setAll(fs);
		StackPane.setAlignment(root, Pos.CENTER);
	}
	public void setMovement1(Movement mov) { this.movement1 = mov; }
	public void setMovement2(Movement mov) { this.movement2 = mov; }
	public Movement getMovement1() { return this.movement1 ; }
	public Movement getMovement2() { return this.movement2; }


}
