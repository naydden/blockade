package game;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;

public class ControledMovement implements Movement {
	
	private static final double SIZE = GameEngine.ELEMENT_SIZE;
	
	private KeyCode lastValidKeyCode;
	private MovementConfig moveConfig;
	private double headRotation; 
	private Position lastPos;
	ArrayList<Node> allPartsOfAllSnakes;
	Group headOfSnake;
		
	private KeyCode keyLEFT;
	private KeyCode keyRIGHT;
	private KeyCode keyUP;
	private KeyCode keyDOWN;
	
	public ControledMovement(String KeyBoardChoice) {
		switch (KeyBoardChoice) {
			case "L":{
				this.keyDOWN = KeyCode.S;
				this.keyLEFT = KeyCode.A;
				this.keyRIGHT = KeyCode.D;
				this.keyUP = KeyCode.W;
				this.lastValidKeyCode = KeyCode.W;
				break;
			}
			case "R":{
				this.keyDOWN = KeyCode.DOWN;
				this.keyLEFT = KeyCode.LEFT;
				this.keyRIGHT = KeyCode.RIGHT;
				this.keyUP = KeyCode.UP;
				this.lastValidKeyCode = KeyCode.UP;
				break;
			}
		}
		Keyboard.storeLastKeyCode(this.lastValidKeyCode);
	}
	public Position nextPosition (double headRotation, Position lastPosition) {
		this.headRotation = headRotation;
		this.lastPos = lastPosition;
		return KeyBoardControl(this.keyLEFT, this.keyRIGHT, this.keyUP, this.keyDOWN);
	}
	public MovementConfig getMoveConfig() {
		return this.moveConfig;
	}
	private Position KeyBoardControl(KeyCode L, KeyCode R, KeyCode U, KeyCode D) {
		KeyCode last;
		if(L.equals(KeyCode.A))
			last =  Keyboard.getLastKeyCodeL();
		else
			last =  Keyboard.getLastKeyCodeR();
	
		if( last.equals(L))
			return goLEFT(L,R);
		else if(last.equals(U))
			return goUP(U,D);
		else if(last.equals(R))
			return goRIGHT(R,L);
		else if(last.equals(D))
			return goDOWN(D,U);
		else {
			if( this.lastValidKeyCode.equals(L))
				return goLEFT(L,R);
			else if(last.equals(U))
				return goUP(U,D);
			else if(last.equals(R))
				return goRIGHT(R,L);
			else if(last.equals(D))
				return goDOWN(D,U);
			else {
				return goUP(U,D);
			}
		}
	}
	public Position goUP(KeyCode Up, KeyCode Down) {
		this.lastValidKeyCode = Up;
		if(this.headRotation == 180) {
			this.lastValidKeyCode = Down;
			Keyboard.storeLastKeyCode(Down);
			this.moveConfig = new MovementConfig(-90, 90, 0, -SIZE, 0);
			return new Position(lastPos.getX(),lastPos.getY()+SIZE);
		}
		this.moveConfig = new MovementConfig(-90, 90, 0, SIZE, 0);
		return new Position(lastPos.getX(),lastPos.getY()-SIZE);	
	}
	public Position goDOWN(KeyCode Down, KeyCode Up) {
		this.lastValidKeyCode = Down;
		if(this.headRotation == 0) {
			this.lastValidKeyCode = Up;
			Keyboard.storeLastKeyCode(Up);
			this.moveConfig = new MovementConfig(-90, 90, 0, SIZE, 0);
			return new Position(lastPos.getX(),lastPos.getY()-SIZE);
		}
		this.moveConfig = new MovementConfig(-90, 90, 0, -SIZE, 180);
		return new Position(lastPos.getX(),lastPos.getY()+SIZE);
	}
	public Position goLEFT(KeyCode Left, KeyCode Right) {
		this.lastValidKeyCode = Left;
		if(this.headRotation == 90) {
			this.lastValidKeyCode = Right;
			Keyboard.storeLastKeyCode(Right);
			
			this.moveConfig = new MovementConfig(0, 180, -SIZE, 0, 0);
			return new Position(lastPos.getX()+SIZE,lastPos.getY());
		}
		this.moveConfig = new MovementConfig(0, 180, SIZE, 0, -90);
		return new Position(lastPos.getX()-SIZE,lastPos.getY());
	}
	public Position goRIGHT(KeyCode Right, KeyCode Left) {
		this.lastValidKeyCode = Right;
		if(this.headRotation == -90) {
			this.lastValidKeyCode = Left;
			Keyboard.storeLastKeyCode(Left);
			this.moveConfig = new MovementConfig(0, 180, SIZE, 0, 90);
			return new Position(lastPos.getX()-SIZE,lastPos.getY());

		}
		this.moveConfig = new MovementConfig(0, 180, -SIZE, 0, 90);
		return new Position(lastPos.getX()+SIZE,lastPos.getY());
	}
}
