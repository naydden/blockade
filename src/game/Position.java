package game;


public class Position {
	private Double x;
	private Double y;
	public Position() {
		x = 0.0;
		y = 0.0;
	}
	public Position(double x0, double y0) {
		x = x0;
		y = y0;	
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
    @Override
    public boolean equals(Object o) { 
  
        // If the object is compared with itself then return true   
        if (o == this) { 
            return true; 
        } 
  
        /* Check if o is an instance of Complex or not 
          "null instanceof [type]" also returns false */
        if (!(o instanceof Position)) { 
            return false; 
        } 
          
        // typecast o to Complex so that we can compare data members  
        Position c = (Position) o; 
          
        // Compare the data members and return accordingly  
        return Double.compare(x, c.x) == 0
                && Double.compare(y, c.y) == 0; 
    }
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + x.hashCode() + y.hashCode();
        return result;
      }
    @Override
	public String toString() {
		  return "(" + x + "," + y + ")";
	}
}
