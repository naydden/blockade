package game;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;

public class RandomMovement implements Movement {
	
	private static final double SIZE = GameEngine.ELEMENT_SIZE;
	
	private double headRotation; 
	private Position lastPos;
	private Random randomChoice;
	
	public RandomMovement() {
		this.randomChoice = new Random();  
	}

	public MovementConfig nextPosition (double headRotation, Position lastPosition) throws Exception {
		this.headRotation = headRotation;
		this.lastPos = lastPosition;
		int randomInteger = this.randomChoice.nextInt(4);

		if (randomInteger == 0) 
			return go(Direction.UP);
		else if (randomInteger == 1)
			return go(Direction.DOWN);
		else if (randomInteger == 2)
			return go(Direction.LEFT);
		else if (randomInteger == 3)
			return go(Direction.RIGHT);
		else
			return go(Direction.UP);
		
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
