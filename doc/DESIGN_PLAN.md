<<<<<<< HEAD
<<<<<<< HEAD
Overview: 
* Our program consists of 5 main classes: Cell, Grid, GameType, State and CellSociety, and GameType.
=======
=======
>>>>>>> 66ca19634965bc9f21cefd5b216a8b6d9732a132
Introduction
============
The primary goal of this project is for our group to design a program that can run several pre-defined 'cellular automata' simulations. The user will be able to specify the parameters for the 'game mode' and the program will take over from there in simulating it. The main design goal of the project is so that it will be very simple to add new types of models (number of neighbors, different cell states, and rules). The algorithms for each set of rules specified for the models will be closed but the parameters and initial states will be open for the user to change.


Overview
========= 
* Our program consists of 6 main classes: Cell, Grid, State, CellSociety, Reader, and GameType.
<<<<<<< HEAD
>>>>>>> master
=======
>>>>>>> 66ca19634965bc9f21cefd5b216a8b6d9732a132
* Cell
	* This class models one element in the grid. 
	* It knows its own state, but not its position
	* It is dependent on State, as that's one of its private instance variables.
	
```java
public Cell(){
    
    public State getCurrentState(); 
    
    public State getNextState(); 
    
    /**
    * Sets cell's current state to next state 
    */
    public void setNextState(State nextState);
    
   /**
    * Transitions the current state to next state
    */
    public void updateCell();
    
}
```
* Grid
	* This class keeps a 2d array of cells. 
	* It models the grid, and governs which cells neighbor which cells.
	* It is dependent on Cell, having a array of cells.
	* It is also dependent on GameType, calling it to determine the cells' next states
	
```java
public Grid() {
    
    /**
    * Constructor for Grid class, written to specify
    * parameters
    * @param width
    * @param height
    * @param initial_state
    * @param g
    */ 
    public Grid(int width, int height, State initial_state, GameType g);
    
    /**
    * Will look through all of the cells in the grid, call updateGrid(), then call
    * determineState on each cell to get the next state, and
    * then will call setNextState() on each cell to 
    * update the entire grid
    */
    public void updateGrid();
    
    public boolean isNeighbor(int x1, int y1, int x2, int y2);
    
}
```
* GameType
	* This class keeps the type of game, with whatever ruleset and variables are applicable
	* For example fire would have a variable regarding the probability of catching on fire given neighboring fire cells.
	
```java
public GameType(){
    
    /**
    * Look at the neighbors and take into account the rules
    * of the given rule set and then determine the next state 
    * of the cell
    * @param currentCell
    * @param neighbors
    */
    public State determineState(Cell currentCell, List<Cell> neighbors);
    
    
}

public FireGameType inherits GameType(){
	double probCatch;
    @Override
    public State determineState(Cell currentCell, List<Cell> neighbors);
    
    public void setprobCatch(double prob);
}
```
* State
	* This class models the state of a cell
	* It contains a image for viewing on the gui, and a unique id for ease in writing logic for it.
	
```java
public State() {
    
    public int getStateId();
    
    public ImageView getStateIV();
}
```
* CellSociety
	* This is the main class
	* It governs the gui, and updates the grid based off of user input
	* It is dependent on all other classes.
	
```java
public CellSociety() {
    
    public void start(Stage stage);
    
    /**
    * Will call update grid and 
    * change the GUI
    * @param cycles
    */
    public void step(int cycles);
    
    /**
    * Will set up the animation 
    * @param stage
    */
    public void setupAnimation(Stage stage);
    
    public void handleKeyInput(KeyCode code);
    
    /**
    * Will set up the scene and initialize the simulation
    * @param grid
    * @param stage
    */
    public Scene setupSimulation(Grid grid, Stage stage);
    
    public void reset();
}
```

* Reader class
	* Reads in a xml file and decides which gametype to set up
	
```java
public Reader(){
	/**
    * Reads in a file and determines the necessary conditions for making a Gametype
    * @param file
    */
	public void readFile(File file);
	/**
    * reads in some data and determines the correct game type.
    * @param game
    */
	public GameType getGameType(String game);
	/**
    * returns number of rows
    */
	public int getRows();
	/**
    * returns number of columns
    */
	public int getCols();
	/**
    * setups up initial state of grid and returns it
    */
	public Grid setupGrid();
}
```
		
* Picture of how components are related
[Class relations](cs308components.jpg "how the classes are related")

<<<<<<< HEAD
<<<<<<< HEAD
User Interface:
=======

User Interface
==============
>>>>>>> master
=======

User Interface
==============
>>>>>>> 66ca19634965bc9f21cefd5b216a8b6d9732a132
* For our user interface, we plan to have a dropdown menu for the different game types (conway, fire, predator, etc...). By making this a dropdown menu, it is easier to add more game modes.
* For items that probably won't change with more rules/grids/etc... We have buttons. 
	* The buttons are start, step, and reset
	* Start causes the simulation to start running at a predetermined speed, step goes through exactly one iteration, reset starts the simulation over
* Of course, the main feature on the UI is the game grid itself, which is prominently displayed.
* Picture 1: Normal UI
[Normal UI](NormalUI.JPG "UI no error")

* Picture 2: Error message UI
[Error message](ErrorMessage.JPG "UI with error")


<<<<<<< HEAD
<<<<<<< HEAD


=======
=======
>>>>>>> 66ca19634965bc9f21cefd5b216a8b6d9732a132
Design Details 
==============

As mentioned above in the overview, our program consists of 6 main classes: Cell, Grid, GameType, State, CellSociety, and Reader. GameType will have a number of subclasses depending on the number of simulations we want to run (1 subclass for each simulation).

##### Use Cases

1. Apply the rules to a middle cell: set the next state of a cell to dead by counting its number of neighbors using the Game of Life rules for a cell in the middle (i.e., with all its neighbors)
	* CellSociety will call start(), which calls setupSimulation() and step(), to set up the GUI
	* setupSimulation() will call readFile() and getGameType() to determine which simulation to run
	* step() will call updateGrid() on myGrid
	* updateGrid() will iterate through all the Cells in myGrid and create and ArrayList of neighbors 	for each Cell using isNeighbor(). For a middle Cell, all 8 neighboring Cells will be added to this list.
	* updateGrid() will call determineState() using the current Cell and the list of neighbors to determine what State the Cell should be in next
	* determineState() will return a State, and setNextState() will be called on the current Cell with this State as the parameter. The next State of the current Cell will be set to the return object of determineState().
	* updateGrid() will call updateCell() which will transition the Cell from its current State to its next State
	
2. Apply the rules to an edge cell: set the next state of a cell to live by counting its number of neighbors using the Game of Life rules for a cell on the edge (i.e., with some of its neighbors missing)
	* CellSociety will call start(), which calls setupSimulation() and step(), to set up the GUI
	* setupSimulation() will call readFile() and getGameType() to determine which simulation to run
	* step() will call updateGrid() on myGrid
	* updateGrid() will iterate through all the Cells in myGrid and create and ArrayList of neighbors 	for each Cell using isNeighbor(). For an edge Cell, all neighboring Cells will be added to this list. This list could contain anywhere from 2 to 8 Cells. 
	* updateGrid() will call determineState() using the current Cell and the list of neighbors to determine what State the Cell should be in next
	* determineState() will return a State, and setNextState() will be called on the current Cell with this State as the parameter. The next State of the current Cell will be set to the return object of determineState().
	* updateGrid() will call updateCell() which will transition the Cell from its current State to its next State
	
3. Move to the next generation: update all cells in a simulation from their current state to their next state and display the result graphically
	* CellSociety will call start(), which calls setupSimulation() and step(), to set up the GUI
	* setupSimulation() will call readFile() and getGameType() to determine which simulation to run
	* step() will call updateGrid() on myGrid
	* updateGrid() will iterate through all the Cells in myGrid and create and ArrayList of neighbors 	for each Cell using isNeighbor().
	* updateGrid() will call determineState() using the current Cell and the list of neighbors to determine what State the Cell should be in next
	* determineState() will return a State, and setNextState() will be called on the current Cell with this State as the parameter. The next State of the current Cell will be set to the return object of determineState().
	* updateGrid() will call updateCell() which will transition the Cell from its current State to its next State
	* updateGrid() will continue to iterate until all the Cells have transitioned to their next State and the simulation is on the next generation. Because each State comes with an ImageView, when the State of each Cell is changed, the result will be displayed graphically.
	
4. Set a simulation parameter: set the value of a parameter, probCatch, for a simulation, Fire, based on the value given in an XML file
	* CellSociety will call start(), which will call setupSimulation()
	* setupSimulation() will call readFile() and getGameType() to determine which simulation to run. In this instance, getGameType() will return a FireGameType object, a subclass of GameType.
	* FireGameType will specify probCatch using setProbCatch()
	
5. Switch simulations: use the GUI to change the current simulation from Game of Life to Wator
	* CellSociety will call start(), which will call setupSimulation()
	* setupSimulation() will call readFile() and getGameType() to determine which simulation to run. In this instance, getGameType() will return a LifeGameType object, a subclass of GameType.
	* start() will call step(), which will continue to run until the user decides to change the simulation 
	* When the user switches the simulation to Wator, CellSoceity will call reset()
	* reset() will call setupSimulation(), which will call readFile(), on a different XML file, and getGameType(). In this instance, getGameType() will return a WatorGameType object, a subclass of GameType.
	* step() will then be called using this new WatorGameType
	
##### Justification 

We created a Cell class to keep track of its own State and to be able to change its State. We did not want a Cell to know its location or who its neighbors are. We figured the Grid class would best be able to handle those things because it can look at multiple Cells at once. 

We created a Grid class in order to make our program more flexible - we wanted to have an object that could theoretically represent more than a 2D array. Our Grid class is primarily responsible for finding the neighbors of each Cell and calling updateCell() for each Cell.

We created an abstract GameType class in order to make our program flexible. Having an abstract GameType class allows us to use inheritance and easily add subclasses when we want to add another simulation. A GameType subclass is the only thing that needs to be added and changed when someone wants to add a new simulation; everything else will work for any general simulation.

We created an abstract State class because we kept referencing to a Cell's State during our initial discussion, and figured it would just be easier if we had a State object that methods could use as parameters and return. The abstract State class also makes it easier to add new States to a simulation, because we just add a new subclass. 

We created a Reader class to handle all the XML files in one place. Having this Reader class prevents the GameType subclasses from having to deal with reading in XML files, which will prevent a lot of repeated code.

The CellSociety class is where the animation happens and where the GUI is created.

Design Considerations
=====================

The overall design pattern allows for the cells and cell subclasses (the models) to be controlled by the Grid class which acts as a controller of sorts. Separate from both of those is the GUI which will only be responsible for displaying the actual simulation. Keeping them separate means that the models will only know how to complete the solution and will not change the view or update any values it should not. This set up makes it so that both the front-end implementation and the back-end implementation can be separated fully to create a lot of flexibility in our design. It also keeps too much control from being in one part of the program as well.

Some of the discussions we had were whether or not to put the State in its own class or not, and we ultimately decided that having a State and subclasses to specify will help to increase flexibility and allow for the easy updating of cells (models). 

Another consideration we had was whether or not to make a GameType class which would help in implementing the rule sets for each automation. We ultimately decided to do this because it will make the addition of new rule sets much easier if a GameType super class exists.

Finally, one assumption that we made is that the grid can be represented in two dimensions and specifically as a 2-D array. We think that for now this is a safe assumption to make because of the challenge of visualizing and depicting a three-dimensional cell representation.

Team Responsibilities:
=======================

Front end GUI: Alexi Kontos

XML/Reader & Resource Files for Game Modes: Alexi Kontos

Grid Class: Samson Rao

Cell and State Classes: Samson Rao

GameType Class: Jenny Chin

CellSociety Class: Everyone
<<<<<<< HEAD
>>>>>>> master
=======
>>>>>>> 66ca19634965bc9f21cefd5b216a8b6d9732a132








