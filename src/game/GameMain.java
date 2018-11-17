package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javafx.scene.paint.Color;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import scenes.FinalScreen;
import scenes.MainScreen;
import scenes.WelcomeScreenMulti;
import scenes.WelcomeScreenSingle;
import scenes.WelcomeScreens;

public class GameMain extends GameEngine {
	private ArrayList<Node> allNodes;
	private List<Snake> snakes;
	private List<Movement> movements;
	StackPane root;

	public GameMain() {
		/**
		 * snakes and movements are in a fixed size array.
		 */
		this.snakes = Arrays.asList(new Snake[2]);
		this.movements = Arrays.asList(new Movement[2]);
		this.allNodes = new ArrayList<Node>();
	}

	/** Application main method */
	public static void main(String[] args) {
		Application.launch(args);
	}
	public void initializeSnake(int i, String name) {
		Position startPos = new Position((new Random().nextInt((int) GRID_SIZE - 2) + 1) * 24, WINDOW_SIZE / 2);
		Random rand = new Random();
		float r = rand.nextFloat(); float g = rand.nextFloat(); float b = rand.nextFloat();
		Color randomColor = Color.color(r,g,b);
		Snake snake = new Snake(name, startPos, randomColor);
		this.snakes.set(i,snake);
	}

	public ArrayList<Node> gameStep() throws Exception {
//		intercambio inicial de posiciones iiciales. Con esos datos la serpiente ya pude tirar, y envia los objetos
//		la serpiente del otro lado puede usar estas posiciones para tomar su camino.
//		move snake 1, receive move positions from snake 2(getAllParts), send positions after move of snke1
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
		this.snakes = Arrays.asList(new Snake[2]);
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
			WelcomeScreenMulti wsm = new WelcomeScreenMulti(this);
			root.getChildren().setAll(wsm);
		}

		StackPane.setAlignment(root, Pos.CENTER);
	}
	
	public void finalScreen(StackPane root,String message) {
		this.root = root;
		FinalScreen fs = new FinalScreen(this,message,root);
		root.getChildren().setAll(fs);
		StackPane.setAlignment(root, Pos.CENTER);
	}
	public void setMovement(int i, Movement mov) { this.movements.set(i, mov); }
	public Movement getMovement(int i) { return this.movements.get(i) ; }
	public Snake getSnake(int i) { return this.snakes.get(i) ; }

}
