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
	boolean turning = false;
	public Snake(double x, double y, Color color)
	{
		this.color = color;
		this.x = x;
		this.y = y;
		this.allParts = new ArrayList<Node>();
		
//		Tail of the snake. Only one and with fixed position
		Polygon tail = new Polygon();
		tail.getPoints().addAll(new Double[]{
	            0.0, SIZE,
	            SIZE, SIZE,
	            (SIZE)/2, 2*SIZE 
	    });
		tail.setFill(Color.BLACK);
		translatePosition(tail);
		allParts.add(tail);

//		First element of snake body
		Rectangle fBody = addBody();
		translatePosition(fBody);
		allParts.add(fBody);
		
//		Head of the snake
		head = new Path();

		MoveTo moveTo = new MoveTo();
		moveTo.setX(0.0f);
		moveTo.setY(0.0f);

		HLineTo hLineTo = new HLineTo();
		hLineTo.setX(SIZE);
		
		QuadCurveTo quadTo = new QuadCurveTo();
		quadTo.setControlX(SIZE);
		quadTo.setControlY(-SIZE*0.9);
		quadTo.setX(SIZE/2);
		quadTo.setY(-SIZE);
		
		QuadCurveTo quadTo2 = new QuadCurveTo();
		quadTo2.setControlX(0);
		quadTo2.setControlY(-SIZE*0.9);	
		quadTo2.setX(0);
		quadTo2.setY(0);
		
		head.getElements().add(moveTo);
		head.getElements().add(hLineTo);
		head.getElements().add(quadTo);
		head.getElements().add(quadTo2);
		
		head.setFill(Color.RED);
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
				x = x - SIZE; 
				if(!turning) {
					double head_x = head.getTranslateX();
					double head_y = head.getTranslateY();
					translatePosition(head);
					rotatePosition(head, "L");
					body.setTranslateX(head_x);
					body.setTranslateY(head_y);
					turning = !turning;
					
//					translatePosition(body);
					
				}
				else {
					translatePosition(body);
					translatePosition(head);
				}
				allParts.add(body);
				break;
			}
			case UP:    {
				turning = false;
				y = y - SIZE; 
				translatePosition(body);
				translatePosition(head);
				allParts.add(body);
				break;
			}
			case RIGHT: {
				x = x + SIZE; translatePosition(body); translatePosition(head);
				allParts.add(body);
				break;
			}
			case DOWN:	{
				turning = false;
				y = y + SIZE; 
				translatePosition(body);
				translatePosition(head);
				allParts.add(body);
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
