package resources;

import java.util.ResourceBundle;


/**
 * Class to keep track of necessary resource strings for the program
 */

public class Resources {

    public static final ResourceBundle RESOURCEKEYS = ResourceBundle.getBundle("resources/resourceKeys");

    public static final String SIMULATION_TYPE = "simulationType";
    public static final String SIMULATION_NAME = "simulationName";
    public static final String INPUT_TYPE = "inputType";
    public static final String PARAM1 = "param1";
    public static final String PARAM2 = "param2";
    public static final String PARAM3 = "param3";
    public static final String NUM_ROWS = "numRows";
    public static final String NUM_COL = "numCols";

    public static final String CONWAY = "CONWAY";
    public static final String SEGREGATION = "SEGREGATION";
    public static final String FIRE = "FIRE";
    public static final String PRED_PREY = "PRED_PREY";
    public static final String RPS = "RPS";

    public static final String PROBABILITY = "PROBABILITY";
    public static final String SPECIFIC = "SPECIFIC";
    public static final String RANDOM = "RANDOM";

    public static final String XML_FILE_PATH = "src/resources/mySavedSimulation.xml";

    private Resources(){
    }

    public static String getString(String key) {
        return RESOURCEKEYS.getString(key);
    }

    public static int getInt(String key){
        return Integer.parseInt(RESOURCEKEYS.getString(key));
    }



}
