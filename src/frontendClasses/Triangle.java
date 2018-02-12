package frontendClasses;

/**
 * @author Jennifer Chin
 *
 * This class calculates the the 3 vertices of an equilateral tirangle by using either the top left corner of the 
 * triangle or the top point of the triangle, and the side length. It is called by the TriangleViewer class in order 
 * to easily create visual polygon representations of Cells for a Triangle grid
 */

public class Triangle {

	public static final double ANGLE = Math.toRadians(60);
	
	double myXStart;
	double myYStart;
	double mySide;
	
	/**
	 * Constructor that takes in the x and y position of the either the top left vertex of the triangle or the top
	 * center point of the triangle and the side length.
	 * @param x
	 * @param y
	 * @param side
	 */
	
	public Triangle(double x, double y, double side){
		myXStart = x;
		myYStart = y;
		mySide = side;
	}
	
	/**
	 * Calculates the 3 vertices of an equilateral triangle if the triangle is "bottom up." This means that there are
	 * 2 vertices on the same line as the top of the triangle, and there is one vertex below that connects with the 
	 * other 2 to create an equilateral triangle.
	 * @return Double[]
	 */
	
	public Double[] getBottomUpPoints(){
		double vert1x = myXStart;
		double vert1y = myYStart;
		double vert2x = myXStart + mySide;
		double vert2y = myYStart;
		double vert3x = myXStart + mySide / 2;
		double vert3y = myYStart + (mySide * Math.sin(ANGLE));
		Double[] points = {vert1x, vert1y, vert2x, vert2y, vert3x, vert3y};
		return points;
	}
	
	/**
	 * Calculates the 3 vertices of an equilateral triangle if the triangle is "point up." This means that there is 
	 * one vertex as the top of the triangle, and there are 2 vertices below on the same line that connect with the
	 * point to form an equilateral triangle. 
	 * @return Double[]
	 */
	
	public Double[] getPointUpPoints(){
		double vert1x = myXStart;
		double vert1y = myYStart;
		double vert2x = myXStart + mySide / 2;
		double vert2y = myYStart + (mySide * Math.sin(ANGLE));
		double vert3x = myXStart - mySide / 2;
		double vert3y = vert2y;
		Double[] points = {vert1x, vert1y, vert2x, vert2y, vert3x, vert3y};
		return points;
	}
	
}
