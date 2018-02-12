package reader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Random;

import alerts.Alerts;
import backendClasses.gametype.*;
import resources.Resources;
import backendClasses.grid.Grid;
import backendClasses.State;
import backendClasses.Cell;
import org.w3c.dom.Document;


/**
 * @author Alexi Kontos
 */

/**
 * Class created to read in XML files, and take the relevant values to initialize the first grid for each rule set
 * so that the simulation can be run.
 */
public class Reader {

    private XMLParser myParser;
    private String simulationType;
    private String inputType;
    private String simulationName;
    private int rows;
    private int columns;
    private double param1;
    private double param2;
    private double param3;
    private GameType myGameType;
    private HashMap<Integer, State> stateMap = new HashMap<>();
    private Document myDoc;
    private XMLBuilder myBuilder = new XMLBuilder(myDoc);


    /**
     * Constructor to create the Reader that will get all of the
     * relevant values from the given XML file
     * @param file
     */
    public Reader(File file) {
        myParser = new XMLParser(file);
        simulationType = myParser.getTextFromXML(Resources.SIMULATION_TYPE).trim();
        simulationName = myParser.getTextFromXML(Resources.SIMULATION_NAME).trim();
        inputType = myParser.getTextFromXML(Resources.INPUT_TYPE).trim();
        rows = Integer.valueOf(myParser.getTextFromXML(Resources.NUM_ROWS).trim());
        columns = Integer.valueOf(myParser.getTextFromXML(Resources.NUM_COL).trim());
        param1 = Double.valueOf(myParser.getTextFromXML(Resources.PARAM1).trim());
        param2 = Double.valueOf(myParser.getTextFromXML(Resources.PARAM2).trim());
        param3 = Double.valueOf(myParser.getTextFromXML(Resources.PARAM3).trim());
        setGameType(simulationType, param1, param2, param3);
    }

    /**
     * Method to set the game type for the simulation to acquire the proper rules for the simulation
     * @param type
     * @param param1
     * @param param2
     * @param param3
     */
    private void setGameType(String type, double param1, double param2, double param3) {
        switch (simulationType) {
            case Resources.FIRE:
                myGameType = new FireGameType(param1);
                break;
            case Resources.CONWAY:
                myGameType = new ConwayGameType();
                break;
            case Resources.PRED_PREY:
                myGameType = new PredPreyGameType(param1, param2, param3);
                break;
            case Resources.SEGREGATION:
                myGameType = new SegregationGameType(param1);
                break;
            case Resources.RPS:
                myGameType = new RPSGameType();
                break;
            default:
                Alerts.XMLWrongSimulationType(new XMLException(Resources.getString("InvalidSimulationError")));
                throw new XMLException(Resources.getString("InvalidSimulationError"));
        }
    }

    /**
     * Method to return the number of rows specified in the XML file
     * @return
     */
    public int getRows(){
        return rows;
    }

    /**
     * Method to return the number of columns specified in the XML file
     * @return
     */
    public int getColumns(){
        return columns;
    }

    /**
     * Method to return the Gametype to other classes in order to keep consistency with the rules
     * @return
     */
    public GameType getGameType() {
        return myGameType;
    }

    /**
     * Method to initialize the map of states, then create the distribution necessary for the probability input type
     * then populates the initial grid with the correct cell states
     * @param myGrid
     */
    public void initializeGrid(Grid myGrid) {
        switch (simulationType) {
            case Resources.CONWAY:
                for (State state : myGameType.getMyValues()) {
                    stateMap.put(state.getStateId(), state);
                }
                break;
            case Resources.FIRE:
                for (State state : myGameType.getMyValues()) {
                    stateMap.put(state.getStateId(), state);
                }
                break;
            case Resources.PRED_PREY:
                for (State state : myGameType.getMyValues()) {
                    stateMap.put(state.getStateId(), state);
                }
                break;
            case Resources.SEGREGATION:
                for (State state : myGameType.getMyValues()) {
                    stateMap.put(state.getStateId(), state);
                }
                break;
            case Resources.RPS:
                for (State state : myGameType.getMyValues()) {
                    stateMap.put(state.getStateId(), state);
                }
                break;
            default:
                Alerts.XMLWrongSimulationType(new XMLException(Resources.getString("InvalidSimulationError")));
                throw new XMLException(Resources.getString("InvalidSimulationError"));
        }
        List<Double> distribution = generateDistribution();
        populateCells(myGrid, inputType, distribution);
    }

    /**
     * Method that iterates through the grid each cell at a time, initializing the proper cell
     * states specified in the XML file
     * @param myGrid
     * @param inputType
     * @param distribution
     */
    private void populateCells(Grid myGrid, String inputType, List<Double> distribution) {
        for (int row = 0; row < getRows(); row++) {
            for (int col = 0; col < getColumns(); col++) {
                try {
                    Cell newCell = null;
                    switch (inputType) {
                        case Resources.RANDOM:
                            Random rand = new Random();
                            newCell = new Cell(stateMap.get(rand.nextInt(stateMap.size())));
                            break;
                        case Resources.PROBABILITY:
                            double rand1 = Math.random();
                            for (int distCheck = 0; distCheck < stateMap.size(); distCheck++) {
                                if (rand1 <= distribution.get(distCheck)) {
                                    newCell = new Cell (stateMap.get(distCheck));
                                    break;
                                }
                            }
                            break;
                        case Resources.SPECIFIC:
                            if (stateMap.get(Character.getNumericValue(myParser.getTextFromXML(String.format("row%d", row)).charAt(col))) == null) {
                                Alerts.InvalidStateValue(new XMLException(String.format(Resources.getString("InvalidStateValue"))));
                                throw new XMLException(String.format(Resources.getString("InvalidStateValue")));
                            }
                            newCell = new Cell(stateMap.get(Character.getNumericValue(myParser.getTextFromXML(String.format("row%d", row)).charAt(col))));
                            break;
                        default:
                            Alerts.XMLWrongInputType(new XMLException(String.format(Resources.getString("InvalidInputTypeMessage"))));
                            throw new XMLException(String.format(Resources.getString("InvalidInputTypeMessage")));
                    }
                    myGrid.setCell(row,col,newCell);
                }
                catch (StringIndexOutOfBoundsException e) {
                    Alerts.CellDataError(new StringIndexOutOfBoundsException(String.format(Resources.getString("InvalidCellDataMessage"))));
                    throw new StringIndexOutOfBoundsException(String.format(Resources.getString("InvalidCellDataMessage")));
                }
            }
        }
    }

    /**
     * Method to create a distribution to select the different cell states
     * @return
     */
    private List<Double> generateDistribution(){
        List<Double> distribution = new ArrayList<>();
        if (inputType.equals(Resources.PROBABILITY)) {
            try {
                double current = 0.0;
                for (int x = 0; x < stateMap.size(); x++) {
                    current += Double.parseDouble(myParser.getTextFromXML(String.format("state%d", x)));
                    distribution.add(current);
                }
                if (current != 1.0) {
                    throw new XMLException("State distribution does not equal 1.0");
                }

            }
            catch (XMLException e) {
                Alerts.XMLError(e, myParser.getFile());
            }
        }
        return distribution;
    }

    /**
     * Method that will iterate through the grid, save the current states and
     * build a new XML file that can be re-loaded from the drop down menu.
     * @param myGrid
     */
    public void saveXML(Grid myGrid) {
        List<String> states = new ArrayList<>();
        for (int row = 0; row < rows; row++) {
            StringBuilder rowData = new StringBuilder("");
            for (int col = 0; col < columns; col++) {
                rowData.append(Integer.toString(myGrid.getCellAtLocation(row, col).getCurrentState().getStateId()));
            }
            states.add(rowData.toString());
        }
        myBuilder.buildXML(simulationType, simulationName, rows, columns, param1, param2, param3, states);
    }


}
