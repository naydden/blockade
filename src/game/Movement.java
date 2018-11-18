package game;

import javafx.scene.Group;
/**
 * Movement interface to provide abstraction from the snake point of view.
 * 
 * @author Boyan Naydenov
 *
 */
public interface Movement {
	/**
	 * Returns a MovementConfig object containing the computed next position
	 * so that a snake can consume it.
	 * 
	 * @param head			The head of the snake, since it is necessary to know its rotation. 
	 * @param lastPosition	The current position of the head, used to locate the next position.
	 * @return				The object containing all the information of the next position.
	 * @throws Exception	
	 */
	public MovementConfig nextPosition(Group head, Position lastPosition) throws Exception;
}
