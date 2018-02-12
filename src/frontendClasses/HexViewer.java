package frontendClasses;

import backendClasses.Cell;
import backendClasses.grid.Grid;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 * @author Jennifer Chin
 * 
 * Implements the Viewer interface. Class created to viewer a Hexagon grid. Creates a hexagonal Polygon object to 
 * represent each cell of the grid and colors the object accordingly.
 */

public class HexViewer implements Viewer{
    
	private Group myGroup;
	private Grid myGrid;
	private boolean isOutlined = true;
	
	/**
	 * Constructor for a HexViewer. Takes in a group, that the Polygons will be added to, and a grid, which will be 
	 * used to determine the color of the Polygons.
	 * @param group
	 * @param grid
	 */

	public HexViewer(Group group, Grid grid){
		myGroup = group;
		myGrid = grid;
	}
	
	/**
	 * Visualizes a hexagonal grid. Takes in the x and y position of the upper left corner of the grid, the grid 
	 * width, grid height, number of grid rows, and number of column rows. Iterates over the Grid to create a hexagon
	 * visual for each cell. Calculates the center point and radius of each hexagon that will be visualized. Uses 
	 * these numbers to create a Hexagon object for each cell in order to calculate the correct vertices. These 
	 * vertices are used to create a Polygon object, whose fill is determined by the State of its corresponding Cell 
	 * in the Grid. All cell visuals are then added to the Group.
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
		double xStep = (double) gridWidth / (double) gridCols;
		double radius = xStep / Math.sqrt(3);
		double yStep = radius + (radius * Math.sin(Math.toRadians(30)));
		double xPos = (double) x + xStep / 2;
		double yPos = (double) y + radius;
		for (int r = 0; r < gridRows; r++){
			for (int c = 0; c < gridCols; c++){
				Hexagon tempHex = new Hexagon(xPos, yPos, radius);
				Polygon cellVisual = new Polygon();
				cellVisual.getPoints().addAll(tempHex.getPointsArray());
				Cell cell = myGrid.getCellAtLocation(r, c);
				cellVisual.setFill(cell.getCurrentState().getStateColor());
				if (isOutlined){
					cellVisual.setStroke(Color.BLACK);
				}
				myGroup.getChildren().add(cellVisual);
				xPos += xStep;
			}
			yPos += yStep;
			if (r % 2 == 0){
				xPos = x + xStep;
			}
			else{
				xPos = x + xStep / 2;
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
