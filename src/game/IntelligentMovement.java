package game;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;

/**
 * @author bobz
 *
 */
/**
 * @author bobz
 *
 */
public class IntelligentMovement implements Movement {
	private static final double SIZE = GameEngine.ELEMENT_SIZE;
	
	private MovementConfig moveConfig;
	private double headRotation; 
	private Position lastPos;
	private ArrayList<Node> allPartsOfAllSnakes;
	private Group headOfSnake;
	private boolean flag = false;

	public Position nextPosition (double headRotation, Position lastPosition) {
		this.headRotation = headRotation;
		this.lastPos = lastPosition;
		
		Rectangle scout = addBody();
		boolean occupiedN = false;
		boolean occupiedE = false;
		boolean occupiedW = false;
		
		if(headRotation == 0) {
			// North Direction
			
			// Check North
			scout = addBody();
			scout.setTranslateX(headOfSnake.getTranslateX());
			scout.setTranslateY(headOfSnake.getTranslateY()-SIZE);
			occupiedN = checkCollision(scout);
			// Check East
			scout = addBody();
			scout.setTranslateX(headOfSnake.getTranslateX()+SIZE);
			scout.setTranslateY(headOfSnake.getTranslateY());
			occupiedE = checkCollision(scout);
			// Check West
			scout = addBody();
			scout.setTranslateX(headOfSnake.getTranslateX()-SIZE);
			scout.setTranslateY(headOfSnake.getTranslateY());
			occupiedW = checkCollision(scout);			
				
		}
		else if(headRotation == 180) {
			// South Direction
			
			// Check North
			scout = addBody();
			scout.setTranslateX(headOfSnake.getTranslateX());
			scout.setTranslateY(headOfSnake.getTranslateY()+SIZE);
			occupiedN = checkCollision(scout);
			// Check East
			scout = addBody();
			scout.setTranslateX(headOfSnake.getTranslateX()-SIZE);
			scout.setTranslateY(headOfSnake.getTranslateY());
			occupiedE = checkCollision(scout);
			// Check West
			scout = addBody();
			scout.setTranslateX(headOfSnake.getTranslateX()+SIZE);
			scout.setTranslateY(headOfSnake.getTranslateY());
			occupiedW = checkCollision(scout);

		}
		else if(headRotation == 90) {
			// East Direction
			
			// Check North
			scout = addBody();
			scout.setTranslateX(headOfSnake.getTranslateX()+SIZE);
			scout.setTranslateY(headOfSnake.getTranslateY());
			occupiedN = checkCollision(scout);
			// Check East
			scout = addBody();
			scout.setTranslateX(headOfSnake.getTranslateX());
			scout.setTranslateY(headOfSnake.getTranslateY()+SIZE);
			occupiedE = checkCollision(scout);
			// Check West
			scout = addBody();
			scout.setTranslateX(headOfSnake.getTranslateX());
			scout.setTranslateY(headOfSnake.getTranslateY()-SIZE);
			occupiedW = checkCollision(scout);
		}
		else if(headRotation == -90) {
			// West Direction
			
			// Check North
			scout = addBody();
			scout.setTranslateX(headOfSnake.getTranslateX()-SIZE);
			scout.setTranslateY(headOfSnake.getTranslateY());
			occupiedN = checkCollision(scout);
			// Check East
			scout = addBody();
			scout.setTranslateX(headOfSnake.getTranslateX());
			scout.setTranslateY(headOfSnake.getTranslateY()-SIZE);
			occupiedE = checkCollision(scout);
			// Check West
			scout = addBody();
			scout.setTranslateX(headOfSnake.getTranslateX());
			scout.setTranslateY(headOfSnake.getTranslateY()+SIZE);
			occupiedW = checkCollision(scout);
		}
		else {
			// North Direction
			
			// Check North
			scout = addBody();
			scout.setTranslateX(headOfSnake.getTranslateX());
			scout.setTranslateY(headOfSnake.getTranslateY()-SIZE);
			occupiedN = checkCollision(scout);
			// Check East
			scout = addBody();
			scout.setTranslateX(headOfSnake.getTranslateX()+SIZE);
			scout.setTranslateY(headOfSnake.getTranslateY());
			occupiedE = checkCollision(scout);
			// Check West
			scout = addBody();
			scout.setTranslateX(headOfSnake.getTranslateX()-SIZE);
			scout.setTranslateY(headOfSnake.getTranslateY());
			occupiedW = checkCollision(scout);
		}
		Random myRand = new Random();
		int randomInteger = myRand.nextInt(2);
		if(occupiedN) {
			if(occupiedE)
				if(occupiedW)
					return goW(headRotation);
				else
					return goW(headRotation);
			else
				if(occupiedW)
					return goE(headRotation);
				else {
					if (randomInteger == 0) 
						return goE(headRotation);
					else
						return goW(headRotation);
				}		
		}
		else if(occupiedE) {
			if(occupiedN)
				if(occupiedW)
					return goW(headRotation);
				else
					return goW(headRotation);
			else
				if(occupiedW)
					return goN(headRotation);
				else {
					if (randomInteger == 0) 
						return goW(headRotation);
					else
						return goN(headRotation);
				}		
		}
		else if(occupiedW) {
			if(occupiedN)
				if(occupiedE)
					return goE(headRotation);
				else
					return goE(headRotation);
			else
				if(occupiedE)
					return goN(headRotation);
				else {
					if (randomInteger == 0) 
						return goE(headRotation);
					else
						return goN(headRotation);
				}		
		}
		else {
			int randomInt = myRand.nextInt(3);
			if (randomInt == 0) 
				return goN(headRotation);
			else if (randomInt == 1)
				return goE(headRotation);
			else
				return goW(headRotation);
		}
	}
	/**
	 * Set of methods to go from Relative base to Absolute base
	 * @param headRotation
	 * @return
	 */
	public Position goN(double headRotation) {
		if(headRotation == 0)
			return goUP();
		else if(headRotation == -90)
			return goLEFT();
		else if(headRotation == 90)
			return goRIGHT();
		else
			return goDOWN();
	}
	public Position goE(double headRotation) {
		if(headRotation == 0)
			return goRIGHT();
		else if(headRotation == -90)
			return goUP();
		else if(headRotation == 90)
			return goDOWN();
		else
			return goLEFT();
	}
	public Position goW(double headRotation) {
		if(headRotation == 0)
			return goLEFT();
		else if(headRotation == -90)
			return goDOWN();
		else if(headRotation == 90)
			return goUP();
		else
			return goRIGHT();
	}

	public MovementConfig getMoveConfig() {
		return this.moveConfig;
	}
	public void setData(ArrayList<Node> allPartsOfAllSnakes, Group head) {
		this.allPartsOfAllSnakes = allPartsOfAllSnakes;
		this.headOfSnake = head;
	}
	public boolean checkCollision(Node block) {
		boolean collisionSnake = false;
		boolean collisionWalls = false;
		double boardSizePX = GameEngine.GRID_SIZE * GameEngine.ELEMENT_SIZE;
		
		// check snakes
		for (Node node : allPartsOfAllSnakes) {
		      if (block.getBoundsInParent().intersects(node.getBoundsInParent()) ||
		    		  block.getBoundsInParent().contains(node.getBoundsInParent()) ||
		    		  node.getBoundsInParent().contains(block.getBoundsInParent()))  {
		    	  collisionSnake = true;
			      }
		}
		
		// check walls
		collisionWalls = (block.getTranslateX() >= boardSizePX || block.getTranslateX()  < 0 || block.getTranslateY()  >= boardSizePX || block.getTranslateY()  < 0) ? true : false;
		
		return collisionSnake || collisionWalls;
	}
	private Rectangle addBody() {
		Rectangle body = new Rectangle(SIZE-5, SIZE-5, Color.AQUAMARINE);
		body.setArcWidth(SIZE/2);
		body.setArcHeight(SIZE/2);
		body.setStroke(Color.BLACK);
		body.setStrokeWidth(4);
		body.setStrokeLineCap(StrokeLineCap.ROUND);
		return body;
	}
	public Position goUP() {
		if(this.headRotation == 180) {
			this.moveConfig = new MovementConfig(-90, 90, 0, -SIZE, 0);
			return new Position(lastPos.getX(),lastPos.getY()+SIZE);
		}
		this.moveConfig = new MovementConfig(-90, 90, 0, SIZE, 0);
		return new Position(lastPos.getX(),lastPos.getY()-SIZE);	
	}
	public Position goDOWN() {
		if(this.headRotation == 0) {
			this.moveConfig = new MovementConfig(-90, 90, 0, SIZE, 0);
			return new Position(lastPos.getX(),lastPos.getY()-SIZE);
		}
		this.moveConfig = new MovementConfig(-90, 90, 0, -SIZE, 180);
		return new Position(lastPos.getX(),lastPos.getY()+SIZE);
	}
	public Position goLEFT() {
		if(this.headRotation == 90) {
			this.moveConfig = new MovementConfig(0, 180, -SIZE, 0, 0);
			return new Position(lastPos.getX()+SIZE,lastPos.getY());
		}
		this.moveConfig = new MovementConfig(0, 180, SIZE, 0, -90);
		return new Position(lastPos.getX()-SIZE,lastPos.getY());
	}
	public Position goRIGHT() {
		if(this.headRotation == -90) {
			this.moveConfig = new MovementConfig(0, 180, SIZE, 0, 90);
			return new Position(lastPos.getX()-SIZE,lastPos.getY());
		}
		this.moveConfig = new MovementConfig(0, 180, -SIZE, 0, 90);
		return new Position(lastPos.getX()+SIZE,lastPos.getY());
	}
}
