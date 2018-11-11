package game;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.Group;
import javafx.scene.Node;

public class SuperIntelligentMovement implements Movement {
//	hacerlo con densidad de posiciones ocupadas en N,NE,E,SE,S,SW,W,NW
private static final double SIZE = GameEngine.ELEMENT_SIZE;
	
	private MovementConfig moveConfig;
	private double headRotation; 
	private Position lastPos;
	private ArrayList<Node> allPartsOfAllSnakes;
	private Group headOfSnake;

	public Position nextPosition (double headRotation, Position lastPosition) {
		this.headRotation = headRotation;
		this.lastPos = lastPosition;
		
		Random myRand = new Random();     
		int randomInteger = myRand.nextInt(4);

		if (randomInteger == 0) 
			return goUP();
		else if (randomInteger == 1)
			return goDOWN();
		else if (randomInteger == 2)
			return goLEFT();
		else if (randomInteger == 3)
			return goRIGHT();
		else
			return goUP();
	}
	public MovementConfig getMoveConfig() {
		return this.moveConfig;
	}
	public void setData(ArrayList<Node> allPartsOfAllSnakes, Group head) {
		this.allPartsOfAllSnakes = allPartsOfAllSnakes;
		this.headOfSnake = head;
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
