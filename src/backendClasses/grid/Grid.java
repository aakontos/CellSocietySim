package backendClasses.grid;

import backendClasses.Cell;

import java.util.List;
/**
 * @author shichengrao
 * 
 * The purpose of this interface is to represent the grid that the simulation runs on
 * It should be extended to make new grids, 
 * This interface depends on Cell (which depends on State), and java class List 
 * To extend it, list possible neighbors, and figure out a coordinate system
 * It currently contains all single regular polygon tilings, but could easily be extended to tessalations
 * Looks through all of the cells in the grid, calls updateGrid(), then calls
 * determineState on each cell to get the next state, and
 * then will call setNextState() on each cell to
 * update the entire grid
 */
public interface Grid {


    /**
     * loops through the grid and sets the new states of all the cells according the ruleset given to it
     */
    public void updateGrid();
    /**
     * checks if a tile is valid (both bounds check and cell check, although cell check currently doesn't matter)
     * @param x
     * @param y
     * @return whether the cell at x,y is valid
     */
    public boolean validTile(int x, int y);
    /**
     * 
     * @param xdeltas
     * @param ydeltas
     * @param x
     * @param y
     * @return gets all cells that are a certain x,y away from a given point
     */
    public List<Cell> getCells(int[] xdeltas, int[] ydeltas, int x, int y);
    /** 
     * sets a cell at a position
     * @param x
     * @param y
     * @param cell
     */
    public void setCell(int x, int y, Cell cell);
    /**
     * gets the cell at a position
     * @param x
     * @param y
     * @return the cell at x,y
     */
    public Cell getCellAtLocation(int x, int y);
    /** 
     * bounds check
     * @param x
     * @param y
     * @return if the x,y is a valid position in the grid
     */
    public boolean outOfBounds(int x, int y);
    /** 
     * @return a list of all states and the number of each state
     */
    public List<Integer> numOfEach();
    /** 
     * changes the grid from finite to toroidal, and vice versa
     */
    public void toggleToroidal();
    /** 
     * changes neighborhood
     */
    public void changeNeighbors(int newNeighbor);
}

