//
// TetrisGrid encapsulates a tetris board and has
// a clearRows() capability.
package assign1;

public class TetrisGrid {
	private boolean[][] grid;
	/**
	 * Constructs a new instance with the given grid.
	 * Does not make a copy.
	 * @param grid
	 */
	
	public TetrisGrid(boolean[][] grid) {
		this.grid = grid;
	}
	
	
	/**
	 * Does row-clearing on the grid (see handout).
	 */
	public void clearRows() {
		//iterate over the matrix to find if entire column is true
		for(int j = 0; j < grid[0].length; j++) {
			boolean rowIsTrue = true;
			for(int i = 0; i < grid.length; i++) {
					rowIsTrue = rowIsTrue & grid[i][j];
			}
		if (rowIsTrue) {
			grid = rebuildGrid(j);
			j = j-1;
		}
		}
	}
	
	/**
	 * rebuilds grid excluding a column
	 */
	private boolean[][] rebuildGrid(int excludedRow) {
		boolean[][] newGrid = new boolean[grid.length][grid[0].length];
		int copyRow = 0;
		//iterate over the matrix to create a new copy
		for(int j = 0; j < grid[0].length; j++) {
			for(int i = 0; i < grid.length; i++) {
				//case when the row has to be deleted
				if(j == grid[0].length-1) {
					newGrid[i][j] = false;
					break;
				}
				//case when we've reached the last row
				else if (j == excludedRow) {
					copyRow = 1;
				}
				//copying normal row
				newGrid[i][j] = grid[i][j+copyRow];
			}
		}
		return newGrid;
	}
	
	
	/**
	 * Returns the internal 2d grid array.
	 * @return 2d grid array
	 */
	boolean[][] getGrid() {
		return grid;
	}
}
