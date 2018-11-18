package game;

import javafx.scene.Group;
import javafx.scene.input.KeyCode;

/**
 * Keyboard movement implementation. Allows both ASDW and arrow control. The
 * control is absolute and not relative to he orientation of the head. This way
 * is thought to be more user friendly.
 * 
 * @author Boyan Naydenov
 */
public class ControlledMovement implements Movement {
	/** Size of each step in px */
	private static final double SIZE = GameEngine.ELEMENT_SIZE;
	/** Last pressed key, used to keep going */
	private KeyCode lastValidKeyCode;
	/** Rotation of the head of the snake */
	private double headRotation;
	/** Position before current movement */
	private Position lastPos;

	/** The keys that are going to be used to control the snake */
	private KeyCode keyLEFT;
	private KeyCode keyRIGHT;
	private KeyCode keyUP;
	private KeyCode keyDOWN;

	/** Creates a movement using the chosen keys */
	public ControlledMovement(String KeyBoardChoice) {
		switch (KeyBoardChoice) {
		case "L": {
			this.keyDOWN = KeyCode.S;
			this.keyLEFT = KeyCode.A;
			this.keyRIGHT = KeyCode.D;
			this.keyUP = KeyCode.W;
			this.lastValidKeyCode = Keyboard.getLastKeyCodeL();
			break;
		}
		case "R": {
			this.keyDOWN = KeyCode.DOWN;
			this.keyLEFT = KeyCode.LEFT;
			this.keyRIGHT = KeyCode.RIGHT;
			this.keyUP = KeyCode.UP;
			this.lastValidKeyCode = Keyboard.getLastKeyCodeR();
			break;
		}
		}
		Keyboard.storeLastKeyCode(this.lastValidKeyCode);
	}

	/**
	 * Returns a MovementConfig object containing the computed next position so that
	 * the parent snake can consume it.
	 * 
	 * @param head         The head of the snake, since it is necessary to know its
	 *                     rotation.
	 * @param lastPosition The current position of the head, used to locate the next
	 *                     position.
	 * @return The object containing all the information of the next position.
	 * @throws Exception
	 */
	public MovementConfig nextPosition(Group head, Position lastPosition) throws Exception {

		this.headRotation = head.getRotate();
		this.lastPos = lastPosition;
		return KeyBoardControl(this.keyLEFT, this.keyRIGHT, this.keyUP, this.keyDOWN);
	}

	/**
	 * Provides abstraction between keys and directions.
	 * 
	 * @param L Designed key to go left.
	 * @param R Designed key to go right.
	 * @param U Designed key to go up.
	 * @param D Designed key to go down.
	 * @return The object containing all the information of the next position.
	 * @throws Exception
	 */
	private MovementConfig KeyBoardControl(KeyCode L, KeyCode R, KeyCode U, KeyCode D) throws Exception {
		KeyCode last;
		/** If no key is pressed the snakes goes on */
		if (L.equals(KeyCode.A))
			last = Keyboard.getLastKeyCodeL();
		else
			last = Keyboard.getLastKeyCodeR();

		if (last.equals(L))
			return go(Direction.LEFT, L, R);
		else if (last.equals(U))
			return go(Direction.UP, U, D);
		else if (last.equals(R))
			return go(Direction.RIGHT, R, L);
		else if (last.equals(D))
			return go(Direction.DOWN, D, U);
		else {
			if (this.lastValidKeyCode.equals(L))
				return go(Direction.LEFT, L, R);
			else if (last.equals(U))
				return go(Direction.UP, U, D);
			else if (last.equals(R))
				return go(Direction.RIGHT, R, L);
			else if (last.equals(D))
				return go(Direction.DOWN, D, U);
			else {
				return go(Direction.UP, U, D);
			}
		}
	}

	/**
	 * Generates the nextPosition based on the given direction. The next position is
	 * expressed as a MovementConfig object.
	 * 
	 * The method uses current head rotation of the snake in order to decide if the
	 * snake tries to go from where it comes. If that is the case the method makes
	 * the snake continue in the same direction.
	 * 
	 * @param dir     Desired direction
	 * @param dirKey1 Key for desired direction (for instance W)
	 * @param dirKey2 Opposite key of dirKey1 (for instance A)
	 * @return nextPosition wrapped in MovementConfig object
	 * @throws Exception
	 */
	public MovementConfig go(Direction dir, KeyCode dirKey1, KeyCode dirKey2) throws Exception {
		switch (dir) {
		case UP: {
			this.lastValidKeyCode = dirKey1;
			/** Bit to avoid snake going to where it comes from. */
			if (this.headRotation == 180) {
				this.lastValidKeyCode = dirKey2;
				Keyboard.storeLastKeyCode(dirKey2);
				return new MovementConfig(-90, 90, 0, -SIZE, 0, new Position(lastPos.getX(), lastPos.getY() + SIZE));
			}
			return new MovementConfig(-90, 90, 0, SIZE, 0, new Position(lastPos.getX(), lastPos.getY() - SIZE));
		}
		case DOWN: {
			this.lastValidKeyCode = dirKey1;
			if (this.headRotation == 0) {
				this.lastValidKeyCode = dirKey2;
				Keyboard.storeLastKeyCode(dirKey2);
				return new MovementConfig(-90, 90, 0, SIZE, 0, new Position(lastPos.getX(), lastPos.getY() - SIZE));
			}
			return new MovementConfig(-90, 90, 0, -SIZE, 180, new Position(lastPos.getX(), lastPos.getY() + SIZE));
		}
		case LEFT: {
			this.lastValidKeyCode = dirKey1;
			if (this.headRotation == 90) {
				this.lastValidKeyCode = dirKey2;
				Keyboard.storeLastKeyCode(dirKey2);
				return new MovementConfig(0, 180, -SIZE, 0, 0, new Position(lastPos.getX() + SIZE, lastPos.getY()));
			}
			return new MovementConfig(0, 180, SIZE, 0, -90, new Position(lastPos.getX() - SIZE, lastPos.getY()));
		}
		case RIGHT: {
			this.lastValidKeyCode = dirKey1;
			if (this.headRotation == -90) {
				this.lastValidKeyCode = dirKey2;
				Keyboard.storeLastKeyCode(dirKey2);
				return new MovementConfig(0, 180, SIZE, 0, 90, new Position(lastPos.getX() - SIZE, lastPos.getY()));
			}
			return new MovementConfig(0, 180, -SIZE, 0, 90, new Position(lastPos.getX() + SIZE, lastPos.getY()));
		}
		default:
			throw new Exception("Specified direction is not valid!");
		}
	}
}
