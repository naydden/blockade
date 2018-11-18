package game;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

/**
 * Intelligent movement implementation. Uses the head surrounding cells
 * to decide randomly just between the free cells, if any.
 * 
 * @author Boyan Naydenov
 */

public class IntelligentMovement extends AutonomousMovement implements Movement {
	/** Size of each step in px. */
	private static final double SIZE = GameEngine.ELEMENT_SIZE;
	/** Snake that uses this movement. */
	private Snake snake;
	/** Rotation of the head of the snake. */
	private double headRotation;
	/** Position before current movement*/
	private Position lastPos;
	/** Contains all the nodes that the snakes create.*/
	private ArrayList<Node> allPartsOfAllSnakes;
	/** head node of the snake */
	private Group headOfSnake;

	/** Creates an intelligent movement and associates it with a snake. */
	public IntelligentMovement(Snake snake) {
		this.snake = snake;
	}

	/**
	 * 	Finds the free cell towards which to move. If 2, chooses randomly.
	 *  If any cells is available chooses randomly as well.
	 * 
	 * @param head			The head of the snake, since it is necessary to know its rotation. 
	 * @param lastPosition	The current position of the head, used to locate the next position.
	 * @return 				The object containing all the information of the next position.
	 * @throws	Exception
	 */
	public MovementConfig nextPosition(Group head, Position lastPosition) throws Exception {
		/** Gets the current context (current head and all the other nodes) */
		this.headOfSnake = head;
		super.headOfSnake = head;
		this.allPartsOfAllSnakes = snake.getAllPartsOfAllSnakes();
		super.allPartsOfAllSnakes = this.allPartsOfAllSnakes;
		this.headRotation = head.getRotate();
		super.headRotation = this.headRotation;
		this.lastPos = lastPosition;
		super.lastPos = this.lastPos;

		Rectangle scout = addBody();
		boolean occupiedN = false;
		boolean occupiedE = false;
		boolean occupiedW = false;

		double Ndx, Ndy, Edx, Edy, Wdx, Wdy;
		/** Definition of the offsets needed to check the surrounding cells
		 * depending on the current orientation.
		 */
		if (headRotation == 0) {
			// North Direction
			Ndx = 0;
			Ndy = -SIZE;
			Edx = SIZE;
			Edy = 0;
			Wdx = -SIZE;
			Wdy = 0;
		} else if (headRotation == 180) {
			// South Direction
			Ndx = 0;
			Ndy = SIZE;
			Edx = -SIZE;
			Edy = 0;
			Wdx = SIZE;
			Wdy = 0;
		} else if (headRotation == 90) {
			// East Direction
			Ndx = SIZE;
			Ndy = 0;
			Edx = 0;
			Edy = SIZE;
			Wdx = 0;
			Wdy = -SIZE;
		} else if (headRotation == -90) {
			// West Direction
			Ndx = -SIZE;
			Ndy = 0;
			Edx = 0;
			Edy = -SIZE;
			Wdx = 0;
			Wdy = SIZE;
		} else {
			// North Direction
			Ndx = 0;
			Ndy = -SIZE;
			Edx = SIZE;
			Edy = 0;
			Wdx = -SIZE;
			Wdy = 0;
		}
		/** Checks if the surrounding cells are occupied */
		occupiedN = checkDir(scout, Ndx, Ndy);
		occupiedE = checkDir(scout, Edx, Edy);
		occupiedW = checkDir(scout, Wdx, Wdy);

		Random myRand = new Random();
		int randomInteger = myRand.nextInt(2);

		if (occupiedN) {
			if (occupiedE)
				if (occupiedW)
					return goLocal(headRotation, Direction.LEFT, Direction.DOWN, Direction.UP, Direction.RIGHT);
				else
					return goLocal(headRotation, Direction.LEFT, Direction.DOWN, Direction.UP, Direction.RIGHT);
			else if (occupiedW)
				return goLocal(headRotation, Direction.RIGHT, Direction.UP, Direction.DOWN, Direction.LEFT);
			else {
				if (randomInteger == 0)
					return goLocal(headRotation, Direction.RIGHT, Direction.UP, Direction.DOWN, Direction.LEFT);
				else
					return goLocal(headRotation, Direction.LEFT, Direction.DOWN, Direction.UP, Direction.RIGHT);
			}
		} else if (occupiedE) {
			if (occupiedN)
				if (occupiedW)
					return goLocal(headRotation, Direction.LEFT, Direction.DOWN, Direction.UP, Direction.RIGHT);
				else
					return goLocal(headRotation, Direction.LEFT, Direction.DOWN, Direction.UP, Direction.RIGHT);
			else if (occupiedW)
				return goLocal(headRotation, Direction.UP, Direction.LEFT, Direction.RIGHT, Direction.DOWN);
			else {
				if (randomInteger == 0)
					return goLocal(headRotation, Direction.LEFT, Direction.DOWN, Direction.UP, Direction.RIGHT);
				else
					return goLocal(headRotation, Direction.UP, Direction.LEFT, Direction.RIGHT, Direction.DOWN);
			}
		} else if (occupiedW) {
			if (occupiedN)
				if (occupiedE)
					return goLocal(headRotation, Direction.RIGHT, Direction.UP, Direction.DOWN, Direction.LEFT);
				else
					return goLocal(headRotation, Direction.RIGHT, Direction.UP, Direction.DOWN, Direction.LEFT);
			else if (occupiedE)
				return goLocal(headRotation, Direction.UP, Direction.LEFT, Direction.RIGHT, Direction.DOWN);
			else {
				if (randomInteger == 0)
					return goLocal(headRotation, Direction.RIGHT, Direction.UP, Direction.DOWN, Direction.LEFT);
				else
					return goLocal(headRotation, Direction.UP, Direction.LEFT, Direction.RIGHT, Direction.DOWN);
			}
		} else {
			int randomInt = myRand.nextInt(3);
			if (randomInt == 0)
				return goLocal(headRotation, Direction.UP, Direction.LEFT, Direction.RIGHT, Direction.DOWN);
			else if (randomInt == 1)
				return goLocal(headRotation, Direction.RIGHT, Direction.UP, Direction.DOWN, Direction.LEFT);
			else
				return goLocal(headRotation, Direction.LEFT, Direction.DOWN, Direction.UP, Direction.RIGHT);
		}
	}
	/**
	 * Translates scout node and obtains if it collides with some other part of any snake or wall.
	 * @param scout		Scout node to investigate.
	 * @param dx		X offset.
	 * @param dy		Y offset.
	 * @return	true if collision, false if not.
	 */
	public  boolean checkDir(Rectangle scout, double dx, double dy) {
		scout.setTranslateX(headOfSnake.getTranslateX() + dx);
		scout.setTranslateY(headOfSnake.getTranslateY() + dy);
		return checkCollision(scout);
	}

}
