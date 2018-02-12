package backendClasses.gametype;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import backendClasses.Cell;
import backendClasses.State;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
/**
 * @author shichengrao
 * This class models the rock paper scissors on a grid
 * It inherits from GameType and depends on cell and state
 */
public class RPSGameType implements GameType {
	public static final int ROCK = 0;
	public static final int PAPER = 1;
	public static final int SCISSORS = 2;
	private Map<Integer, State> myValues = new HashMap<Integer, State>() {{
		
	}};
	/**
	 * puts the three states into the myValues map
	 */
	public RPSGameType() {
		myValues.put(ROCK, new State(ROCK, Color.GREEN, "ROCK",this));
		myValues.put(PAPER, new State(PAPER, Color.BLUE,"PAPER",this));
		myValues.put(SCISSORS, new State(SCISSORS, Color.RED,"SCISSORS",this));
	}
	/**
	 * The cell's next state should be itself, as this is purely movement based
	 */
	@Override
	public State determineState(Cell currentCell, List<Cell> Neighbors) {
		return currentCell.getNextState();
	}
	/**
	 * the cell should beat a cell. If it loses/ ties with every cell around it, don't do anything
	 */
	@Override
	public Point2D determinePosition(Cell currentCell, List<Cell> neighbors, int x, int y, Cell[][] grid,int[] xvals, int[] yvals, boolean toroidal) {
		for(Cell cell: neighbors) {
			if(win(currentCell, cell)) {
				return randomMoveToSpecifiedType(grid, x, y, cell.getCurrentState(),xvals, yvals, toroidal);
			}
		}
		return null;
	}
	/**
	 * moves to a nearby cell of a specified type
	 * @param grid
	 * @param x
	 * @param y
	 * @param wantedState
	 * @param xvals
	 * @param yvals
	 * @param toroidal
	 * @return the point which contains the location of the nearby specified state
	 * important assumption: this method is only called when there is a nearby cell with the specified state, otherwise it goes on infinitely
	 */
	private Point2D randomMoveToSpecifiedType(Cell[][] grid, int x, int y, State wantedState, int[] xvals, int[] yvals, boolean toroidal) {
		while(true) {
			int rand = (int)(Math.random() * xvals.length);
			int xdelta = xvals[rand];
			int ydelta = yvals[rand];
			
			int newX = x + xdelta ;
			int newY =y + ydelta ;
			if(toroidal) {
				newX = (newX + grid.length)%grid.length;
				newY = (newY + grid[0].length)%grid[0].length;
			}
			if(newX < 0 || newY<0 || newX >= grid.length || newY >= grid[0].length) {
				continue;
			}
			if(grid[newX][newY].getCurrentState().equals(wantedState)) {
				return new Point2D(newX,newY);
			}
		}
	}
	/**
	 * checks if cell1 beats cell 2
	 * @param cell1
	 * @param cell2
	 * @return whether or not cell1 beats cell 2
	 */
	public boolean win(Cell cell1, Cell cell2) {
		return ((cell1.getCurrentState().getStateId() - cell2.getCurrentState().getStateId()) + myValues.size())%myValues.size() == 1;
	}
	/**
	 * returns myValues, which in this case is rock, paper, and scissors
	 */
	@Override
	public List<State> getMyValues() {
		return new ArrayList<> (myValues.values());
	}
	/**
	 * to grow, cells need to stay and move to the other cell, so it shouldn't be replaced with an empty cell
	 */
	@Override
	public boolean replaceWithEmpty(Cell cell, int time) {
		return false;
	}

}
