package frontendClasses;

import backendClasses.Cell;
import backendClasses.grid.Grid;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 * @author Jennifer Chin
 * 
 * Implements the Viewer interface. Class created to viewer a Triangle grid. Creates an equilateral triangle Polygon 
 * object to represent each cell of the grid and colors the object accordingly.
 */

public class TriangleViewer implements Viewer {
	
	private Group myGroup;
	private backendClasses.grid.Grid myGrid;
	private boolean isOutlined = true;
	
	/**
	 * Constructor for a TriangleViewer. Takes in a group, that the Polygons will be added to, and a grid, which will 
	 * be used to determine the color of the Polygons.
	 * @param group
	 * @param grid
	 */

	public TriangleViewer(Group group, Grid grid){
		myGroup = group;
		myGrid = grid;
	}
	
	/**
	 * Visualizes a Triangle grid. Takes in the x and y position of the upper left corner of the grid, the grid 
	 * width, grid height, number of grid rows, and number of column rows. Iterates over the Grid to create a triangle
	 * visual for each cell. Calculates the top left vertex of the triangle or the top point of the triangle
	 * and the side length of each triangle that will be visualized. Determines whether the triangle is bottom up or 
	 * point up depending on which row and column it is has in the Grid. Uses this information to create a Triangle 
	 * object and calculate the correct vertices. These vertices are used to create a Polygon object, whose fill is 
	 * determined by the State of its corresponding Cell in the Grid. All cell visuals are then added to the Group.
	 * @param x
	 * @param y
	 * @param gridWidth
	 * @param gridHeight
	 * @param gridRows
	 * @param gridCols
	 * @return Group
	 */
	
	@Override
	public Group initGrid(int x, int y, int gridWidth, int gridHeight, int gridRows, int gridCols) {
		myGroup = new Group();
		double yStep = (double) gridHeight / (double) gridRows;
		double side = yStep / Math.sin(Math.toRadians(60));
		double xStep = side;
		double xPos = (double) x;
		double yPos = (double) y;
		for (int r = 0; r < gridRows; r++){
			for (int c = 0; c < gridCols; c++){
				Triangle tempTri = new Triangle(xPos, yPos, side);
				Polygon cellVisual = new Polygon();
				if ((c % 2 == 0 && r % 2 == 0) || (r % 2 == 1 && c % 2 == 1)){
					cellVisual.getPoints().addAll(tempTri.getBottomUpPoints());
					xPos += xStep;
				}
				else{
					cellVisual.getPoints().addAll(tempTri.getPointUpPoints());
				}
				Cell cell = myGrid.getCellAtLocation(r, c);
				cellVisual.setFill(cell.getCurrentState().getStateColor());
				if (isOutlined){
					cellVisual.setStroke(Color.BLACK);
				}
				myGroup.getChildren().add(cellVisual);
			}
			yPos += yStep;
			if (r % 2 == 0){
				xPos = x + side / 2;
			}
			else{
				xPos = x;
			}
		}
		return myGroup;
	}
	
	/**
	 * Method that determines whether the Viewer should be created with grid lines or not
	 */

	@Override
	public void changeLines() {
		isOutlined = !isOutlined;
	}

}
