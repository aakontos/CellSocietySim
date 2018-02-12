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
 * This class models the segregation of agents
 * It inherits from GameType and depends on cell and state
 * Users can change the pickiness of the agents by adjusting the threshold
 * important assumption: there is at least one empty square, for agent mobility
 */
public class SegregationGameType implements GameType {
	double satisfaction;
	public static final int EMPTY = 0;
	public static final int AGENTX = 1;
	public static final int AGENTY = 2;
	private Map<Integer, State> myValues = new HashMap<Integer, State>();
	/**
	 * puts the three states into the myValues map, sets the threshold for movement
	 */
	public SegregationGameType(double threshold) {
		satisfaction = threshold;
		myValues.put(EMPTY, new State(EMPTY, Color.WHITE, "EMPTY",this));
		myValues.put(AGENTX, new State(AGENTX, Color.BLUE,"AGENTX",this));
		myValues.put(AGENTY, new State(AGENTY, Color.RED, "AGENTY",this));
	}
	/**
	 * The cell's next state should be itself, unless its an empty cell, where it should not do anything (aka take the next state)
	 */
	@Override
	public State determineState(Cell currentCell, List<Cell> Neighbors) {
		if(currentCell.getCurrentState().equals(myValues.get(EMPTY))) {
			return currentCell.getNextState();
		}	
		return currentCell.getCurrentState();
	}
	@Override
	public List<State> getMyValues() {
		return new ArrayList<>(myValues.values());
	}
	/**
	 * If a cell is next to too many foreign agents, it moves to a random empty spot.
	 * To many foreign agents is defined as the relative percentage of similar agents nearby being less than the threshold
	 */
	@Override
	public Point2D determinePosition(Cell currentCell, List<Cell> Neighbors, int x,int y, Cell[][]grid, int[] xvals, int[] yvals, boolean toroidal) {
		if(!currentCell.getCurrentState().equals(myValues.get(EMPTY))) {
			double currentSatisfaction = 1;
			int similar = 0;
			int different = 0;
			similar += numSimilar(currentCell, Neighbors);
			different += numDifferent(currentCell, Neighbors);
			if((similar + different) != 0) {
				currentSatisfaction = similar / (double) (similar + different);
			}
			if(currentSatisfaction < satisfaction) {
				return randomEmptyPlace(grid);
			}
		}
		return null;
	}
	/**
	 * The agent moves away and does not leave a copy behind
	 */
	public boolean replaceWithEmpty(Cell cell, int time) {
		return true;
	}
	/**
	 * helper method for determining the number of similar agents around an agent
	 * @param givenCell
	 * @param cells
	 * @return number of similar agents in the list given
	 */
	public int numSimilar(Cell givenCell, List<Cell> cells) {
		int result = 0;
		for(Cell cell: cells) {
				if(cell.getCurrentState().equals(givenCell.getCurrentState())) {
					result++;
				}
		}
		return result;
	}
	/**
	 * helper method for determining the number of different agents around an agent
	 * @param givenCell
	 * @param cells
	 * @return number of different agents in the list given
	 */
	public int numDifferent(Cell givenCell, List<Cell> cells) {
		int result = 0;
		for(Cell cell: cells) {
				if(!cell.getCurrentState().equals(givenCell.getCurrentState())&& !cell.getCurrentState().equals(myValues.get(EMPTY))) {
					result++;
			}
		}
		return result;
	}
	/**
	 * finds an empty spot on the board
	 * @param grid
	 * @return point containing the x and y of an empty spot
	 */
	public Point2D randomEmptyPlace(Cell[][] grid) {
		while(true) {
			int randomRow = (int) (Math.random() * grid.length);
			int randomCol = (int) (Math.random() * grid[0].length);
			if(grid[randomRow][randomCol].getNextState().equals(myValues.get(EMPTY))){
				return new Point2D(randomRow,randomCol);
			}
		}
	}
}
