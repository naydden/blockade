package game;

import javafx.scene.Group;

public interface Movement {
	public MovementConfig nextPosition (Group head, Position lastPosition) throws Exception;
}
