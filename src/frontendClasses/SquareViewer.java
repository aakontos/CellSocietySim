package frontendClasses;

import backendClasses.Cell;
import backendClasses.grid.Grid;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @author Jennifer Chin
 * 
 * Implements the Viewer interface. Class created to viewer a square grid. Creates a Rectangle object to represent 
 * each cell of the grid and colors the object accordingly.
 */

public class SquareViewer implements Viewer {
	
	private Group myGroup;
	private Grid myGrid;
	private boolean isOutlined = true;
	
	/**
	 * Constructor for a SquareViewer. Takes in a group, that the Polygons will be added to, and a grid, which will be 
	 * used to determine the color of the Polygons.
	 * @param group
	 * @param grid
	 */

	public SquareViewer(Group group, Grid grid){
		myGroup = group;
		myGrid = grid;
	}
	
	/**
	 * Visualizes a square grid. Takes in the x and y position of the upper left corner of the grid, the grid width, 
	 * grid height, number of grid rows, and number of column rows. Iterates over the Grid to create a Rectangle for 
	 * each cell. Uses the parameters to calculate the x and y position of the top left corner of each Rectangle, 
	 * whose fill is determined by the State of its corresponding Cell in the Grid. All cell visuals are then added 
	 * to the Group.
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
		double xStep = (double) gridWidth / (double) gridRows;
		double yStep = (double) gridHeight / (double) gridCols;
		double xPos = (double) x;
		double yPos = (double) y;
		for (int r = 0; r < gridRows; r++){
			for (int c = 0; c < gridCols; c++){
				Rectangle cellVisual = new Rectangle(xPos, yPos, xStep, yStep);
				Cell cell = myGrid.getCellAtLocation(r, c);
				cellVisual.setFill(cell.getCurrentState().getStateColor());
				if (isOutlined){
					cellVisual.setStroke(Color.BLACK);
				}
				myGroup.getChildren().add(cellVisual);
				xPos += xStep;
			}
			yPos += yStep;
			xPos = x;
		}
		return myGroup;
	}
	
	/**
	 * Method that determines whether the Viewer should be created with grid lines or not
	 */
	
	@Override
	public void changeLines(){
		isOutlined = !isOutlined;
	}

}
