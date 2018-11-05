package game;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.paint.Color;

public class SimpleExample extends Game {
	private Egg egg;
	private ArrayList<Node> allNodes;
	
	public SimpleExample() 
	{
		egg = new Egg(WINDOW_SIZE/2, WINDOW_SIZE/2, Color.AQUAMARINE);
		this.allNodes = new ArrayList<Node>();
		this.allNodes.add(egg);
	}
	
	/** Application main method	 */
	public static void main(String[] args) { Application.launch(args); }
	
	public ArrayList<Node> gameStep()
	{
		egg.move();
		return allNodes;
	}
	

}
