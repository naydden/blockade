package game;

public interface Movement {
	public Position nextPosition (double headRotation, Position lastPosition);
	public MovementConfig getMoveConfig();
}
