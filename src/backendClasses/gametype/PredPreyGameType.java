package backendClasses.gametype;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import backendClasses.Cell;
import backendClasses.Shark;
import backendClasses.State;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
/**
 * @author shichengrao
 * This class models the wa-tor world
 * It inherits from GameType and depends on cell and state and shark
 * Users can change the reproduction time of both fish and sharks, as well as the death timer on sharks
 */
public class PredPreyGameType implements GameType {
	double fishReproduction;
	double sharkReproduction;
	double sharkEnergy;
	public static final int EMPTY = 0;
	public static final int FISH = 1;
	public static final int SHARK = 2;
	private Map<Integer, State> myValues = new HashMap<Integer, State>();
	/**
	 * puts the three states into the myValues map, sets the two reproduction times and the shark death time
	 */
	public PredPreyGameType(double fishTime, double sharkLife, double sharkTime) {
		fishReproduction = fishTime;
		sharkEnergy = sharkLife;
		sharkReproduction = sharkTime;
		myValues.put(EMPTY, new State(EMPTY, Color.WHITE, "EMPTY",this));
		myValues.put(FISH, new State(FISH, Color.BLUE,"FISH",this));
		myValues.put(SHARK, new Shark(SHARK, Color.GRAY, "SHARK", (int)sharkLife, (int)sharkTime,this));
		
	}
	/**
	 * So a hierarchy happens of shark>fish>empty, and the next state reflects this. With the caveat that a shark moving decrements its time left, unless it eats a fish, where the level gets reset
	 */
	@Override
	public State determineState(Cell currentCell, List<Cell> Neighbors) {
		if(currentCell.getCurrentState().equals(myValues.get(EMPTY))) {
			return currentCell.getNextState();
		}
		if(currentCell.getCurrentState().equals(myValues.get(FISH)) && currentCell.getNextState().equals(myValues.get(FISH))) {
			return currentCell.getCurrentState();
		}
		if(currentCell.getCurrentState().getStateId() == myValues.get(SHARK).getStateId()&&currentCell.getNextState().getStateId() == myValues.get(SHARK).getStateId()) {
			if(((Shark) currentCell.getCurrentState()).getDTime() == 0) {
				return myValues.get(EMPTY);
			}
			State state = currentCell.getCurrentState();
			return new Shark(state.getStateId(), state.getStateColor(), state.getName(), ((Shark) state).getRTime(),((Shark) state).getDTime() - 1,this);
		}
		if(currentCell.getNextState().getStateId() == myValues.get(SHARK).getStateId()&&currentCell.getCurrentState().equals(myValues.get(FISH))){
			return myValues.get(SHARK);
		}
		return currentCell.getCurrentState();
	}
	/**
	 * returns myValues, which in this case is a shark, a fish, and an empty tile
	 */
	@Override
	public List<State> getMyValues() {
		return new ArrayList<>(myValues.values());
	}
	/**
	 * determines the position, which for shark is the fish > empty tile > no movement, and for a fish is an empty tile > no movement
	 */
	@Override
	public Point2D determinePosition(Cell currentCell, List<Cell> Neighbors, int x, int y, Cell[][]grid, int[] xvals, int[] yvals, boolean toroidal) {
		if(!currentCell.getCurrentState().equals(myValues.get(EMPTY))) {
			if(currentCell.getCurrentState().equals(myValues.get(FISH))) {
				return updateFish(Neighbors, grid, x, y,xvals, yvals,toroidal);
			}
			else {
				return updateShark(Neighbors, grid, x, y,xvals, yvals,toroidal);
			}
		}
		return null;
	}
	/**
	 * finds if there's an empty tile nearby
	 * @param cells
	 * @return whether there's an empty tile nearby
	 */
	private boolean checkEmpty(List<Cell> cells) {
		for(Cell cell: cells) {
			if(cell.getNextState().equals(myValues.get(EMPTY))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * finds if there's a fish tile nearby
	 * @param cells
	 * @return whether there's a fish tile nearby
	 */
	private boolean checkFish(List<Cell> cells) {
		for(Cell cell: cells) {
			if(cell.getNextState().equals(myValues.get(FISH))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * there should only be a reproduction if its on a reproductive cycle
	 */
	public boolean replaceWithEmpty(Cell cell, int time) {
		return !((cell.getCurrentState().equals(myValues.get(FISH))&&(time % (int)fishReproduction == 0)) || (cell.getCurrentState().getStateId() == myValues.get(SHARK).getStateId()&&time % (int)sharkReproduction == 0));
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
			int newX =x + xdelta;
			int newY =y + ydelta ;
			if(toroidal) {
				newX = (newX + grid.length)%grid.length;
				newY = (newY + grid[0].length)%grid[0].length;
			}
			if(newX < 0 || newY<0 || newX >= grid.length || newY >= grid[0].length) {
				continue;
			}
			
			if(grid[newX][newY].getNextState().equals(wantedState)) {
				return new Point2D(newX,newY);
			}
		}
	}
	/**
	 * helper method for just fish
	 * @param Neighbors
	 * @param grid
	 * @param x
	 * @param y
	 * @param xvals
	 * @param yvals
	 * @param toroidal
	 * @return where the fish should go (random empty cell or stay in place)
	 */
	public Point2D updateFish(List<Cell> Neighbors, Cell[][]grid, int x, int y,int[]xvals, int[] yvals,boolean toroidal) {
		if(checkEmpty(Neighbors) ) {
			return randomMoveToSpecifiedType(grid, x, y, myValues.get(EMPTY),xvals,yvals,toroidal);
		}
		return null;
	}
	/**
	 * helper method for just sharks
	 * @param Neighbors
	 * @param grid
	 * @param x
	 * @param y
	 * @param xvals
	 * @param yvals
	 * @param toroidal
	 * @return where the shark should go (random fish or random empty cell or stay in place)
	 */
	public Point2D updateShark(List<Cell> Neighbors, Cell[][]grid, int x, int y,int[]xvals, int[] yvals,boolean toroidal) {
		if(checkFish(Neighbors) ) {
			return randomMoveToSpecifiedType(grid, x, y, myValues.get(FISH),xvals,yvals,toroidal);
		}
		if(checkEmpty(Neighbors) ) {
			return randomMoveToSpecifiedType(grid, x, y, myValues.get(EMPTY),xvals,yvals,toroidal);
		}
		return null;
	}
	
}
