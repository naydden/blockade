package game;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;

public class SuperIntelligentMovement implements Movement {
	private static final double SIZE = GameEngine.ELEMENT_SIZE;
	private Snake snake;
	private double headRotation; 
	private Position lastPos;
	ArrayList<Node> allPartsOfAllSnakes;
	Group headOfSnake;

	public SuperIntelligentMovement(Snake snake) {
		this.snake = snake;
	}
	
	public MovementConfig nextPosition (Group head, Position lastPosition) throws Exception {
		this.headOfSnake = head;
		this.allPartsOfAllSnakes = snake.getAllPartsOfAllSnakes();
		this.headRotation = head.getRotate();
		this.lastPos = lastPosition;
		
		Rectangle scout = addBody();
		int freeN = 0;
		int freeE = 0;
		int freeW = 0;
		
		double Ndx,Ndy,Edx,Edy,Wdx,Wdy;
		
		if(headRotation == 0) {
			// North Direction
			Ndx = 0; Ndy = -SIZE; Edx = SIZE; Edy = 0; Wdx = -SIZE;	Wdy = 0;		
		}
		else if(headRotation == 180) {
			// South Direction
			Ndx = 0; Ndy = SIZE; Edx = -SIZE; Edy = 0; Wdx = SIZE;	Wdy = 0;
		}
		else if(headRotation == 90) {
			// East Direction
			Ndx = SIZE; Ndy = 0; Edx = 0; Edy = SIZE; Wdx = 0; Wdy = -SIZE;
		}
		else if(headRotation == -90) {
			// West Direction
			Ndx = -SIZE; Ndy = 0; Edx = 0; Edy = -SIZE; Wdx = 0; Wdy = SIZE;
		}
		else {
			// North Direction
			Ndx = 0; Ndy = -SIZE; Edx = SIZE; Edy = 0; Wdx = -SIZE;	Wdy = 0;
		}
		

		freeN = checkDir(scout, Ndx, Ndy);
		freeE = checkDir(scout, Edx, Edy);
		freeW = checkDir(scout, Wdx, Wdy);
		
		Random myRand = new Random();
		int randomInt = myRand.nextInt(2);
		if(freeN > freeE && freeN>freeW)
			return goLocal(headRotation, Direction.UP, Direction.LEFT, 
					Direction.RIGHT, Direction.DOWN);
		else if(freeE > freeN && freeE>freeW)
			return goLocal(headRotation, Direction.RIGHT, Direction.UP,
					Direction.DOWN, Direction.LEFT);
		else if(freeW > freeN && freeW>freeE)
			return goLocal(headRotation, Direction.LEFT, Direction.DOWN, 
					Direction.UP, Direction.RIGHT);
		else if(freeW == freeN && freeW>freeE) {
			if (randomInt == 0) 
				return goLocal(headRotation, Direction.LEFT, Direction.DOWN, 
						Direction.UP, Direction.RIGHT);
			else
				return goLocal(headRotation, Direction.UP, Direction.LEFT,
						Direction.RIGHT, Direction.DOWN);
		}
		else if(freeE == freeN && freeE>freeW) {			
			if (randomInt == 0) 
				return goLocal(headRotation, Direction.RIGHT, Direction.UP,
						Direction.DOWN, Direction.LEFT);
			else
				return goLocal(headRotation, Direction.UP, Direction.LEFT,
						Direction.RIGHT, Direction.DOWN);
		}
		else if(freeE == freeW && freeE>freeN) {
			if (randomInt == 0) 
				return goLocal(headRotation, Direction.RIGHT, Direction.UP,
						Direction.DOWN, Direction.LEFT);
			else
				return goLocal(headRotation, Direction.LEFT, Direction.DOWN,
						Direction.UP, Direction.RIGHT);
		}
		else {
			randomInt = myRand.nextInt(3);
			if (randomInt == 0) 
				return goLocal(headRotation, Direction.UP, Direction.LEFT,
						Direction.RIGHT, Direction.DOWN);
			else if (randomInt == 1)
				return goLocal(headRotation, Direction.RIGHT, Direction.UP,
						Direction.DOWN, Direction.LEFT);
			else
				return goLocal(headRotation, Direction.LEFT, Direction.DOWN,
						Direction.UP, Direction.RIGHT);
		}
	}
	public int checkDir(Rectangle scout, double dx, double dy) {
		int freeCells = 0;
		scout.setTranslateX(headOfSnake.getTranslateX()+dx);
		scout.setTranslateY(headOfSnake.getTranslateY()+dy);
		int i = 1;
		while(!checkCollision(scout)) {
			freeCells++;
			i++;
			scout.setTranslateX(headOfSnake.getTranslateX()+i*dx);
			scout.setTranslateY(headOfSnake.getTranslateY()+i*dy);	
		}
		return freeCells;
	}
	/**
	 * Set of methods to go from Relative base to Absolute base
	 * @param headRotation
	 * @return
	 * @throws Exception 
	 */
	public MovementConfig goLocal(double headRotation, Direction dir1, 
			Direction dir2, Direction dir3, Direction dir4) throws Exception {
		if(headRotation == 0)
			return go(dir1);
		else if(headRotation == -90)
			return go(dir2);
		else if(headRotation == 90)
			return go(dir3);
		else
			return go(dir4);
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
		collisionWalls = (block.getTranslateX() >= boardSizePX || block.getTranslateX()  < 0 
				|| block.getTranslateY()  >= boardSizePX || block.getTranslateY()  < 0) ? true : false;
		
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
	public MovementConfig go(Direction dir) throws Exception {
		switch(dir) {
			case UP:{
				if(this.headRotation == 180)
					return new MovementConfig(-90, 90, 0, -SIZE, 0, 
							new Position(lastPos.getX(),lastPos.getY()+SIZE));
				else
					return new MovementConfig(-90, 90, 0, SIZE, 0,
							new Position(lastPos.getX(),lastPos.getY()-SIZE));
			}
			case DOWN: {
				if(this.headRotation == 0)
					return new MovementConfig(-90, 90, 0, SIZE, 0, 
							new Position(lastPos.getX(),lastPos.getY()-SIZE));
				else
					return new MovementConfig(-90, 90, 0, -SIZE, 180,
							new Position(lastPos.getX(),lastPos.getY()+SIZE));
			}
			case LEFT: {
				if(this.headRotation == 90)
					return new MovementConfig(0, 180, -SIZE, 0, 0,
							new Position(lastPos.getX()+SIZE,lastPos.getY()));
				else
					return new MovementConfig(0, 180, SIZE, 0, -90,
							new Position(lastPos.getX()-SIZE,lastPos.getY()));
			}
			case RIGHT: {
				if(this.headRotation == -90)
					return new MovementConfig(0, 180, SIZE, 0, 90,
							new Position(lastPos.getX()-SIZE,lastPos.getY()));
				else
					return new MovementConfig(0, 180, -SIZE, 0, 90,
							new Position(lastPos.getX()+SIZE,lastPos.getY()));	
			}
			default:
				throw new Exception("Specified direction is not valid!");
		}
	}
}
