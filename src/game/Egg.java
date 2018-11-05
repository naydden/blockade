package game;


import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;

import java.util.ArrayList;

import javafx.scene.Node;

public class Egg extends Rectangle { // square eggs fill space much better
	private static final double SIZE = Game.ELEMENT_SIZE; 
	private static final double STEP = 0.25;
	private ArrayList<Node> allParts;
	private double x, y, dx, dy;
	
	public Egg(double x, double y, Color color)
	{
		super(SIZE, SIZE, color);
		this.x = x;
		this.y = y;
		this.allParts = new ArrayList<Node>();
		this.dx = Math.random()-0.5;
		this.dy = Math.random()-0.5;
		updatePosition();

		allParts.add(this);
		
		// a few unnecessary esthetic calls
		setArcWidth(SIZE/2);
		setArcHeight(SIZE/2);
		setStroke(Color.BLACK);
		setStrokeWidth(3.0);
		setStrokeLineCap(StrokeLineCap.ROUND);
	}
	
	public ArrayList<Node> getAllParts() { return allParts; }

	public void move()
	{
		// constant accelerated move as soon as a key is pressed
		switch (Keyboard.getLastKeyCode()) {
			case LEFT:  dx = dx-STEP; break;
			case UP:    dy = dy-STEP; break;
			case RIGHT: dx = dx+STEP; break;
			case DOWN:	dy = dy+STEP; break;
			default: // keep horizontal&vertical speed when any other key is pressed
		}
		x = x + dx;
		y = y + dy;
		updatePosition();
	}
	
	private void updatePosition()
	{
		// these are the methods that actually position the Node on screen
		setTranslateX(x);
		setTranslateY(y);
	}
}
