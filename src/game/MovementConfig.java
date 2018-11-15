package game;

public class MovementConfig {
	Position position;
	double Rot1;
	double Rot2;
	double bodyX; 
	double bodyY;
	double setRot;
	
	public MovementConfig(double Rot1, double Rot2, double bodyX, double bodyY, double setRot, Position currentPosition ) {
		this.position = currentPosition;
		this.Rot1 = Rot1;
		this.Rot2 = Rot2;
		this.bodyX = bodyX;
		this.bodyY = bodyY;
		this.setRot = setRot;
	}
	
}
