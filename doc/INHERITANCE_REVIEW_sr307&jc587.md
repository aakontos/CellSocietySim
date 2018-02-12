Part 1
* What is an implementation decision that your design is encapsulating (i.e., hiding) for other areas of the program?
	* We both use a 2d array in grid that no other class sees. 
* What inheritance hierarchies are you intending to build within your area and what behavior are they based around?
	* We both have a grid hierarchy, and differ on whether or not to have a state hierarchy. 
* What parts within your area are you trying to make closed and what parts open to take advantage of this polymorphism you are creating?
	* The superclasses are open to extension, while the individual subclasses are closed to any editing or extension.
* What exceptions (error cases) might occur in your area and how will you handle them (or not, by throwing)?
	* if the cell is for fire but its a conway cell, it should throw a InvalidCellException. Also when the xml loads the grid, if it goes out of bounds, it should throw a IndexOutOfBoundsException.
* Why do you think your design is good (also define what your measure of good is)?
	*One measure is flexibility. By making a game hierarchy, we can add more gametypes without disrupting the general flow. 
    
    
    
Part 2
* How is your area linked to/dependent on other areas of the project?
	* The gui needs to call the backend to see its changes and update its visualization accordingly. The xml generates the objects the backend uses like the grid. 
* Are these dependencies based on the other class's behavior or implementation?
	* The other components depend on the back end, while the backend doesnt really depend on anything else, so it has no dependencies on other class's implementation
How can you minimize these dependencies?
back end has no dependencies to other areas. For dependencies to backend, its probably best to minimize the number of calls needed (only call updateGrid)
* Go over one pair of super/sub classes in detail to see if there is room for improvement.
Focus on what things they have in common (these go in the superclass) and what about them varies (these go in the subclass).
	* The grid superclass and square grid sub class. For me, I should be using the protected method type, since I want to inherit the method, but its a helper method i dont want any other class to call. 

Part 3
* Come up with at least five use cases for your part (most likely these will be useful for both teams).
	* 1.step - go through one cycle of a simulation
	* 2. play - loop constantly through updates. 
	* 3. go from an xml file to a correct grid (square, triangle, etc...) with the correct type (fire, conway, etc...)
	* 4. user chooses the new simulation
	* 5. user attempts to load in bad data and gets message describing why what he did is invalid.
* What feature/design problem are you most excited to work on?
	* I'm most excited to implement the different game types. 
* What feature/design problem are you most worried about working on?
	* I'm worried the different dependencies that other parts have to my part and how everything works together. 