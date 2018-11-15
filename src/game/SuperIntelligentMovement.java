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
	
	private MovementConfig moveConfig;
	private double headRotation; 
	private Position lastPos;
	ArrayList<Node> allPartsOfAllSnakes;
	Group headOfSnake;

	public Position nextPosition (double headRotation, Position lastPosition) {
		this.headRotation = headRotation;
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
			return goN(headRotation);
		else if(freeE > freeN && freeE>freeW)
			return goE(headRotation);
		else if(freeW > freeN && freeW>freeE)
			return goW(headRotation);
		else if(freeW == freeN && freeW>freeE) {
			if (randomInt == 0) 
				return goW(headRotation);
			else
				return goN(headRotation);
		}
		else if(freeE == freeN && freeE>freeW) {			
			if (randomInt == 0) 
				return goE(headRotation);
			else
				return goN(headRotation);
		}
		else if(freeE == freeW && freeE>freeN) {
			if (randomInt == 0) 
				return goE(headRotation);
			else
				return goW(headRotation);
		}
		else {
			randomInt = myRand.nextInt(3);
			if (randomInt == 0) 
				return goN(headRotation);
			else if (randomInt == 1)
				return goE(headRotation);
			else
				return goW(headRotation);
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
