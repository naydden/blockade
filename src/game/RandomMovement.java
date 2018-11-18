package game;

import java.util.Random;

import javafx.scene.Group;

/**
 * Random movement implementation. Just chooses between the possible directions randomly.
 * 
 * @author Boyan Naydenov
 */
public class RandomMovement implements Movement {
	/** Size of each step in px */
	private static final double SIZE = GameEngine.ELEMENT_SIZE;
	/** Rotation of the head of the snake */
	private double headRotation;
	/** Position before current movement */
	private Position lastPos;
	/** Random number used to choose movement */
	private Random randomChoice;

	public RandomMovement() {
		this.randomChoice = new Random();
	}
	/**
	 * Chooses the next direction randomly
	 * 
	 * @param head	head of the snake
	 * @param lastPosition	current position
	 * @return nextPosition wrapped in MovementConfig object
	 * @throws Exception
	 */
	public MovementConfig nextPosition(Group head, Position lastPosition) throws Exception {
		this.headRotation = head.getRotate();
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
	/**
	 * Generates the nextPosition based on the given direction.
	 * The next position is expressed as a MovementConfig object.
	 * 
	 * The method uses current head rotation of the snake in order to
	 * decide if the snake tries to go from where it comes. If that is
	 * the case the method makes the snake continue in the same direction.
	 * 
	 * @param dir	Desired direction
	 * @return		nextPosition wrapped in MovementConfig object
	 * @throws Exception
	 */
	public MovementConfig go(Direction dir) throws Exception {
		switch (dir) {
		case UP: {
			/** Bit to avoid snake going to where it comes from. */
			if (this.headRotation == 180)
				return new MovementConfig(-90, 90, 0, -SIZE, 0, new Position(lastPos.getX(), lastPos.getY() + SIZE));
			else
				return new MovementConfig(-90, 90, 0, SIZE, 0, new Position(lastPos.getX(), lastPos.getY() - SIZE));
		}
		case DOWN: {
			if (this.headRotation == 0)
				return new MovementConfig(-90, 90, 0, SIZE, 0, new Position(lastPos.getX(), lastPos.getY() - SIZE));
			else
				return new MovementConfig(-90, 90, 0, -SIZE, 180, new Position(lastPos.getX(), lastPos.getY() + SIZE));
		}
		case LEFT: {
			if (this.headRotation == 90)
				return new MovementConfig(0, 180, -SIZE, 0, 0, new Position(lastPos.getX() + SIZE, lastPos.getY()));
			else
				return new MovementConfig(0, 180, SIZE, 0, -90, new Position(lastPos.getX() - SIZE, lastPos.getY()));
		}
		case RIGHT: {
			if (this.headRotation == -90)
				return new MovementConfig(0, 180, SIZE, 0, 90, new Position(lastPos.getX() - SIZE, lastPos.getY()));
			else
				return new MovementConfig(0, 180, -SIZE, 0, 90, new Position(lastPos.getX() + SIZE, lastPos.getY()));
		}
		default:
			throw new Exception("Specified direction is not valid!");
		}
	}
}
