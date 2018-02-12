package backendClasses.gametype;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import backendClasses.Cell;
import backendClasses.IncorrectGameTypeException;
import backendClasses.State;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import java.util.ArrayList;
/**
 * @author shichengrao
 * This class models Conway's game of life
 * It inherits from GameType and depends on cell and state
 * Use it to model a conway's game of life game
 * It is flexible to allow differing variations of "life-like games", by changing B/S
 */
public class ConwayGameType implements GameType{
	public static final int DEAD = 0;
	public static final int ALIVE = 1;
	private static final int SURVIVAL = 2;
	private static final int OPTIMAL = 3;
	private Map<Integer, State> myValues = new HashMap<Integer, State>(); 
	/**
	 * puts the two states into the myValues map
	 */
	public ConwayGameType() {
		myValues.put(DEAD, new State(DEAD, Color.WHITE, "DEAD",this));
		myValues.put(ALIVE, new State(ALIVE, Color.BLACK, "ALIVE",this));
	}
	/**
	 * Returns alive if there are 3 alive neighbors, the current state is 2 alive neighbors, dead otherwise
	 */
	@Override
	public State determineState(Cell currentCell, List<Cell> Neighbors) {
		int numAliveNeighbors = 0;
		for(Cell cell: Neighbors) {
			if(cell.getCurrentState().getStateId() == ALIVE) {
				numAliveNeighbors++;
			}
		}
		return numAliveNeighbors == OPTIMAL || (numAliveNeighbors == SURVIVAL && currentCell.getCurrentState().getStateId() == ALIVE)? myValues.get(ALIVE):myValues.get(DEAD);
	}
	/**
	 * Conway's game of life does not involve individual mobility, so determinePosition returns the current position
	 */
	@Override
	public Point2D determinePosition(Cell currentCell, List<Cell> Neighbors, int x, int y, Cell[][] grid, int[] xvals, int[] yvals,boolean toroidal) {
		return new Point2D(x,y);
	}
	/**
	 * returns myValues, which in this case is an alive and a dead cell
	 */
	@Override
	public List<State> getMyValues() {
		return new ArrayList<>(myValues.values());
	}
	/**
	 * A non factor for conway, due to lack of mobility
	 */
	public boolean replaceWithEmpty(Cell cell, int time) {
		return true;
	}
}
