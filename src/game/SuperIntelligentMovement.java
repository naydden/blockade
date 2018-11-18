package game;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

/**
 * Intelligent movement implementation. Uses the head surrounding cells and goes
 * towards the direction with the biggest number of free cells.
 * 
 * @author Boyan Naydenov
 */
public class SuperIntelligentMovement extends AutonomousMovement implements Movement {
	/** Size of each step in px. */
	private static final double SIZE = GameEngine.ELEMENT_SIZE;
	/** Snake that uses this movement. */
	private Snake snake;
	/** Rotation of the head of the snake. */
	private double headRotation;
	/** Position before current movement */
	private Position lastPos;
	/** Contains all the nodes that the snakes create. */
	private ArrayList<Node> allPartsOfAllSnakes;
	/** head node of the snake */
	private Group headOfSnake;

	/** Creates an super-intelligent movement and associates it with a snake. */
	public SuperIntelligentMovement(Snake snake) {
		this.snake = snake;
	}

	/**
	 * Finds the free cell towards which to move. It looks in the three surrounding
	 * directions until it finds an obstacle. Goes towards the direction with the
	 * most mount of free cells. In case of equality, chooses randomly.
	 * 
	 * @param head         The head of the snake, since it is necessary to know its
	 *                     rotation.
	 * @param lastPosition The current position of the head, used to locate the next
	 *                     position.
	 * @return The object containing all the information of the next position.
	 * @throws Exception
	 */
	public MovementConfig nextPosition(Group head, Position lastPosition) throws Exception {
		this.headOfSnake = head;
		super.headOfSnake = head;
		this.allPartsOfAllSnakes = snake.getAllPartsOfAllSnakes();
		super.allPartsOfAllSnakes = this.allPartsOfAllSnakes;
		this.headRotation = head.getRotate();
		super.headRotation = this.headRotation;
		this.lastPos = lastPosition;
		super.lastPos = this.lastPos;

		Rectangle scout = addBody();
		int freeN = 0;
		int freeE = 0;
		int freeW = 0;

		double Ndx, Ndy, Edx, Edy, Wdx, Wdy;
		/**
		 * Definition of the offsets needed to check the surrounding cells depending on
		 * the current orientation.
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

		freeN = checkDir(scout, Ndx, Ndy);
		freeE = checkDir(scout, Edx, Edy);
		freeW = checkDir(scout, Wdx, Wdy);

		Random myRand = new Random();
		int randomInt = myRand.nextInt(2);
		if (freeN > freeE && freeN > freeW)
			return goLocal(headRotation, Direction.UP, Direction.LEFT, Direction.RIGHT, Direction.DOWN);
		else if (freeE > freeN && freeE > freeW)
			return goLocal(headRotation, Direction.RIGHT, Direction.UP, Direction.DOWN, Direction.LEFT);
		else if (freeW > freeN && freeW > freeE)
			return goLocal(headRotation, Direction.LEFT, Direction.DOWN, Direction.UP, Direction.RIGHT);
		else if (freeW == freeN && freeW > freeE) {
			if (randomInt == 0)
				return goLocal(headRotation, Direction.LEFT, Direction.DOWN, Direction.UP, Direction.RIGHT);
			else
				return goLocal(headRotation, Direction.UP, Direction.LEFT, Direction.RIGHT, Direction.DOWN);
		} else if (freeE == freeN && freeE > freeW) {
			if (randomInt == 0)
				return goLocal(headRotation, Direction.RIGHT, Direction.UP, Direction.DOWN, Direction.LEFT);
			else
				return goLocal(headRotation, Direction.UP, Direction.LEFT, Direction.RIGHT, Direction.DOWN);
		} else if (freeE == freeW && freeE > freeN) {
			if (randomInt == 0)
				return goLocal(headRotation, Direction.RIGHT, Direction.UP, Direction.DOWN, Direction.LEFT);
			else
				return goLocal(headRotation, Direction.LEFT, Direction.DOWN, Direction.UP, Direction.RIGHT);
		} else {
			randomInt = myRand.nextInt(3);
			if (randomInt == 0)
				return goLocal(headRotation, Direction.UP, Direction.LEFT, Direction.RIGHT, Direction.DOWN);
			else if (randomInt == 1)
				return goLocal(headRotation, Direction.RIGHT, Direction.UP, Direction.DOWN, Direction.LEFT);
			else
				return goLocal(headRotation, Direction.LEFT, Direction.DOWN, Direction.UP, Direction.RIGHT);
		}
	}

	/**
	 * Finds the number of free cells in each direction.
	 * 
	 * @param scout Scout node to investigate.
	 * @param dx    X offset.
	 * @param dy    Y offset.
	 * @return true if collision, false if not.
	 */
	public int checkDir(Rectangle scout, double dx, double dy) {
		int freeCells = 0;
		scout.setTranslateX(headOfSnake.getTranslateX() + dx);
		scout.setTranslateY(headOfSnake.getTranslateY() + dy);
		int i = 1;
		while (!checkCollision(scout)) {
			freeCells++;
			i++;
			scout.setTranslateX(headOfSnake.getTranslateX() + i * dx);
			scout.setTranslateY(headOfSnake.getTranslateY() + i * dy);
		}
		return freeCells;
	}
}
