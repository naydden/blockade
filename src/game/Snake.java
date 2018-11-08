package game;


import javafx.scene.paint.Color;
//import javafx.scene.shape.Rectangle;
//import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.*;

import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.input.KeyCode;

public class Snake { // square eggs fill space much better
	private static final double SIZE = GameEngine.ELEMENT_SIZE; 
	private static final double STEP = 12;
	private ArrayList<Node> allParts;
	private double x, y;
	private Color color;
	Path head;

	public Snake(double x, double y, Color color)
	{
		this.color = color;
		this.x = x;
		this.y = y;
		this.allParts = new ArrayList<Node>();
		
//		Tail of the snake. Only one and with fixed position
		Polygon tail = new Polygon();
		tail.getPoints().addAll(new Double[]{
	            0.0, 2*SIZE,
	            SIZE, 2*SIZE,
	            (SIZE)/2, 3*SIZE 
	    });
		tail.setFill(Color.BLACK);
		translatePosition(tail,0,0);
		allParts.add(tail);

//		First element of snake body
		Rectangle fBody = addBody();
		translatePosition(fBody, 0, SIZE);
		allParts.add(fBody);
		
//		Head of the snake
		head = new Path();

		MoveTo moveTo = new MoveTo();
		moveTo.setX(0);
		moveTo.setY(SIZE);

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
		
		head.getElements().add(moveTo);
		head.getElements().add(hLineTo);
		head.getElements().add(quadTo);
		head.getElements().add(quadTo2);
		
		head.setFill(Color.RED);
		head.setStroke(Color.BLACK);
		head.setStrokeWidth(3.0);
		head.setStrokeLineCap(StrokeLineCap.ROUND);
		
		translatePosition(head,0,0);
		allParts.add(head);	
		
	}
	
	public ArrayList<Node> getAllParts() { return allParts; }

	public void move() throws Exception
	{
		switch (Keyboard.getLastKeyCode()) {
			case LEFT:  {
				if(head.getRotate() == 90) {
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
				if(head.getRotate() == 180) {
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
				if(head.getRotate() == -90) {
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
				if(head.getRotate() == 0) {
					Keyboard.storeLastKeyCode(KeyCode.UP);
					y = y - SIZE;
					keyBoardMove(-90, 90, 0, SIZE, 0);
					break;
				}
				y = y + SIZE;
				keyBoardMove(-90, 90, 0, -SIZE, 180);
				break;
			}
			default: // keep horizontal&vertical speed when any other key is pressed
		}
	}
	private void keyBoardMove(double Rot1, double Rot2, double bodyX, double bodyY, double setRot ) {
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
		boolean collision = false;;
		for(Node node : allParts) {
			collision = (node.getTranslateX() == x && node.getTranslateY() == y) ? true : false;
			if(collision)
				break;
		}
		return collision;
	}
	
}
