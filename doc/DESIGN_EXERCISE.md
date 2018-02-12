CompSci 308: Design Exercise 1/25/18
====================================

#### 1 and 2. How does a cell know what rules to apply for its simulation and How does a Cell know about its neighbors? How can it update itself without effecting its neighbors update?

A cell knows what states it can be in, can change its own state but has to be told what to change to. There will be a grid class that acts as a manager that looks at all of the cells and its neighbors without asking the cell itself to look. Cell will not know its own location will just know its type and the grid will handle the relationships between the neighbors in each grid location and tell cells to change their states. The cells will know which next state it should be in, so as we go through all of the cells if it needs to change it will change, but if not its next state will be its current state and then at the end all cells will shift into their next state.

#### 3. What is the grid? Does it have any behaviors? Who needs to know about it?

The grid will act as the manager of all of the cells, the cells do not need to know about it, but the grid will be able to look at the cells and their neighbors in order to change cells states. GUI needs to see the grid in order visualize the grid. The behaviors of the grid were described above.

#### 4. What information about a simulation needs to be (in?) the configuration file?

The rule set and initial state (how cells change state depending on neighbors), dimensions of the grid.

#### 5. How is the GUI updated after all the cells have been updated?

The GUI is updated by looping through the Grid and updating each cell's state, (similarly to the breakout game's step method). If a cell state has changed then update if not then don't.

CRC Cards
=========
#### Cell Class

```java
public Cell(){
    
    public State get_current_state(); 
    
    public State get_next_state(); 
    
    public void set_next_state(State next_state);
    
    /**
    * Sets cell's current state to next state 
    */
    public void update_cell();
    //Update will transition the current state to next state
    
}
```

#### Grid Class
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
    * Will look through all of the cells in the grid, call update_grid(), then call
    * determineState on each cell to get the next state, and
    * then will call set_next_state() on each cell to 
    * update the entire grid
    */
    public void updateGrid();
    
    public boolean is_neighbor(int x1, int y1, int x2, int y2);
    
}

```

#### GameType Class
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
```
#### FireGameType subclass
```java
public FireGameType inherits GameType(){
	double probCatch;
    @Override
    public State determineState(Cell currentCell, List<Cell> neighbors);
    
    public void setprobCatch(double prob);
}
```



#### State Class
```java
public State() {
    
    public int getStateId();
    
    public ImageView getStateIV();
}
```

#### CellSociety Class
```java
public CellSociety() {
    
    public void start(Stage stage);
    
    /**
    * Will call update grid and 
    * change the GUI
    * @param elapsedTime
    */
    public void step(double elapsedTime);
    
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

Use Cases
=========
1) Apply the Rules to a middle cell
    * setup_simulation()
    * step()
    * update_grid()
        * isNeighbor()
        * determine_state()
        * set_next_state()
        * update_cell()
2) Apply the Rules to an edge cell
    * setup_simulation()
    * step()
    * update_grid()
        * isNeighbor()
        * determine_state()
        * set_next_state()
        * update_cell()
        
3) Move to the next generation
    * setup_simulation()
    * step()
    * update_grid()
        * isNeighbor()
        * determine_state()
        * set_next_state()
        * update_cell()
        
4) Set a simulation parameter
    * setup_simulation()
    * //selects FireGameType, and is taken care of with setProbCatch()
        
5) Switch simulations
	*reset()
    *setup_simulation()//with new gameType

