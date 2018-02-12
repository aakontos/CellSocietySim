# Cell Society

### Authors - Samson Rao (sr307), Jenny Chin (jrc81), Alexi Kontos (aak29)

#### Dates: 1/23/18 - 2/11/18
#### Cumulative Time Spent: ~100 hours


Cell Society is a program that simulates based on a model that consists of a grid of cells with a finite number of states it can be in. Simulations run by updating a cell based on a fixed set of rules described in terms of the cell's current state, and the states of its immediate neighbors. 

In this program we allow the user to run simulations based on the following rule sets:
* Spreading of Fire
    * [Rules of Spreading of Fire](http://nifty.stanford.edu/2007/shiflet-fire/)
* Conway's Game of Life
    * [Rules of Conway's Game of Life](https://en.wikipedia.org/wiki/Conway's_Game_of_Life)
* Wa-Tor Model of Predator Prey Relationships
    * [Rules of Wa-Tor](http://nifty.stanford.edu/2011/scott-wator-world/)
* Schelling's Model of Segregation
    * [Rules of Segregation](http://nifty.stanford.edu/2014/mccown-schelling-model-segregation/)
* Rock Paper Scissors
    * [Rules of Rock Paper Scissors](https://www.gamedev.net/blogs/entry/2249737-another-cellular-automaton-video/)


The user can choose the manner in which the grid is populated with the following choices:
* Random
    * The grid will be randomly filled with any of the states defined in the gamemode's rules
* Probability
    * The grid will be filled with the given probabilities of each state specified in the XML file
* Specific
    * The grid will be filled specifically how the grid is defined in the XML file
   
Both probability and specific can be edited by the user, and will run as long as the user correctly fills in the data. If they do not they will be prompted with an alert of the mistake they have made and asked to fix their issue.
 

The user can run the above simulations on grids with the following shapes:
* Square Grid
* Hexagonal Grid
* Triangular Grid

The user can choose between the following different types of grid edges:
* Finite Grid Edge
* Toroidal Grid Edge

The user can also change the speed of the simulation as they want, individually change single cells by clicking directly on it, save and load a simulation at a specific point, update the type of neighborhood (the manner in which neighbors are selected), and see a visualization of the simulation's state population in real time below the grid.

To control the simulation, the user can use the following buttons, sliders, and menus:
* Play (Button)
    * Pressing will run the simulation
* Pause (Button)
    * Pressing will pause the simulation
* Step (Button)
    * Pressing will move the simulation one step forward
* Speed Up (Button)
    * Pressing will speed up the simulation
* Slow Down
    * Pressing will slow down the simulation
* Reset (Button)
    * Pressing will reset the simulation to its initial state
* Save (Button)
    * Pressing will save the simulation at its current state
* Simulations (Menu)
    * Allows the user to select from the given simulations
* Change Size (Slider)
    * Allows the user to change the size of the visualization of the grid
* Change Rows/Columns (Slider)
    * Allows the user to change the number of rows and columns in the grid
* Grids (Menu)
    * Allows the user to change the shape of the grid's cells between square, triangle, and hexagon
* Toggle Grid Edge (Button)
    * Allows the user to toggle between the toroidal and finite grid edges
* Toggle Grid Lines (Button)  
    * Allows the user to turn the grid outlines on and off
* Neighborhoods (Menu)
    * Allows the user to edit the way the 'neighborhoods' are determined
      
      
See package-info.java in each package for more documentation on the package, as well as the JavaDocs that accompany each class and method for more detailed explanations.

Responsibilities
================ 
## Jennifer Chin
Jennifer worked primarily on the visualization portion of the project, creating most of the classes in the front end package. She was responsible for the creating the GUI and updating it accordingly depending on the current simulation and grid. In order to do so, she created the Viewer interface and several controls that allow the user to interact with the simulation.
## Samson Rao
Samson worked primarily on the backend logic, from building the backend heirarchy (interfaces Grid and GameType and their subclasses), to implementing all the specific classes in the backendClasses package. 
## Alexi Kontos
Alexi worked primarily on the configuration of the project, writing all of the .xml files, and the files associated with reading, writing, and saving them (XMLParser, XMLException, XMLBuilder, Reader). As well Alexi wrote the Alerts package, the Resources class, and the StateChart class.

Known Bugs
===========
* Changing a cell's state by clicking on the cell, the first click will not register, but every time after it will.
* Any simulation that involves movement tends to shift to the top left of the grid no matter what
* When the error message pops up to notify the user that a given distrubution for the probability choice is greater than 1.0, the alert pops up twice






