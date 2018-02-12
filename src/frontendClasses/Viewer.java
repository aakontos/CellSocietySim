package frontendClasses;

import javafx.scene.Group;
import javafx.scene.paint.Color;

/**
 * @author Jennifer Chin
 *
 * Interface for Viewer classes. Requires that all Viewer class must implement initGrid() and changeLines(). Is
 * implemented by SquareViewer, HexViewer, and TriangleViewer. Methods are public so that they can be called by the
 * CellSociety class. 
 */

public interface Viewer {
	
	/**
	 * Creates the visualization of the Grid. This method iterates over the Grid to create a Shape visualization for
	 * each Cell. This method determines the position of each Shape and the color of each Shape depending on the State
	 * of the corresponding Cell in the Grid. The cell visualizations are then added to the Group so that they can be
	 * displayed by the CellSociety class. 
	 * @param x
	 * @param y
	 * @param gridWidth
	 * @param gridHeight
	 * @param gridRows
	 * @param gridCols
	 * @return Group
	 */
	
	public Group initGrid(int x, int y, int gridWidth, int gridHeight, int gridRows, int gridCols);
	
	/**
	 * Method that determines whether the Viewer should be created with grid lines or not
	 */
	
	public void changeLines();

}
