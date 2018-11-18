package game;

import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.util.ArrayList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;

/**
 * Snake class able to know if it has crashed.
 * 
 * @author Boyan Naydenov
 *
 */
public class Snake {
	/** Size of each step */
	private static final double SIZE = GameEngine.ELEMENT_SIZE;
	/** Size of the board */
	private static final double BOARDSIZEPX = GameEngine.GRID_SIZE * GameEngine.ELEMENT_SIZE;
	/** List with all of the snake's elements */
	private ArrayList<Node> allPartsOfSnake;
	/** List with all the snakes elements */
	private ArrayList<Node> allPartsOfAllSnakes;
	/** Coordinates */
	private double x, y;
	/** Colour of the snake */
	private Color color;
	/** Movement used by the snake. It can be changed on runtime. */
	private Movement mov;
	/** Indicator of the status of the snake. True if crashed **/
	private boolean crashed;
	/** Head of the snake */
	private Group head;
	/** Next position of the snake. Protected since it is used by GameMain */
	protected MovementConfig nextPosition;
	/** name of the snake. */
	String snakeName;

	/**
	 * Creates a snake and positions it in the game arena.
	 * 
	 * @param snakeName
	 * @param pos
	 * @param color
	 * @param direction if 0, top-left corner quadrant, if 1, bottom-right.
	 */
	public Snake(String snakeName, Position pos, Color color, int direction) {
		this.snakeName = snakeName;
		this.color = color;
		this.x = pos.getX();
		this.y = pos.getY();
		this.allPartsOfSnake = new ArrayList<Node>();
		this.allPartsOfAllSnakes = new ArrayList<Node>();
		this.crashed = false;

		if (direction == 0) {
			Double[] snakeCoord = new Double[] { -3.0, 1.5, -3.0, SIZE - 4.5, -SIZE * 0.9, SIZE / 2 };
			initSnake(snakeCoord, -SIZE, 90, 1);
			Keyboard.storeLastKeyCode(KeyCode.D);
		} else {
			Double[] snakeCoord = new Double[] { 0.0, 1.5, 0.0, SIZE - 3.5, SIZE * 0.9, SIZE / 2 };
			initSnake(snakeCoord, SIZE, -90, 2);
			Keyboard.storeLastKeyCode(KeyCode.RIGHT);
		}
	}

	/**
	 * Generates the initial snake, with one triangle as a tail, one rectangle as a
	 * body and one curve as a head.
	 * 
	 * @param tailCoor   Definition coordinates of the tail
	 * @param transX     X Position of the body.
	 * @param rotateHead Rotation of the head.
	 * @param transTail  X Translate position of the tail.
	 */
	public void initSnake(Double[] tailCoor, double transX, double rotateHead, double transTail) {
		/** tail of snake */
		Polygon tail = new Polygon();
		tail.getPoints().addAll(tailCoor);
		tail.setFill(Color.BLACK);
		translatePosition(tail, transTail * transX, 0);
		allPartsOfSnake.add(tail);

		/** first element of snake body */
		Rectangle fBody = addBody();
		translatePosition(fBody, transX, 0);
		allPartsOfSnake.add(fBody);

		/** Head of the snake */
		head = new Group();
		Path headShape = new Path();

		MoveTo moveTo = new MoveTo();
		moveTo.setX(0);
		moveTo.setY(SIZE - 5);
		HLineTo hLineTo = new HLineTo();
		hLineTo.setX(SIZE - 5);

		QuadCurveTo quadTo = new QuadCurveTo();
		quadTo.setControlX(SIZE - 5);
		quadTo.setControlY(0.1);
		quadTo.setX((SIZE - 5) / 2);
		quadTo.setY(1);

		QuadCurveTo quadTo2 = new QuadCurveTo();
		quadTo2.setControlX(0);
		quadTo2.setControlY(0.9);
		quadTo2.setX(0);
		quadTo2.setY(SIZE - 5);

		headShape.getElements().add(moveTo);
		headShape.getElements().add(hLineTo);
		headShape.getElements().add(quadTo);
		headShape.getElements().add(quadTo2);

		headShape.setFill(Color.RED);
		headShape.setStroke(Color.BLACK);
		headShape.setStrokeWidth(3);
		headShape.setStrokeLineCap(StrokeLineCap.ROUND);
		head.setRotate(rotateHead);
		head.getChildren().add(headShape);

		translatePosition(head, 0, 0);
		allPartsOfSnake.add(head);
	}

	/**
	 * Moves the snake's head by calling the assigned movement definition.
	 * 
	 * @param allPartsOfAllSnakes All the nodes in order to check for a crash.
	 * @throws Exception If some problem occurs when getting next position, it will
	 *                   throw and stop the clock.
	 */
	public void move(ArrayList<Node> allPartsOfAllSnakes) throws Exception {
		this.allPartsOfAllSnakes = allPartsOfAllSnakes;
		try {
			this.nextPosition = mov.nextPosition(head, new Position(x, y));
		} catch (Exception e) {
			System.out.println(
					"Movement for snake " + this.snakeName + " has not been set! " + "Assigning random movement.");
			this.mov = new RandomMovement();
			this.nextPosition = mov.nextPosition(head, new Position(x, y));
		}

		nodeMove(this.nextPosition);
	}

	/**
	 * Moves the snake's head by giving the next position already. When in CLIENT
	 * role, this method is used instead of the previous one for the SERVER snake.
	 * When in SERVER role, this method is used instead of the previous one for the
	 * CLIENT snake.
	 * 
	 * @param allPartsOfAllSnakes All the nodes in order to check for a crash.
	 * @param nextPos             Next position that the snake has been asked to
	 *                            take.
	 * @throws Exception
	 */
	public void controlledMove(ArrayList<Node> allPartsOfAllSnakes, MovementConfig nextPos) {
		this.allPartsOfAllSnakes = allPartsOfAllSnakes;
		nodeMove(nextPos);
	}

	/**
	 * Consumes MovementConfig object moving the head node and creating a new body
	 * rectangle.
	 * 
	 * @param mov Object containing all the parameters to rotate/translate head, and
	 *            add body node.
	 */
	private void nodeMove(MovementConfig mov) {

		this.x = mov.position.getX();
		this.y = mov.position.getY();
		Rectangle body = addBody();

		double head_x = head.getTranslateX();
		double head_y = head.getTranslateY();
		double headRotation = head.getRotate();

		if (headRotation == mov.Rot1 || headRotation == mov.Rot2) {
			translatePosition(head, 0, 0);
			head.setRotate(mov.setRot);
			body.setTranslateX(head_x);
			body.setTranslateY(head_y);
		} else {
			translatePosition(body, mov.bodyX, mov.bodyY);
			translatePosition(head, 0, 0);
		}
		allPartsOfSnake.add(body);

		if (isCollision(head)) {
			this.crashed = true;
		}
	}

	/**
	 * Utility method to create a typical snake rectangle node
	 * 
	 * @return rectangle to build the snake.
	 */
	private Rectangle addBody() {
		Rectangle body = new Rectangle(SIZE - 5, SIZE - 5, color);
		body.setArcWidth(SIZE / 2);
		body.setArcHeight(SIZE / 2);
		body.setStroke(Color.BLACK);
		body.setStrokeWidth(3);
		body.setStrokeLineCap(StrokeLineCap.ROUND);
		return body;
	}

	/**
	 * Simply translates the position of the given node.
	 * 
	 * @param nod
	 * @param plusX
	 * @param plusY
	 */
	private void translatePosition(Node nod, double plusX, double plusY) {
		nod.setTranslateX(x + plusX);
		nod.setTranslateY(y + plusY);
	}

	/**
	 * Checks if block in particular crashes with some snake or with any wall.
	 * 
	 * @param block
	 * @return true if collided, false if not.
	 */
	private boolean isCollision(Node block) {
		/**
		 * Checks collision with self and with wall.
		 */
		boolean collisionWalls = (x >= BOARDSIZEPX || x < 0 || y >= BOARDSIZEPX || y < 0) ? true : false;
		boolean collisionSnake = checkBounds(block);
		return collisionWalls || collisionSnake;
	}

	/**
	 * Tells if head node intersects with any other node of any snake.
	 * 
	 * @param head
	 * @return true if collided.
	 */
	private boolean checkBounds(Node head) {
		for (Node node : allPartsOfAllSnakes) {
			if (node != head)
				if (head.getBoundsInParent().intersects(node.getBoundsInParent()))
					return true;
		}
		return false;
	}

	/**
	 * 
	 * Getters and Setters
	 * 
	 */
	public ArrayList<Node> getAllParts() {
		return allPartsOfSnake;
	}

	public ArrayList<Node> getAllPartsOfAllSnakes() {
		return this.allPartsOfAllSnakes;
	}

	public Group getHead() {
		return this.head;
	}

	public void setMovement(Movement mov) {
		this.mov = mov;
	}

	public boolean isCrashed() {
		return this.crashed;
	}

}
