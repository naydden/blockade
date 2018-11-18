package game;


import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.util.ArrayList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;

public class Snake {
	private static final double SIZE = GameEngine.ELEMENT_SIZE;
	private static final double BOARDSIZEPX = GameEngine.GRID_SIZE * GameEngine.ELEMENT_SIZE;
	private ArrayList<Node> allPartsOfSnake;
	private ArrayList<Node> allPartsOfAllSnakes;
	private double x, y;
	private Color color;
	private Movement mov;
	String snakeName;
	private boolean crashed;
	Group head;
	MovementConfig nextPosition;

	public Snake(String snakeName, Position pos, Color color, int direction)
	{
		this.snakeName = snakeName;
		this.color = color;
		this.x = pos.getX();
		this.y = pos.getY();
		this.allPartsOfSnake = new ArrayList<Node>();
		this.allPartsOfAllSnakes = new ArrayList<Node>();
		this.crashed = false;
		
//		Tail of the snake. Only one and with fixed position
		if(direction == 0) {
			Double[] snakeCoord = new Double[]{
		            -3.0, 1.5,
		            -3.0, SIZE-4.5,
		            -SIZE*0.9, SIZE/2 
		    };		
			initSnake(snakeCoord,-SIZE,90,1);
			Keyboard.storeLastKeyCode(KeyCode.D);
		}
		else {
			Double[] snakeCoord = new Double[]{
		            0.0, 1.5,
		            0.0, SIZE-3.5,
		            SIZE*0.9, SIZE/2
		    };		
			initSnake(snakeCoord,SIZE,-90,2);
			Keyboard.storeLastKeyCode(KeyCode.RIGHT);
		}
	}
	
	public ArrayList<Node> getAllParts() { return allPartsOfSnake; }
	
	public void initSnake(Double[] tailCoor, double transX, double rotateHead, double transTail) {
		// tail of snake
		Polygon tail = new Polygon();
		tail.getPoints().addAll(tailCoor);
		tail.setFill(Color.BLACK);
		translatePosition(tail,transTail*transX,0);
		allPartsOfSnake.add(tail);

		// First element of snake body
		Rectangle fBody = addBody();
		translatePosition(fBody, transX, 0);
		allPartsOfSnake.add(fBody);
		
		// Head of the snake
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
		head.setRotate(rotateHead);
		head.getChildren().add(headShape);
		
		translatePosition(head,0,0);
		allPartsOfSnake.add(head);
	}
	public void move(ArrayList<Node> allPartsOfAllSnakes) throws Exception
	{
		this.allPartsOfAllSnakes = allPartsOfAllSnakes;
		this.nextPosition = mov.nextPosition(head,new Position(x,y));
		nodeMove(this.nextPosition);
	}
	public void controlledMove(ArrayList<Node> allPartsOfAllSnakes, MovementConfig nextPos) throws Exception
	{
		this.allPartsOfAllSnakes = allPartsOfAllSnakes;
		nodeMove(nextPos);
	}

	private void nodeMove(MovementConfig mov) {
	
		this.x = mov.position.getX();
		this.y = mov.position.getY();
		Rectangle body = addBody();
		
		double head_x = head.getTranslateX();
		double head_y = head.getTranslateY();
		double headRotation = head.getRotate();
		
		if(headRotation == mov.Rot1 || headRotation == mov.Rot2) {
			translatePosition(head,0,0);
			head.setRotate(mov.setRot);
			body.setTranslateX(head_x);
			body.setTranslateY(head_y);
		}
		else {
			translatePosition(body,mov.bodyX,mov.bodyY);
			translatePosition(head,0,0);
		}
		allPartsOfSnake.add(body);
		
		if(isCollision(head)) {
			this.crashed = true;
		}
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

	private boolean isCollision(Node block) {
		/**
		 * Checks collision with self and with wall. 
		 */
		boolean collisionWalls = (x >= BOARDSIZEPX || x < 0 || y >= BOARDSIZEPX || y < 0) 
				? true : false;	
		boolean collisionSnake = checkBounds(block);
		return collisionWalls || collisionSnake;
	}
	
	private boolean checkBounds(Node head) {
		  for (Node node : allPartsOfAllSnakes) {
		    if (node != head)
		      if (head.getBoundsInParent().intersects(node.getBoundsInParent()))
		    	  return true;
		  }
		  return false;
	}
	
	@Override
	public String toString() {
		return this.snakeName;
	}
	public ArrayList<Node> getAllPartsOfAllSnakes(){
		return this.allPartsOfAllSnakes;
	}
	public Group getHead(){
		return this.head;
	}
	public void setMovement(Movement mov) {
		this.mov = mov;
	}
	public boolean isCrashed() { return this.crashed; }
	
}
