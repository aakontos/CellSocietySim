package frontendClasses;

import backendClasses.gametype.GameType;
import resources.Resources;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.scene.Scene;
import backendClasses.grid.*;
import java.util.List;
import backendClasses.State;

/**
 * @author Alexi Kontos
 */

/**
 * Class to create a chart of the populations in each simulation, to be updated in real time as the simulation runs
 */

public class StateChart {


    private NumberAxis xAxis = new NumberAxis();
    private CategoryAxis yAxis = new CategoryAxis();
    private BarChart<Number, String> bc = new BarChart<>(xAxis, yAxis);
    private Grid myGrid;
    private GameType myGameType;

    /**
     * Constructor for the StateChart, initializes the constant values that won't change per simulation
     * @param gr
     * @param gt
     */
    public StateChart (Grid gr, GameType gt) {
        xAxis.setLabel(Resources.getString("Population"));
        yAxis.setLabel(Resources.getString("StateType"));
        xAxis.setTickLabelRotation(90);
        bc.setTitle(Resources.getString("ChartTitle"));
        myGrid = gr;
        myGameType = gt;
    }

    /**
     * Method to update the data in the grid per each step. Each time it is called the number of each state in the simulation,
     * as well as the values for each state are updated in case of a switch in gameType. The data is also cleared and reset so
     * no duplicate data is added.
     */
    public void addData() {
        List<Integer> numOfStates = myGrid.numOfEach();
        List<State> stateList = myGameType.getMyValues();
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("State");
        for (int i = 0; i < numOfStates.size(); i++) {
            series1.getData().add(new XYChart.Data(numOfStates.get(i), stateList.get(i).getName()));
        }
        bc.getData().clear();
        bc.getData().add(series1);
    }

    /**
     * Method to return the Bar Chart object to other classes
     * @return
     */
    public BarChart<Number, String> getBarChart() {
        return this.bc;
    }



}
