package frontendClasses;

import java.io.File;
import backendClasses.gametype.GameType;
import backendClasses.grid.Grid;
import backendClasses.grid.HexGrid;
import backendClasses.grid.SquareGrid;
import backendClasses.grid.TriangleGrid;
import backendClasses.Cell;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import reader.Reader;
import resources.Resources;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;

/**
 * @author Jennifer Chin
 * 
 * Class created to display the GUI. This class is responsible for all the animation in the program and determining
 * where features are displayed. This class also creates buttons and other instances that allow the user to interact
 * with the simulation.
 */


public class CellSociety extends Application{
	
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 800;
    public static final Paint BACKGROUND = Color.WHITE;
    private int GRID_WIDTH = 500;
    private int GRID_HEIGHT = 500;
    public static final int GRID_X_POS = 250;
    public static final int GRID_Y_POS = 50;

    private Grid myGrid;
    private ObservableList<String> possSimulations = FXCollections.observableArrayList(
    		"FireProbability",
    		"FireRandom",
    		"FireSpecific",
    		"ConwayProbability",
    		"ConwayRandom",
    		"ConwaySpecific",
    		"PredPreyProbability",
    		"PredPreyRandom",
    		"PredPreySpecific",
    		"SegregationProbability",
    		"SegregationRandom",
    		"SegregationSpecific",
    		"RPSProbability",
    		"RPSRandom",
    		"RPSSpecific",
    		"SavedSimulation"
    		);
    private ObservableList<String> possGrids = FXCollections.observableArrayList(
    		"Square",
    		"Hexagon",
    		"Triangle");
    private ObservableList<String> possNeighbors = FXCollections.observableArrayList("0", "1", "2", "3");
    private boolean runSimulation = false;
    private GameType myGameType;
    private Viewer myViewer;
    private int myGridRows;
    private int myGridCols;
    private File myCurrentFile;
    private Timeline animation;
    private int framesPerSecond = 5;
    private int millisecondDelay = 1000 / framesPerSecond;
    private double secondDelay = 1.0 / framesPerSecond;
    private Group myCells;
    private Group root = new Group();
    private Reader reader;
    private String myGridType = "Square";
    private Slider rowColSlider;
    private StateChart myChart;
    private int myRate;
    
    /**
     * Starts the animation. Calls helper methods to set up the initial GUI. Initializes myCurrentFile and myStage.
     * @param stage 
     */
	public void start(Stage stage){
		Stage myStage = stage;
		myCurrentFile = new File("src/resources/empty.xml");
		setSimulation(); 
		Scene myScene = setupSimulation(myStage);
		myStage.setScene(myScene);
		myStage.setTitle(Resources.getString("Title"));
		myStage.show();
		setupAnimation();
	}
	
	/**
	 * Function that is called every frame to create the animation. Updates the myGrid object and updates the viewer
	 * accordingly. Also updates the chart on the bottom of the screen that displays how many cells of each type 
	 * exist at a time.
	 * @param cycles
	 */
	
	private void step(double cycles){
		if (runSimulation){
			myGrid.updateGrid();
			changeViewer();
			root.getChildren().remove(myChart.getBarChart());
			changeChart();
		}
	}
	
	/**
	 * Sets up the animation for the simulation.
	 */
	
	private void setupAnimation(){
		KeyFrame frame = new KeyFrame(Duration.millis(millisecondDelay), e -> step(secondDelay));
		animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}
	
	/**
	 * Sets up the initial GUI for the simulation. Uses a gridpane to place static objects such as buttons and drop
	 * down menus. Updating objects, such as the grid viewer, are added to the Scene directly. Returns a Scene that
	 * is displayed by myStage.
	 * @param stage
	 * @return Scene
	 */
	
	private Scene setupSimulation(Stage stage){
		Scene scene = new Scene(root, WIDTH, HEIGHT, BACKGROUND);
		GridPane gridpane = new GridPane();
		gridpane.setPadding(new Insets(Resources.getInt("Inset"), Resources.getInt("Inset"), Resources.getInt("Inset"), Resources.getInt("Inset")));
		gridpane.setVgap(Resources.getInt("GridGap"));
		gridpane.setHgap(Resources.getInt("GridGap"));
		
		ComboBox simulationMenu = new ComboBox(possSimulations);
		simulationMenu.setPromptText("Simulations");
		simulationMenu.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
				String item = simulationMenu.getSelectionModel().getSelectedItem().toString();
				myCurrentFile =  new File(Resources.getString(item));
				changeSimulation(root);
			}
		});
		gridpane.add(simulationMenu, Resources.getInt("LeftButtonsX"), Resources.getInt("FirstButtonY"));
		
		ComboBox gridMenu = new ComboBox(possGrids);
		gridMenu.setPromptText("Grids");
		gridMenu.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
				String tempgrid = gridMenu.getSelectionModel().getSelectedItem().toString();
				myGridType = tempgrid;
				root.getChildren().remove(myCells);
				setGridType();
			}
		});
		gridpane.add(gridMenu, Resources.getInt("RightButtonsX"), Resources.getInt("SixthButtonY"));
		
		ComboBox neighborMenu = new ComboBox(possNeighbors);
		neighborMenu.setPromptText("Neighborhoods");
		neighborMenu.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
				String temp = neighborMenu.getSelectionModel().getSelectedItem().toString();
				myGrid.changeNeighbors(Integer.parseInt(temp));
			}
		});
		gridpane.add(neighborMenu, Resources.getInt("RightButtonsX"), Resources.getInt("SeventhButtonY"));
		
		Button speedUpButton = makeSpeedUpButton();
		addToGridPane(speedUpButton, Resources.getInt("LeftButtonsX"), Resources.getInt("SecondButtonY"), gridpane);
		Button slowDownButton = makeSlowDownButton();
		addToGridPane(slowDownButton, Resources.getInt("LeftButtonsX"), Resources.getInt("ThirdButtonY"), gridpane);
		Button playButton = makePlayButton();
		addToGridPane(playButton, Resources.getInt("RightButtonsX"), Resources.getInt("FirstButtonY"), gridpane);
		Button pauseButton = makePauseButton();
		addToGridPane(pauseButton, Resources.getInt("RightButtonsX"), Resources.getInt("SecondButtonY"), gridpane);
		Button stepButton = makeStepButton();
		addToGridPane(stepButton, Resources.getInt("RightButtonsX"), Resources.getInt("ThirdButtonY"), gridpane);
		Button resetButton = makeResetButton();
		addToGridPane(resetButton, Resources.getInt("RightButtonsX"), Resources.getInt("FourthButtonY"), gridpane);
		Button saveButton = makeSaveButton();
		addToGridPane(saveButton, Resources.getInt("LeftButtonsX"), Resources.getInt("FourthButtonY"), gridpane);
		Button toroidalButton = makeToroidalButton();
		addToGridPane(toroidalButton, Resources.getInt("RightButtonsX"), Resources.getInt("FifthButtonY"), gridpane);
		Button outlineButton = makeOutlineButton();
		addToGridPane(outlineButton, Resources.getInt("LeftButtonsX"), Resources.getInt("SeventhButtonY"), gridpane);
		
		Slider sizeSlider = new Slider(Resources.getInt("GridMin"), Resources.getInt("InitGridWidth"), Resources.getInt("InitGridHeight"));
		sizeSlider.valueProperty().addListener(new ChangeListener<Number>(){
			public void changed(ObservableValue<? extends Number> ov, Number old_value, Number new_value){
				GRID_WIDTH = new_value.intValue();
				GRID_HEIGHT = new_value.intValue();
				changeViewer();
			}
		});
		gridpane.add(sizeSlider, Resources.getInt("LeftButtonsX"), Resources.getInt("FifthButtonY"));
		Label sizeCaption = new Label("Change Size:");
		gridpane.add(sizeCaption, Resources.getInt("LeftButtonsX"), Resources.getInt("FifthButtonY") - 1);
		
		rowColSlider = new Slider(2, myGridRows, myGridRows);
		rowColSlider.valueProperty().addListener(new ChangeListener<Number>(){
			public void changed(ObservableValue<? extends Number> ov, Number old_value, Number new_value){
				myGridRows = new_value.intValue();
				myGridCols = new_value.intValue();
				changeViewer();
			}
		});
		gridpane.add(rowColSlider, Resources.getInt("LeftButtonsX"), Resources.getInt("SixthButtonY"));
		Label rowColCaption = new Label ("Change Rows/Columns: ");
		gridpane.add(rowColCaption, Resources.getInt("LeftButtonsX"), Resources.getInt("SixthButtonY") - 1);
		
		Text title = new Text(Resources.getInt("TitleX"), Resources.getInt("TitleY"), Resources.getString("DisplayTitle"));
		title.setFont(new Font(Resources.getInt("TitleSize")));
		root.getChildren().add(title);
		root.getChildren().add(gridpane);
		
		scene.setOnMousePressed(new EventHandler<MouseEvent>(){
			@Override public void handle(MouseEvent e){
				onMouseClick();
			}
		});
		
		return scene;
	}
	
	/**
	 * Allows users to click on a cell and cause the cell to change state. Calculates the position of the clicked
	 * cell in the grid and updates the grid and viewer accordingly.
	 */
	
	private void onMouseClick(){
		for(Node node: myCells.getChildren()){
			double xPos = node.getBoundsInLocal().getMinX();
			double yPos = node.getBoundsInLocal().getMinY();
			double xStep = GRID_WIDTH / myGridRows;
			double yStep = GRID_HEIGHT / myGridCols;
			int col = (int) Math.round((xPos - GRID_X_POS) / xStep);
			int row = (int) Math.round((yPos - GRID_Y_POS) / yStep);
			node.setOnMousePressed(new EventHandler<MouseEvent>(){
				@Override public void handle(MouseEvent e){
					Cell cell = myGrid.getCellAtLocation(col, row);
					cell.increment();
					if (node instanceof Shape){
						((Shape) node).setFill(cell.getCurrentState().getStateColor());
					}
				}
			});
		}
	}
	
	/**
	 * Initializes the reader and obtains necessary information from the reader. This has its own helper method 
	 * because the reader needs to be initialized in multiple places.
	 */
	
	private void setSimulation(){
		reader = new Reader(myCurrentFile);
		myGameType = reader.getGameType();
		myGridRows = reader.getRows();
		myGridCols = reader.getColumns();
		setGridType();
	}
	
	/**
	 * Changes the simulation when a different simulation is selected from the drop down menu. This method all resets
	 * the row and column slider
	 * @param root
	 */
	
	private void changeSimulation(Group root){
		setSimulation(); 
		root.getChildren().remove(myCells);
		root.getChildren().remove(myChart.getBarChart());
		setGridType();
		rowColSlider.setValue(myGridRows);
	}
	
	/**
	 * Sets the grid type depending on what shape the user chooses from the drop down menu. The default grid type is
	 * Square (set above as an instance variable). This method changes the grid itself as well as the viewer. This
	 * method also changes the chart depending on the simulation/ grid shape.
	 */
	
	private void setGridType(){
		root.getChildren().remove(myCells);
		if (myGridType.equals("Square")){
			myGrid = new SquareGrid(myGridRows, myGridCols, myGameType);
			myViewer = new SquareViewer(root, myGrid);
		}
		else if (myGridType.equals("Triangle")){
			myGrid = new TriangleGrid(myGridRows, myGridCols, myGameType);
			myViewer = new TriangleViewer(root, myGrid);
		}
		else if (myGridType.equals("Hexagon")){
			myGrid = new HexGrid(myGridRows, myGridCols, myGameType);
			myViewer = new HexViewer(root, myGrid);
		}
		reader.initializeGrid(myGrid);
		myCells = myViewer.initGrid(GRID_X_POS, GRID_Y_POS, GRID_WIDTH, GRID_HEIGHT, myGridRows, myGridCols);
		root.getChildren().add(myCells);
		
		if (myChart != null){
			root.getChildren().remove(myChart.getBarChart());
		}
		myChart = new StateChart(myGrid, myGameType);
		changeChart();
	}
	
	/**
	 * Allows the user to decide if the viewer has grid lines or not 
	 * @return Button
	 */
	
	private Button makeOutlineButton(){
		Button outlineButton = new Button("Toggle Grid Lines");
		outlineButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
				myViewer.changeLines();
				myGrid.updateGrid();
				changeViewer();
			}
		});
		return outlineButton;
	}
	
	/**
	 * Allows the user the determine if the grid edges are finite or toroidal
	 * @return Button
	 */
	
	private Button makeToroidalButton(){
		Button toroidalButton = new Button("Toggle Grid Edge");
		toroidalButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
				myGrid.toggleToroidal();
			}
		});
		return toroidalButton;
	}
	
	/**
	 * Allows the user to speed up the simulation
	 * @return Button
	 */
	
	private Button makeSpeedUpButton(){
		Button speedUpButton = new Button("Speed Up");
		speedUpButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
				animation.setRate(myRate++);
			}
		});
		return speedUpButton;
	}
	
	/**
	 * Allows the user to slow down the simulation
	 * @return Button
	 */
	
	private Button makeSlowDownButton(){
		Button slowDownButton = new Button("Slow Down");
		slowDownButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle (ActionEvent e){
				if (myRate <= 1){
					return;
				}
				animation.setRate(myRate--);
			}
		});
		return slowDownButton;
	}
	
	/**
	 * Allows the user to start the simulation - gives the user control of when the simulation starts
	 * @return Button
	 */
	
	private Button makePlayButton(){
		Button playButton = new Button("Play");
		playButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
				runSimulation = true;
			}
		});
		return playButton;
	}
	
	/**
	 * Allows the user to pause the simulation
	 * @return Button
	 */
	
	private Button makePauseButton(){
		Button pauseButton = new Button("Pause");
		pauseButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
				runSimulation = false;
			}
		});
		return pauseButton;
	}
	
	/**
	 * Allows the user to step through the simulation one frame at a time
	 * @return Button
	 */
	
	private Button makeStepButton(){
		Button stepButton = new Button("Step");
		stepButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
				myGrid.updateGrid();
				changeViewer();
				root.getChildren().remove(myChart.getBarChart());
				changeChart();
			}
		});
		return stepButton;
	}
	
	/**
	 * Allows the user to reset the current simulation 
	 * @return Button
	 */
	
	private Button makeResetButton(){
		Button resetButton = new Button("Reset");
		resetButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
				changeSimulation(root);
				runSimulation = false;
			}
		});
		return resetButton;
	}
	
	/**
	 * Allows the user to save their current simulation as an XML file that can be rerun later
	 * @return Button
	 */
	
	private Button makeSaveButton(){
		Button saveButton = new Button("Save");
		saveButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
				reader.saveXML(myGrid);
			}
		});
		return saveButton;
	}
	
	/**
	 * Helper method for adding Buttons to the gridpane
	 * @param button
	 * @param xPos
	 * @param yPos
	 * @param gridpane
	 */
	
	private void addToGridPane(Button button, int xPos, int yPos, GridPane gridpane){
		gridpane.add(button, xPos, yPos);
	}
	
	/**
	 * Helper method that updates the viewer
	 */
	
	private void changeViewer(){
		root.getChildren().remove(myCells);
		myCells = myViewer.initGrid(GRID_X_POS, GRID_Y_POS, GRID_WIDTH, GRID_HEIGHT, myGridRows, myGridCols);
		root.getChildren().add(myCells);
	}
	
	/**
	 * Helper method that updates the chart
	 */
	
	private void changeChart(){
		myChart.addData();
		myChart.getBarChart().setMaxHeight(Resources.getInt("ChartHeight"));
		myChart.getBarChart().setLayoutX(Resources.getInt("ChartXPos"));
		myChart.getBarChart().setLayoutY(Resources.getInt("ChartYPos"));
		root.getChildren().add(myChart.getBarChart());
	}
	
	/**
	 * Main method that calls start to begin the simulation
	 * @param args
	 */
	
	public static void main(String[] args) {
		launch(args);
	}

}
