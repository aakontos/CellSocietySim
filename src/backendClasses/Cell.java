package backendClasses;

import java.util.List;
/**
 * @author shichengrao
 * 
 * The purpose of this class is to model a cell
 * It should be extended to make new cells
 * It does have a few assumptions, namely that state cannot be affected by a state more than 1 cycle away
 * This class depends on State and the java class list
 * To extend it, extend behavior and add new behavior
 * 
 */
public class Cell{
	private State currentState;
	private State nextState;
	private boolean valid = false;
	public Cell() {
		
	}
	/**
	 * makes a valid cell with initialstate on both current and next
	 * @param initialState
	 */
	public Cell(State initialState) {
		currentState = initialState;
		nextState = initialState;
		valid = true;
	}
	/**
	 * @return the current state
	 */
    public State getCurrentState() {
    		return currentState;
    }
    /**
	 * @return the next state
	 */
    public State getNextState() {
    		return nextState;
    }
    
    /**
    * Sets cell's current state to next state 
    */
    public void setNextState(State nextState) {
    		this.nextState = nextState;
    }
 

   /**
    * Transitions the current state to next state
    */
    public void updateCell() {
    		currentState = nextState;
    }
    /**
     * @return if the cell is valid (always true for now, possibly not for later uses)
     */
    public boolean valid() {
    		return valid;
    }
    /**
	 * @return the "next state" of the cell
	 */
    public void increment(){
    		List<State> values = currentState.getMyGameType().getMyValues();
    		currentState = values.get((currentState.getStateId() + 1)% values.size());
    }
}
