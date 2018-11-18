package game;

import javafx.scene.input.KeyCode;

public class Keyboard {

	/** Last-pressed KeyCode */
	private static KeyCode lastKeyCodeR = KeyCode.UP;
	private static KeyCode lastKeyCodeL = KeyCode.RIGHT;

	/** You shall not create a Keyboard object */
	private Keyboard() { }

	/**
	 * Automatically invoked by the keyboard event listener,
	 * stores the last-pressed KeyCode.
	 */
	public static void storeLastKeyCode(KeyCode keycode) { 
		
		if(keycode == KeyCode.UP || keycode == KeyCode.DOWN 
				|| keycode == KeyCode.LEFT || keycode == KeyCode.RIGHT ) {
			lastKeyCodeR = keycode;
		}
		else if(keycode == KeyCode.W || keycode == KeyCode.D 
				|| keycode == KeyCode.A || keycode == KeyCode.S ) {
			lastKeyCodeL = keycode;			
		}
		
	 }

	/** 
	 * Get the last typed KeyCode.
	 * @return the last pressed KeyCode.
	 */
	public static KeyCode getLastKeyCodeL() { return lastKeyCodeL; }
	public static KeyCode getLastKeyCodeR() { return lastKeyCodeR; }

}
