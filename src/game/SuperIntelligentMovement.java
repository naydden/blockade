package game;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

public class SuperIntelligentMovement extends AutonomousMovement implements Movement {
	private static final double SIZE = GameEngine.ELEMENT_SIZE;
	private Snake snake;
	private double headRotation;
	private Position lastPos;
	ArrayList<Node> allPartsOfAllSnakes;
	Group headOfSnake;

	public SuperIntelligentMovement(Snake snake) {
		this.snake = snake;
	}

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
	 * Translates scout node and obtains if it collides with some other part of any snake or wall.
	 * @param scout		Scout node to investigate.
	 * @param dx		X offset.
	 * @param dy		Y offset.
	 * @return	true if collision, false if not.
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
