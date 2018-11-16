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
	public MovementConfig nextPosition (double headRotation, Position lastPosition) throws Exception {
		this.headRotation = headRotation;
		this.lastPos = lastPosition;
		return KeyBoardControl(this.keyLEFT, this.keyRIGHT, this.keyUP, this.keyDOWN);
	}
	private MovementConfig KeyBoardControl(KeyCode L, KeyCode R, KeyCode U, KeyCode D) throws Exception {
		KeyCode last;
		if(L.equals(KeyCode.A))
			last =  Keyboard.getLastKeyCodeL();
		else
			last =  Keyboard.getLastKeyCodeR();
	
		if( last.equals(L))
			return go(Direction.LEFT,L,R);
		else if(last.equals(U))
			return go(Direction.UP,U,D);
		else if(last.equals(R))
			return go(Direction.RIGHT,R,L);
		else if(last.equals(D))
			return go(Direction.DOWN,D,U);
		else {
			if( this.lastValidKeyCode.equals(L))
				return go(Direction.LEFT,L,R);
			else if(last.equals(U))
				return go(Direction.UP,U,D);
			else if(last.equals(R))
				return go(Direction.RIGHT,R,L);
			else if(last.equals(D))
				return go(Direction.DOWN,D,U);
			else {
				return go(Direction.UP,U,D);
			}
		}
	}
	public MovementConfig go(Direction dir, KeyCode dirKey1, KeyCode dirKey2) throws Exception {
		switch(dir) {
			case UP:{
				this.lastValidKeyCode = dirKey1;
				if(this.headRotation == 180) {
					this.lastValidKeyCode = dirKey2;
					Keyboard.storeLastKeyCode(dirKey2);
					return new MovementConfig(-90, 90, 0, -SIZE, 0, 
							new Position(lastPos.getX(),lastPos.getY()+SIZE));
				}
				return new MovementConfig(-90, 90, 0, SIZE, 0,
						new Position(lastPos.getX(),lastPos.getY()-SIZE));
			}
			case DOWN: {
				this.lastValidKeyCode = dirKey1;
				if(this.headRotation == 0) {
					this.lastValidKeyCode = dirKey2;
					Keyboard.storeLastKeyCode(dirKey2);
					return new MovementConfig(-90, 90, 0, SIZE, 0, 
							new Position(lastPos.getX(),lastPos.getY()-SIZE));
				}
				return new MovementConfig(-90, 90, 0, -SIZE, 180,
							new Position(lastPos.getX(),lastPos.getY()+SIZE));
			}
			case LEFT: {
				this.lastValidKeyCode = dirKey1;
				if(this.headRotation == 90) {
					this.lastValidKeyCode = dirKey2;
					Keyboard.storeLastKeyCode(dirKey2);
					return new MovementConfig(0, 180, -SIZE, 0, 0,
							new Position(lastPos.getX()+SIZE,lastPos.getY()));
				}
				return new MovementConfig(0, 180, SIZE, 0, -90,
							new Position(lastPos.getX()-SIZE,lastPos.getY()));
			}
			case RIGHT: {
				this.lastValidKeyCode = dirKey1;
				if(this.headRotation == -90) {
					this.lastValidKeyCode = dirKey2;
					Keyboard.storeLastKeyCode(dirKey2);
					return new MovementConfig(0, 180, SIZE, 0, 90,
							new Position(lastPos.getX()-SIZE,lastPos.getY()));
				}
				return new MovementConfig(0, 180, -SIZE, 0, 90,
							new Position(lastPos.getX()+SIZE,lastPos.getY()));	
			}
			default:
				throw new Exception("Specified direction is not valid!");
		}
	}
}
