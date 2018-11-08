package game;


import javafx.scene.paint.Color;
//import javafx.scene.shape.Rectangle;
//import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.*;

import java.util.ArrayList;

import javafx.scene.Node;

public class Snake { // square eggs fill space much better
	private static final double SIZE = GameEngine.ELEMENT_SIZE; 
	private static final double STEP = 12;
	private ArrayList<Node> allParts;
	private double x, y;
	private Color color;
	Path head;
	boolean turningL = false;
	boolean turningR = false;
	boolean turningD = false;
	boolean turningU = false;
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
		translatePosition(tail);
		allParts.add(tail);

//		First element of snake body
		Rectangle fBody = addBody();
		translatePositionBody(fBody, 0, SIZE);
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
		
		translatePosition(head);
		allParts.add(head);	
		
	}
	
	public ArrayList<Node> getAllParts() { return allParts; }

	public void move() throws Exception
	{
		Rectangle body = addBody();
		// constant accelerated move as soon as a key is pressed
		switch (Keyboard.getLastKeyCode()) {
			case LEFT:  {
				turningU = false;
				turningD = false;
				turningR = false;
				x = x - SIZE; 
				if(!turningL) {
					double head_x = head.getTranslateX();
					double head_y = head.getTranslateY();
					translatePosition(head);
					rotatePosition(head, "L");
					body.setTranslateX(head_x);
					body.setTranslateY(head_y);
					turningL = !turningL;				
				}
				else {
					translatePositionBody(body,SIZE,0 );
					translatePosition(head);
				}
				allParts.add(body);
				break;
			}
			case UP:    {
				turningR = false;
				turningD = false;
				turningL = false;
				y = y - SIZE; 
				if(!turningU) {
					double head_x = head.getTranslateX();
					double head_y = head.getTranslateY();
					translatePosition(head);
					rotatePosition(head, "L");
					body.setTranslateX(head_x);
					body.setTranslateY(head_y);
					turningR = !turningR;				
				}
				else {
					translatePositionBody(body,-SIZE,0 );
					translatePosition(head);
				}
				allParts.add(body);
				break;
			}
			case RIGHT: {
				turningU = false;
				turningD = false;
				turningL = false;
				x = x + SIZE; 
				if(!turningR) {
					double head_x = head.getTranslateX();
					double head_y = head.getTranslateY();
					translatePosition(head);
					rotatePosition(head, "R");
					body.setTranslateX(head_x);
					body.setTranslateY(head_y);
					turningR = !turningR;				
				}
				else {
					translatePositionBody(body,-SIZE,0 );
					translatePosition(head);
				}
				allParts.add(body);
				break;
			}
			case DOWN:	{
				turningU = false;
				turningR = false;
				turningL = false;
				y = y + SIZE; 
				translatePositionBody(body,0,-SIZE);
				translatePosition(head);
				allParts.add(body);
				direction = "down";
				break;
			}
			default: // keep horizontal&vertical speed when any other key is pressed
		}
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
	

	private void translatePosition(Node nod)
	{
		// these are the methods that actually position the Node on screen
		nod.setTranslateX(x);
		nod.setTranslateY(y);
	}
	private void translatePositionBody(Node nod, double plusX, double plusY)
	{
		// these are the methods that actually position the Node on screen
		nod.setTranslateX(x+plusX);
		nod.setTranslateY(y+plusY);
	}
	
	private void rotatePosition(Node nod, String direction) throws Exception
	{
		if(direction == "R") {
			nod.setRotate(90);
		}
		else if (direction == "L") {
			nod.setRotate(-90);
		}
		else {
			throw new Exception();
		}
	}
}
