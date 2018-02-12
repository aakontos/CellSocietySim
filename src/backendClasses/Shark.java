package backendClasses;

import backendClasses.gametype.GameType;
import javafx.scene.paint.Color;
/**
 * @author shichengrao
 * 
 * The purpose of this class is to model a shark
 * This class depends on class, which depends on state
 */
public class Shark extends State {

	private int reproductionTime = 0;
	private int deathTime = 0;
	/**
	 * makes a valid shark with initialstate on both current and next 
	 * also involves reproduction time and death time
	 * @param initialState
	 */
	public Shark(int id, Color color, String name, int rTime, int dTime, GameType g) {
		super(id, color, name,g);
		reproductionTime = rTime;
		deathTime = dTime;
	}
	/**
	 * sets reproduction time
	 * @param rTime
	 */
	public void setRTime(int rTime) {
		reproductionTime = rTime;
	}
	/**
	 * sets death time
	 * @param dTime
	 */
	public void setDTime(int dTime) {
		deathTime = dTime;
	}
	/**
	 * get death time
	 * @return
	 */
	public int getDTime() {
		return deathTime;
	}
	/** 
	 * get reproduction time
	 * @return
	 */
	public int getRTime() {
		return reproductionTime;
	}
}
