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
 * This class models the spreading of fire through trees
 * It inherits from GameType and depends on cell and state
 * Use it to model firespread
 * Users can change speed of fire spread by controlling the probability of a tree catching on fire given a fire near it
 */
public class FireGameType implements GameType{
	private double probCatch;
	public static final int EMPTY = 0;
	public static final int TREE = 1;
	public static final int BURNING = 2;
	private Map<Integer, State> myValues = new HashMap<Integer, State>();

	/**
	 * puts the three states into the myValues map, sets fire probability
	 */
	public FireGameType(double prob) {
		probCatch = prob;
		myValues.put(EMPTY, new State(EMPTY, Color.WHITE, "EMPTY",this));
		myValues.put(TREE, new State(TREE, Color.GREEN, "TREE",this));
		myValues.put(BURNING, new State(BURNING, Color.RED, "BURNING",this));
	}
	/**
	 * sets probability of a tree catching a fire given a fire near it
	 * @param prob
	 */
	public void setprobCatch(double prob) {
		probCatch = prob;
	}
	/**
	 * If a cell is burning or empty, it should become empty. If a cell is a tree, it should stay a tree always if not next to a fire, and with some probability if next to a fire
	 */
	@Override
	public State determineState(Cell currentCell, List<Cell> Neighbors) {
		if(currentCell.getCurrentState().getStateId() < EMPTY || currentCell.getCurrentState().getStateId() > BURNING) {
			throw new IncorrectGameTypeException("Cell has invalid state; not in fire simulation");
		}
		if(currentCell.getCurrentState().getStateId() != TREE) {
			return myValues.get(EMPTY);
		}
		else {
			for(int i = 0; i < Neighbors.size(); i++) {
				if(Neighbors.get(i).getCurrentState().getStateId() == BURNING) {
					double threshold = Math.random();
					if(threshold < probCatch) {
						return myValues.get(BURNING);
					}
				}
			}
			return myValues.get(TREE);
		}
	}
	/**
	 * Fire does not involve individual mobility, so determinePosition returns the original position
	 */
	@Override
	public Point2D determinePosition(Cell currentCell, List<Cell> Neighbors, int x, int y,Cell[][] grid, int[] xvals, int[] yvals, boolean toroidal) {
		return new Point2D(x,y);
	}
	/**
	 * returns myValues, which in this case is an burning tree, a normal tree, and an empty tile
	 */
	public List<State> getMyValues() {
		return new ArrayList<>(myValues.values());
	}
	/**
	 * a nonfactor, due to this gameType's lack of mobility
	 */
	public boolean replaceWithEmpty(Cell cell, int time) {
		return true;
	}
}
