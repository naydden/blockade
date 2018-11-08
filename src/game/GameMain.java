package game;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.paint.Color;

public class GameMain extends GameEngine {
	private Snake snake;
	private ArrayList<Node> allNodes;
	
	public GameMain() 
	{
		snake = new Snake(WINDOW_SIZE/2, WINDOW_SIZE/2, Color.AQUAMARINE);
		this.allNodes = new ArrayList<Node>();;
	}
	
	/** Application main method	 */
	public static void main(String[] args) { Application.launch(args); }
	
	public ArrayList<Node> gameStep()
	{
		try {
			snake.move();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<Node> allAddedNodes = snake.getAllParts();
		for (Node x : allAddedNodes){
			   if (!allNodes.contains(x))
			      allNodes.add(x);
		}
		return allNodes;
	}
	

}
