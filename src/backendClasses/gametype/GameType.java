package backendClasses.gametype;
import java.util.List;

import backendClasses.Cell;
import backendClasses.State;
import javafx.geometry.Point2D;
/**
 * @author shichengrao
 * 
 * The purpose of this interface is to compactly keep a ruleset for a game. 
 * It should be extended to make new games
 * It does have a few assumptions, namely that determineState and determinePosition can apply the necessary rules. If a single cell can affect multiple cells in a way those cells cannot detect, issues occur.
 * This does not mean multi effects cannot occur though, as other cells can detect their own changes and surroundings.
 * This interface depends on Cell (which depends on State), and java classes List and Point2D
 * To use it, list all possible states in myValues, and then write the necessary rule set for determining the correct next state and the necessary rule set for the correct next position.
 * 
 */


public interface GameType{

    /**
     * Look at the neighbors and take into account the rules
     * of the given rule set to determine the next state
     * of the cell
     * Assumes cell is not null
     * @param currentCell
     * @param neighbors
     * @return nextState
     */
    public State determineState(Cell currentCell, List<Cell> Neighbors);
    /**
     * Look at the neighbors and take into account the rules
     * of the given rule set to determine the next position
     * of the cell
     * This takes more parameters, as moving requires looking at the rest of the grid
     * Assumes cell is not null
     * @param currentCell
     * @param neighbors
     * @param x,y for current position
     * @param grid
     * @param xdeltas, ydeltas for neighbor positions
     * @param toroidal to know whether or not to wrap around the grid
     * @return Point2D, containing an x and a y position for it next position
     */
    public Point2D determinePosition(Cell currentCell, List<Cell> Neighbors ,int x, int y, Cell[][] grid, int[] xdeltas, int[] ydeltas,boolean toroidal);
    /**
     *return the list of possible states for this gametype
     * @return List of states
     */
    public List<State> getMyValues();
    /**
     *decides whether or not a cell should be "destroyed" given the time. This allows for interaction with the cycle number.
     * @param cell
     * @param time
     * @return boolean stating whether to replace the cell with an empty one of the same gametype
     */
	public boolean replaceWithEmpty(Cell cell, int time);

}