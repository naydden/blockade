package game;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;

public class AutonomousMovement {
	/** Size of each step in px. */
	private static final double SIZE = GameEngine.ELEMENT_SIZE;
	/** Rotation of the head of the snake. */
	protected double headRotation;
	/** Position before current movement */
	protected Position lastPos;
	/** Contains all the nodes that the snakes create. */
	protected ArrayList<Node> allPartsOfAllSnakes;
	/** head node of the snake */
	protected Group headOfSnake;

	/**
	 * Utility method to create a typical snake rectangle node
	 * 
	 * @return same rectangle as the one used to build the snake.
	 */
	public Rectangle addBody() {
		Rectangle body = new Rectangle(SIZE - 5, SIZE - 5, Color.AQUAMARINE);
		body.setArcWidth(SIZE / 2);
		body.setArcHeight(SIZE / 2);
		body.setStroke(Color.BLACK);
		body.setStrokeWidth(4);
		body.setStrokeLineCap(StrokeLineCap.ROUND);
		return body;
	}

	/**
	 * Checks if block node collides with something.
	 * 
	 * @param block Scout node.
	 * @return true if collision, false if not.
	 */
	public boolean checkCollision(Node block) {
		boolean collisionSnake = false;
		boolean collisionWalls = false;
		double boardSizePX = GameEngine.GRID_SIZE * GameEngine.ELEMENT_SIZE;

		// check snakes
		for (Node node : allPartsOfAllSnakes) {
			if (block.getBoundsInParent().intersects(node.getBoundsInParent())
					|| block.getBoundsInParent().contains(node.getBoundsInParent())
					|| node.getBoundsInParent().contains(block.getBoundsInParent())) {
				collisionSnake = true;
			}
		}

		// check walls
		collisionWalls = (block.getTranslateX() >= boardSizePX || block.getTranslateX() < 0
				|| block.getTranslateY() >= boardSizePX || block.getTranslateY() < 0) ? true : false;

		return collisionSnake || collisionWalls;
	}

	/**
	 * Goes from Relative base to Absolute base depending on current direction.
	 * 
	 * @param headRotation
	 * @return nextPosition wrapped in MovementConfig object
	 * @throws Exception
	 */
	public MovementConfig goLocal(double headRotation, Direction dir1, Direction dir2, Direction dir3, Direction dir4)
			throws Exception {
		if (headRotation == 0)
			return go(dir1);
		else if (headRotation == -90)
			return go(dir2);
		else if (headRotation == 90)
			return go(dir3);
		else
			return go(dir4);
	}

	/**
	 * Generates the nextPosition based on the given direction. The next position is
	 * expressed as a MovementConfig object.
	 * 
	 * The method uses current head rotation of the snake in order to decide if the
	 * snake tries to go from where it comes. If that is the case the method makes
	 * the snake continue in the same direction.
	 * 
	 * @param dir Desired direction
	 * @return nextPosition wrapped in MovementConfig object
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
