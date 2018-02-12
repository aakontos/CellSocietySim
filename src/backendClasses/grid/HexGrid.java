package backendClasses.grid;

import java.util.ArrayList;
import java.util.List;

import backendClasses.Cell;
import backendClasses.gametype.GameType;
import javafx.geometry.Point2D;
/**
 * @author shichengrao
 * This class models a hexagonal tiling
 * It inherits from Grid and depends on cell and state
 * Can alter neighborhoods
 */
public class HexGrid implements Grid {
	
	private Cell[][] grid;
	private GameType game;
	private int gametime;
	private int neighborhoodType = 0;
	private boolean toroidal = true;
	private static final List<int[]> xneighborhoods = new ArrayList<int[]>() {{
		add(new int[] {0,0,1,-1,1,-1});
	}};
	private static final List<int[]> yneighborhoods = new ArrayList<int[]>() {{
		add(new int[] {-1,1,0,0,-1,-1});
		add(new int[] {-1,1,0,0,1,1});
	}};
	/**
	 * makes a new grid with specified height, width and game mode
	 * @param width
	 * @param height
	 * @param g
	 */
	public HexGrid(int width, int height, GameType g) {
		grid = new Cell[width][height];
		game = g;
		gametime = 0;
		toroidal = false;
		neighborhoodType = 0;
	}
	/**
	 * goes through the entire grid and updates the whole grid based on the rules of the game mode
	 */
	@Override
	public void updateGrid() {
		gametime++;
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[0].length;j++) {
				if(validTile(i,j)) {
					List<Cell> Neighbors = getCells(xneighborhoods.get(neighborhoodType),yneighborhoods.get(neighborhoodType+ (i%2)),i,j);
					Point2D point = game.determinePosition(grid[i][j], Neighbors, i, j, grid,xneighborhoods.get(neighborhoodType),yneighborhoods.get(neighborhoodType+ (i%2)),toroidal);
					if(point == null) {
						continue;
					}
					grid[(int)point.getX()][(int)point.getY()].setNextState(game.determineState(grid[i][j],Neighbors));
					if((point.getX() != i || point.getY() != j)&&game.replaceWithEmpty(grid[i][j], gametime)) {
							grid[i][j].setNextState(game.getMyValues().get(0));
					}
				}
			}
		}
		for(Cell[] row: grid) {
			for(Cell cell: row) {
				cell.updateCell();
			}
		}

	}
	/**
	 * returns whether the cell is valid or not
	 */
	@Override
	public boolean validTile(int x, int y) {
		return(!(outOfBounds(x,y)|| !grid[x][y].valid()));
	}
	/**
	 * gets cells based on the neighborhoods defined
	 */
	@Override
	public List<Cell> getCells(int[] xdeltas, int[] ydeltas,int x, int y) {
		List<Cell> cells = new ArrayList<>();
		for(int i = 0; i < xdeltas.length; i++) {
			int newX = newX(x, xdeltas[i]);
			int newY = newY(y, ydeltas[i]);
			if(validTile(newX, newY)) {
				cells.add(grid[newX][newY]);
			}
		}
		return cells;
	}
	/**
	 * returns an xcoordinate, possibly with wrapping depending on if its a toroidal grid
	 */
	public int newX(int x, int xdelta) {
		if(toroidal) {
			return ((x+xdelta) + grid.length)%grid.length;
		}
		return x + xdelta;
	}
	/**
	 * returns an ycoordinate, possibly with wrapping depending on if its a toroidal grid
	 */
	public int newY(int y, int ydelta) {
		if(toroidal) {
			return ((y+ydelta) + grid[0].length)%grid[0].length;
		}
		return y + ydelta;
	}
	/** 
     * sets a cell at a position
     * @param x
     * @param y
     * @param cell
     */
	@Override
	public void setCell(int x, int y, Cell cell) {
		grid[x][y] = cell;
	}
	/** 
	 * as a bounded rectangle, out of bounds is solely based on if the coordinates would generate an ArrayOutOfBounds exception
	 */
	@Override
	public boolean outOfBounds(int x, int y) {
		return x < 0 || x >= grid.length || y < 0|| y>= grid[0].length;
	}
	 /** 
     * bounds check
     * @param x
     * @param y
     * @return if the x,y is a valid position in the grid
     */
	@Override
	public Cell getCellAtLocation(int x, int y) {
		return grid[x][y];
	}
	/** 
     * @return a list of all states and the number of each state
     */
	@Override
	public List<Integer> numOfEach(){
		List<Integer> result = new ArrayList<>();
		for(int i = 0; i < game.getMyValues().size(); i++) {
			result.add(0);
		}
		for(Cell[] row: grid) {
			for(Cell cell: row) {
				int id = cell.getCurrentState().getStateId();
				result.set(id, result.get(id)+ 1);
			}
		}
		return result;
	}
	/** 
     * changes the grid from finite to toroidal, and vice versa
     */
	public void toggleToroidal() {
		toroidal = !toroidal;
	}
	 /** 
     * changes neighborhood to one of those specified in the private instance variable
     */
	public void changeNeighbors(int newNeighborhood) {
		neighborhoodType = newNeighborhood;
	}
}
