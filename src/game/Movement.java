package game;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.Node;

public interface Movement {
	public MovementConfig nextPosition (double headRotation, Position lastPosition) throws Exception;
}
