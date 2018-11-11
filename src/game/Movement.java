package game;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.Node;

public interface Movement {
	public Position nextPosition (double headRotation, Position lastPosition);
	public MovementConfig getMoveConfig();
	public void setData(ArrayList<Node> allPartsOfAllSnakes, Group head);
}
