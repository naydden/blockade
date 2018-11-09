package game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.paint.Color;

public class GameMain extends GameEngine {
	private Snake snake1;
	private Snake snake2;
	private ArrayList<Node> allNodes;
	private Set<Position> allPositions;
	
	public GameMain() 
	{
		allPositions = new HashSet<Position>();
		
		Position startPos1 = new Position(new Random().nextInt((int)GRID_SIZE)*24, WINDOW_SIZE/2);
		Position startPos2 = new Position(new Random().nextInt((int)GRID_SIZE)*24, WINDOW_SIZE/2);
		snake1 = new Snake(startPos1, Color.AQUAMARINE);
		snake2 = new Snake(startPos2, Color.GREEN);
		
		this.allNodes = new ArrayList<Node>();
	}
	
	/** Application main method	 */
	public static void main(String[] args) { Application.launch(args); }
	
	public ArrayList<Node> gameStep() throws Exception
	{
		snake1.move(allPositions);
		snake2.move(allPositions);
		
		Set<Position> updatedPositions = new HashSet<>();
		ArrayList<Node> allAddedNodes1 = snake1.getAllParts();
		updatedPositions.addAll(snake1.getAllPositions());
		ArrayList<Node> allAddedNodes2 = snake2.getAllParts();
		updatedPositions.addAll(snake2.getAllPositions());
		
		System.out.println(updatedPositions);
		this.allPositions = updatedPositions;
		
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
	

}
