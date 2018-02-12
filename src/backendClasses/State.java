package backendClasses;

import backendClasses.gametype.GameType;
import javafx.scene.paint.Color;
/**
 * @author shichengrao
 * 
 * The purpose of this class is to model a state of a cell
 * This class depends on javafx's color
 */
public class State {
    private int myID;
    private Color myColor;
    private String myName;
    private GameType game;
    /**
     * makes a new state, with a unique id for the gametype, and a color
     * @param id
     * @param color
     * @param name
     * @param g
     */
    public State(int id, Color color, String name, GameType g) {
        myName = name;
        myID = id;
        myColor = color;
        game = g;
    }
    /**
     * gets the id of the state
     * @return
     */
    public int getStateId() {
        return myID;
    }
    /**
     * gets the color of the state
     * @return
     */
    public Color getStateColor() {
        return myColor;
    }
    /**
     * gets the name of the state
     * @return
     */
    public String getName() {
    		return myName;
    }
    /**
     * gets the gametype of the state
     * @return
     */
    public GameType getMyGameType() {
    		return game;
    }
}
