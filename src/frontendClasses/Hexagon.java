package frontendClasses;

/**
 * @author Jennifer Chin
 *
 * This class calculates the the 6 vertices of a regular hexagon by using a specified center point and "radius." It is
 * called by the HexagonViewer class in order to easily create visual polygon representations of Cells for a Hexagonal
 * grid
 */

public class Hexagon{
	
	public static final double ANGLE = Math.toRadians(30);
	
	double myCenterX;
	double myCenterY;
	double myRadius;
	
	/**
	 * Constructor for a hexagon that takes in a center point and radius. The radius is the length from the center 
	 * point to a vertex. 
	 * @param centerX
	 * @param centerY
	 * @param radius
	 */
	
	public Hexagon(double centerX, double centerY, double radius){
		myCenterX = centerX;
		myCenterY = centerY;
		myRadius =  radius;
	}
	
	/**
	 * Calculates the 6 vertices of a regular hexagon using the center point and radius
	 * @return Double[]
	 */
	
	public Double[] getPointsArray(){
		double vert1x = myCenterX;
		double vert1y = myCenterY - myRadius;
		double vert2x = myCenterX + (myRadius * Math.cos(ANGLE));
		double vert2y = myCenterY - (myRadius * Math.sin(ANGLE));
		double vert3x = vert2x;
		double vert3y = myCenterY + (myRadius * Math.sin(ANGLE));
		double vert4x = myCenterX;
		double vert4y = myCenterY + myRadius;
		double vert5x = myCenterX - (myRadius * Math.cos(ANGLE));
		double vert5y = vert3y;
		double vert6x = vert5x;
		double vert6y = vert2y;
		Double[] points = {vert1x, vert1y, vert2x, vert2y, vert3x, vert3y, 
				vert4x, vert4y, vert5x, vert5y, vert6x, vert6y};
		return points;
	}

}
