package game;

import java.io.Serializable;
/**
 * Utility class to store the next move parameters.
 * The Movement classes generate one and the snake consumes
 * it and creates the next position.
 * 
 * Implements Serializable in order to send through a stream.
 * 
 * @author Boyan Naydenov
 * 
 */
public class MovementConfig implements Serializable {
	/** Necessary to deserialize properly */
	private static final long serialVersionUID = 1L;
	/** Position of the upper left corner.*/
	Position position;
	/** Possible rotation of the desired direction (e.g. LEFT) in degrees */
	double Rot1;
	/** Possible rotation of the desired direction (e.g. RIGHT) in degrees */
	double Rot2;
	/** necessary X translation */
	double bodyX;
	/** necessary Y translation */
	double bodyY;
	/** necessary rotation in degrees */
	double setRot;

	public MovementConfig(double Rot1, double Rot2, double bodyX, double bodyY, double setRot,
			Position currentPosition) {
		this.position = currentPosition;
		this.Rot1 = Rot1;
		this.Rot2 = Rot2;
		this.bodyX = bodyX;
		this.bodyY = bodyY;
		this.setRot = setRot;
	}

}
