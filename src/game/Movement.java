package game;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.Node;

public interface Movement {
	public MovementConfig nextPosition (Group head, Position lastPosition) throws Exception;
}
