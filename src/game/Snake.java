package game;


import javafx.scene.paint.Color;
//import javafx.scene.shape.Rectangle;
//import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;

public class Snake { // square eggs fill space much better
	private static final double SIZE = GameEngine.ELEMENT_SIZE; 
	private static final double STEP = 12;
	private ArrayList<Node> allPartsOfSnake;
	private ArrayList<Node> allPartsOfAllSnakes;
	private double x, y;
	private Color color;
	private KeyCode lastValidKeyCode = KeyCode.P;
	private String keyBoardChoice;
	Group head;

	public Snake(Position pos, Color color, String keyBoardChoice )
	{
		this.color = color;
		this.x = pos.getX();
		this.y = pos.getY();
		this.allPartsOfSnake = new ArrayList<Node>();
		this.allPartsOfAllSnakes = new ArrayList<Node>();
		this.keyBoardChoice = keyBoardChoice;

		
//		Tail of the snake. Only one and with fixed position
		Polygon tail = new Polygon();
		tail.getPoints().addAll(new Double[]{
	            0.0, 2*(SIZE),
	            SIZE-5, 2*(SIZE),
	            (SIZE-5)/2, 3*(SIZE) 
	    });
		tail.setFill(Color.BLACK);
		translatePosition(tail,0,0);
		allPartsOfSnake.add(tail);

//		First element of snake body
		Rectangle fBody = addBody();
		translatePosition(fBody, 0, SIZE);
		allPartsOfSnake.add(fBody);
		
//		Head of the snake
		head = new Group();
		Path headShape = new Path();
		
		MoveTo moveTo = new MoveTo();
		moveTo.setX(0);
		moveTo.setY(SIZE-5);
		HLineTo hLineTo = new HLineTo();
		hLineTo.setX(SIZE-5);
		
		QuadCurveTo quadTo = new QuadCurveTo();
		quadTo.setControlX(SIZE-5);
		quadTo.setControlY(0.1);
		quadTo.setX((SIZE-5)/2);
		quadTo.setY(1);
		
		QuadCurveTo quadTo2 = new QuadCurveTo();
		quadTo2.setControlX(0);
		quadTo2.setControlY(0.9);	
		quadTo2.setX(0);
		quadTo2.setY(SIZE-5);
		
		headShape.getElements().add(moveTo);
		headShape.getElements().add(hLineTo);
		headShape.getElements().add(quadTo);
		headShape.getElements().add(quadTo2);
		
		headShape.setFill(Color.RED);
		headShape.setStroke(Color.BLACK);
		headShape.setStrokeWidth(3);
		headShape.setStrokeLineCap(StrokeLineCap.ROUND);
		
		head.getChildren().add(headShape);
		
		translatePosition(head,0,0);
		allPartsOfSnake.add(head);	
		
	}
	
	public ArrayList<Node> getAllParts() { return allPartsOfSnake; }
	
	public void move(ArrayList<Node> allPartsOfAllSnakes) throws Exception
	{
		this.allPartsOfAllSnakes = allPartsOfAllSnakes;
		switch (keyBoardChoice) {
			case "L":
				leftKeyBoardControl(); break;
			case "R":
				rightKeyBoardControl(); break;
		}
		
	}
	private void rightKeyBoardControl() throws Exception {

		switch (Keyboard.getLastKeyCodeR()) {
		case LEFT:  {
			this.lastValidKeyCode = KeyCode.LEFT;
			if(head.getRotate() == 90) {
				this.lastValidKeyCode = KeyCode.RIGHT;
				Keyboard.storeLastKeyCode(KeyCode.RIGHT);
				x = x + SIZE; 
				keyBoardMove(0, 180, -SIZE, 0, 0);
				break;
			}
			x = x - SIZE; 
			keyBoardMove(0, 180, SIZE, 0, -90);
			break;
		}
		case UP:    {
			this.lastValidKeyCode = KeyCode.UP;
			if(head.getRotate() == 180) {
				this.lastValidKeyCode = KeyCode.DOWN;
				Keyboard.storeLastKeyCode(KeyCode.DOWN);
				y = y + SIZE;
				keyBoardMove(-90, 90, 0, -SIZE, 0);
				break;
			}
			y = y - SIZE;
			keyBoardMove(-90, 90, 0, SIZE, 0);
			break;
		}
		case RIGHT: {
			this.lastValidKeyCode = KeyCode.RIGHT;
			if(head.getRotate() == -90) {
				this.lastValidKeyCode = KeyCode.LEFT;
				Keyboard.storeLastKeyCode(KeyCode.LEFT);
				x = x - SIZE; 
				keyBoardMove(0, 180, SIZE, 0, 0);
				break;
			}
			x = x + SIZE; 
			keyBoardMove(0, 180, -SIZE, 0, 90);
			break;
		}
		case DOWN:	{
			this.lastValidKeyCode = KeyCode.DOWN;
			if(head.getRotate() == 0) {
				this.lastValidKeyCode = KeyCode.UP;
				Keyboard.storeLastKeyCode(KeyCode.UP);
				y = y - SIZE;
				keyBoardMove(-90, 90, 0, SIZE, 0);
				break;
			}
			y = y + SIZE;
			keyBoardMove(-90, 90, 0, -SIZE, 180);
			break;
		}
		case P:
			this.lastValidKeyCode = KeyCode.P;
			// stop the game
			break;
		default: {
			// keep horizontal&vertical speed when any other key is pressed
			Keyboard.storeLastKeyCode(this.lastValidKeyCode);
			break;
		}
	}
	}
	private void leftKeyBoardControl() throws Exception {

		switch (Keyboard.getLastKeyCodeL()) {
		case A:  {
			this.lastValidKeyCode = KeyCode.LEFT;
			if(head.getRotate() == 90) {
				this.lastValidKeyCode = KeyCode.RIGHT;
				Keyboard.storeLastKeyCode(KeyCode.RIGHT);
				x = x + SIZE; 
				keyBoardMove(0, 180, -SIZE, 0, 0);
				break;
			}
			x = x - SIZE; 
			keyBoardMove(0, 180, SIZE, 0, -90);
			break;
		}
		case W:    {
			this.lastValidKeyCode = KeyCode.UP;
			if(head.getRotate() == 180) {
				this.lastValidKeyCode = KeyCode.DOWN;
				Keyboard.storeLastKeyCode(KeyCode.DOWN);
				y = y + SIZE;
				keyBoardMove(-90, 90, 0, -SIZE, 0);
				break;
			}
			y = y - SIZE;
			keyBoardMove(-90, 90, 0, SIZE, 0);
			break;
		}
		case D: {
			this.lastValidKeyCode = KeyCode.RIGHT;
			if(head.getRotate() == -90) {
				this.lastValidKeyCode = KeyCode.LEFT;
				Keyboard.storeLastKeyCode(KeyCode.LEFT);
				x = x - SIZE; 
				keyBoardMove(0, 180, SIZE, 0, 0);
				break;
			}
			x = x + SIZE; 
			keyBoardMove(0, 180, -SIZE, 0, 90);
			break;
		}
		case S:	{
			this.lastValidKeyCode = KeyCode.DOWN;
			if(head.getRotate() == 0) {
				this.lastValidKeyCode = KeyCode.UP;
				Keyboard.storeLastKeyCode(KeyCode.UP);
				y = y - SIZE;
				keyBoardMove(-90, 90, 0, SIZE, 0);
				break;
			}
			y = y + SIZE;
			keyBoardMove(-90, 90, 0, -SIZE, 180);
			break;
		}
		case P:
			this.lastValidKeyCode = KeyCode.P;
			// stop the game
			break;
		default: {
			// keep horizontal&vertical speed when any other key is pressed
			Keyboard.storeLastKeyCode(this.lastValidKeyCode);
			break;
		}
	}
	}
	private void keyBoardMove(double Rot1, double Rot2, double bodyX, double bodyY, double setRot ) throws Exception {
	
		Rectangle body = addBody();
		
		double head_x = head.getTranslateX();
		double head_y = head.getTranslateY();
		double headRotation = head.getRotate();
		
		if(headRotation == Rot1 || headRotation == Rot2) {
			translatePosition(head,0,0);
			head.setRotate(setRot);
			body.setTranslateX(head_x);
			body.setTranslateY(head_y);
		}
		else {
			translatePosition(body,bodyX,bodyY);
			translatePosition(head,0,0);
		}
		allPartsOfSnake.add(body);
		if(isCollision(head))
			throw new Exception("crash");
	}
	
	private Rectangle addBody() {
		Rectangle body = new Rectangle(SIZE-5, SIZE-5, color);
		body.setArcWidth(SIZE/2);
		body.setArcHeight(SIZE/2);
		body.setStroke(Color.BLACK);
		body.setStrokeWidth(3);
		body.setStrokeLineCap(StrokeLineCap.ROUND);
		return body;
	}
	
	private void translatePosition(Node nod, double plusX, double plusY)
	{
		nod.setTranslateX(x+plusX);
		nod.setTranslateY(y+plusY);
	}
//	methods that checks if snake head collides with another snake or with the walls.
	private boolean isCollision(Node block) {
		/**
		 * Checks collision with self and with wall. 
		 */
		boolean collision = false;
		double boardSizePX = GameEngine.GRID_SIZE * GameEngine.ELEMENT_SIZE;
		Position currentPosition = new Position(x,y);

//		check walls
		collision = (x >= boardSizePX || x < 0 || y >= boardSizePX || y < 0) 
				? true : false;
		if(collision)
			return collision;
//		check if snakes crashed with each other or with itself.
//		This is known when the position of the upper-left edge is the same.
		if(checkBounds(block))
			collision = true;
		return collision;
	}
	private boolean checkBounds(Node block) {
		  boolean collisionDetected = false;
		  for (Node node : allPartsOfAllSnakes) {
		    if (node != block) {
		      if (block.getBoundsInParent().intersects(node.getBoundsInParent())) {
		        collisionDetected = true;
		      }
		    }
		  }
		  return collisionDetected;
	}
	
}
