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
	private ArrayList<Node> allParts;
	private Set<Position> allPositions;
	private double x, y;
	private Color color;
	private KeyCode lastValidKeyCode = KeyCode.P;
	Group head;

	public Snake(Position pos, Color color)
	{
		this.color = color;
		this.x = pos.getX();
		this.y = pos.getY();
		this.allParts = new ArrayList<Node>();
		this.allPositions = new HashSet<Position>();
		
//		Tail of the snake. Only one and with fixed position
		Polygon tail = new Polygon();
		tail.getPoints().addAll(new Double[]{
	            0.0, 2*SIZE,
	            SIZE, 2*SIZE,
	            (SIZE)/2, 3*SIZE 
	    });
		tail.setFill(Color.BLACK);
		translatePosition(tail,0,0);
		this.allPositions.add(new Position(x,y+2*SIZE));
		allParts.add(tail);

//		First element of snake body
		Rectangle fBody = addBody();
		translatePosition(fBody, 0, SIZE);
		this.allPositions.add(new Position(x,y+SIZE));
		allParts.add(fBody);
		
//		Head of the snake
		head = new Group();
		
		MoveTo moveTo = new MoveTo();
		moveTo.setX(0);
		moveTo.setY(SIZE);
		
		Path headWrapper = new Path();
		HLineTo hLineTo1 = new HLineTo();
		hLineTo1.setX(SIZE);
		VLineTo vLineTo1 = new VLineTo();
		vLineTo1.setY(SIZE);
		HLineTo hLineTo2 = new HLineTo();
		hLineTo1.setX(0);
		VLineTo vLineTo2 = new VLineTo();
		vLineTo1.setY(0);
		headWrapper.getElements().add(moveTo);
		headWrapper.getElements().add(hLineTo1);
		headWrapper.getElements().add(vLineTo1);
		headWrapper.getElements().add(hLineTo2);
		headWrapper.getElements().add(vLineTo2);	
		
		Path headShape = new Path();
		HLineTo hLineTo = new HLineTo();
		hLineTo.setX(SIZE);
		
		QuadCurveTo quadTo = new QuadCurveTo();
		quadTo.setControlX(SIZE);
		quadTo.setControlY(0.1);
		quadTo.setX(SIZE/2);
		quadTo.setY(0);
		
		QuadCurveTo quadTo2 = new QuadCurveTo();
		quadTo2.setControlX(0);
		quadTo2.setControlY(0.9);	
		quadTo2.setX(0);
		quadTo2.setY(SIZE);
		
		headShape.getElements().add(moveTo);
		headShape.getElements().add(hLineTo);
		headShape.getElements().add(quadTo);
		headShape.getElements().add(quadTo2);
		
		headShape.setFill(Color.RED);
		headShape.setStroke(Color.BLACK);
		headShape.setStrokeWidth(3.0);
		headShape.setStrokeLineCap(StrokeLineCap.ROUND);
		
//		head.getChildren().add(headWrapper);
		head.getChildren().add(headShape);
		
		translatePosition(head,0,0);
		allParts.add(head);	
		
	}
	
	public ArrayList<Node> getAllParts() { return allParts; }
	public Set<Position> getAllPositions() { return allPositions; }
	
	public void move(Set<Position> allPositions) throws Exception
	{
		this.allPositions = allPositions;
		switch (Keyboard.getLastKeyCode()) {
			case LEFT:  {
				this.lastValidKeyCode = KeyCode.LEFT;
				if(head.getRotate() == 90) {
					this.lastValidKeyCode = KeyCode.RIGHT;
					Keyboard.storeLastKeyCode(KeyCode.RIGHT);
					x = x + SIZE; 
					keyBoardMove(0, 180, -SIZE, 0, 0);
					this.allPositions.add(new Position(x,y));
					break;
				}
				x = x - SIZE; 
				keyBoardMove(0, 180, SIZE, 0, -90);
				this.allPositions.add(new Position(x,y));
				break;
			}
			case UP:    {
				this.lastValidKeyCode = KeyCode.UP;
				if(head.getRotate() == 180) {
					this.lastValidKeyCode = KeyCode.DOWN;
					Keyboard.storeLastKeyCode(KeyCode.DOWN);
					y = y + SIZE;
					keyBoardMove(-90, 90, 0, -SIZE, 0);
					this.allPositions.add(new Position(x,y));
					break;
				}
				y = y - SIZE;
				keyBoardMove(-90, 90, 0, SIZE, 0);
				this.allPositions.add(new Position(x,y));
				break;
			}
			case RIGHT: {
				this.lastValidKeyCode = KeyCode.RIGHT;
				if(head.getRotate() == -90) {
					this.lastValidKeyCode = KeyCode.LEFT;
					Keyboard.storeLastKeyCode(KeyCode.LEFT);
					x = x - SIZE; 
					keyBoardMove(0, 180, SIZE, 0, 0);
					this.allPositions.add(new Position(x,y));
					break;
				}
				x = x + SIZE; 
				keyBoardMove(0, 180, -SIZE, 0, 90);
				this.allPositions.add(new Position(x,y));
				break;
			}
			case DOWN:	{
				this.lastValidKeyCode = KeyCode.DOWN;
				if(head.getRotate() == 0) {
					this.lastValidKeyCode = KeyCode.UP;
					Keyboard.storeLastKeyCode(KeyCode.UP);
					y = y - SIZE;
					keyBoardMove(-90, 90, 0, SIZE, 0);
					this.allPositions.add(new Position(x,y));
					break;
				}
				y = y + SIZE;
				keyBoardMove(-90, 90, 0, -SIZE, 180);
				this.allPositions.add(new Position(x,y));
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
		if(isCollision())
			throw new Exception("crash");
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
		allParts.add(body);	
	}
	
	private Rectangle addBody() {
		Rectangle body = new Rectangle(SIZE, SIZE, color);
		body.setArcWidth(SIZE/2);
		body.setArcHeight(SIZE/2);
		body.setStroke(Color.BLACK);
		body.setStrokeWidth(3.0);
		body.setStrokeLineCap(StrokeLineCap.ROUND);
		return body;
	}
	
	private void translatePosition(Node nod, double plusX, double plusY)
	{
		nod.setTranslateX(x+plusX);
		nod.setTranslateY(y+plusY);
	}
//	methods that checks if snake head collides with another snake or with the walls.
	private boolean isCollision() {
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
////		check crash with itself
//		for(Node node : allParts) {
//			collision = (node.getTranslateX() == x && node.getTranslateY() == y) 
//					? true : false;
//			if(collision)
//				break;
//		}
		
//		check if snakes crashed with each other or with itself. 
//		This is known when the position of the upper-left edge is the same.
		if(allPositions.contains(currentPosition))
			collision = true;
		return collision;
	}
	
}
