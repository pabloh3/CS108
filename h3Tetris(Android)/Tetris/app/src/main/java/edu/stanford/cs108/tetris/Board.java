// Board.java
package edu.stanford.cs108.tetris;
import java.util.Arrays; 
import java.util.Collections; 

/**
 CS108 Tetris Board.
 Represents a Tetris board -- essentially a 2-d grid
 of booleans. Supports tetris pieces and row clearing.
 Has an "undo" feature that allows clients to add and remove pieces efficiently.
 Does not do any drawing or have any idea of pixels. Instead,
 just represents the abstract 2-d board.
*/
public class Board	{
	// Some ivars are stubbed out for you:
	private int width;
	private int height;
	private boolean[][] grid;
	private boolean DEBUG = true;
	boolean committed;
	//the following ivars help us keep track of the board situation
	private int[] widths;
	private int[] heights;
	private int maxHeight;
	private Board backup;
	
	// Here a few trivial methods are provided:
	
	/**
	 Creates an empty board of the given width and height
	 measured in blocks.
	*/
	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		//initialize the grid to false
		grid = new boolean[width][height];
		for(int i = 0; i < width; i++) {
			for(int j =0; j < height; j++){
				grid[i][j] = false;
			}
		}
		//initialize the widths array to 0
		widths = new int[height];
		for (int i = 0; i < height; i++) {
			widths[i] = 0;
		}
		//initialize the heights array to 0
		heights = new int[width];
		for (int i = 0; i < width; i++) {
			heights[i] = 0;
		}
		//initialize the maxHeight to 0
		this.maxHeight = 0;
		committed = true;
	}
	
	
	/**
	 Returns the width of the board in blocks.
	*/
	public int getWidth() {
		return width;
	}
	
	
	/**
	 Returns the height of the board in blocks.
	*/
	public int getHeight() {
		return height;
	}
	
	
	/**
	 Returns the max column height present in the board.
	 For an empty board this is 0.
	*/
	public int getMaxHeight() {
		int max = 0; 
		 for (int i: heights){
			 if (i > max) max = i;
		 }
		return max;
	}
	
	
	/**
	 Checks the board for internal consistency -- used
	 for debugging.
	*/
	public void sanityCheck() throws RuntimeException{
		if (DEBUG) {
			int maxHeightTot = 0; int k; int[] widthsSanity = new int[height];
			//initialize widths array
			for(int widthX : widthsSanity) widthX = 0;
			//iterate over the entire board
			for(k = 0; k < width; k++) {
				int maxHeightL = 0;
				for(int l = 0; l < height; l++){
					//recalculate heights and widths
					if(grid[k][l] == true){
						if(l + 1 > maxHeightL) maxHeightL = l + 1;
						if(maxHeightL > maxHeightTot) maxHeightTot = maxHeightL;
						widthsSanity[l]++;
					}
				}
				if(maxHeightL != heights[k]){
					throw new RuntimeException("Wrong column height");
				}
			}
			if(maxHeight != maxHeightTot){
				throw new RuntimeException("Wrong maxHeight");
			}
			for(int m = 0; m < height; m++){
				if (widthsSanity[m] != widths[m]) throw new RuntimeException("Wrong width");
			}
		}
	}
	
	/**
	 Given a piece and an x, returns the y
	 value where the piece would come to rest
	 if it were dropped straight down at that x.
	 
	 <p>
	 Implementation: use the skirt and the col heights
	 to compute this fast -- O(skirt length).
	*/
	public int dropHeight(Piece piece, int x) {
		int[] skirt = piece.getSkirt();
		int dropHeightMax = 0; int distance; int distanceMin = height;
		//iterate over the skirt to find the place where the distance between
		//the skirt and the heights is smallest
		for(int i = 0; i < piece.getWidth(); i++){
			distance = heights[x+i] - skirt[i];
			if(i == 0 || distance > distanceMin){
				distanceMin = distance;  
				dropHeightMax = heights[x+i]-skirt[i];
			}
		}
		return dropHeightMax;
	}
	
	
	/**
	 Returns the height of the given column --
	 i.e. the y value of the highest block + 1.
	 The height is 0 if the column contains no blocks.
	*/
	public int getColumnHeight(int x) {
		return heights[x];
	}
	
	
	/**
	 Returns the number of filled blocks in
	 the given row.
	*/
	public int getRowWidth(int y) {
		 return widths[y];
	}
	
	
	/**
	 Returns true if the given block is filled in the board.
	 Blocks outside of the valid width/height area
	 always return true.
	*/
	public boolean getGrid(int x, int y) {
		//try returning the value of the grid spot
		try{
			if(grid[x][y]) return true;
			else return false;
		}
		//if out of bounds, return true
		catch(ArrayIndexOutOfBoundsException exception) {
			return true;			
		}
	}
	
	
	public static final int PLACE_OK = 0;
	public static final int PLACE_ROW_FILLED = 1;
	public static final int PLACE_OUT_BOUNDS = 2;
	public static final int PLACE_BAD = 3;
	
	/**
	 Attempts to add the body of a piece to the board.
	 Copies the piece blocks into the board grid.
	 Returns PLACE_OK for a regular placement, or PLACE_ROW_FILLED
	 for a regular placement that causes at least one row to be filled.
	 
	 <p>Error cases:
	 A placement may fail in two ways. First, if part of the piece may falls out
	 of bounds of the board, PLACE_OUT_BOUNDS is returned.
	 Or the placement may collide with existing blocks in the grid
	 in which case PLACE_BAD is returned.
	 In both error cases, the board may be left in an invalid
	 state. The client can use undo(), to recover the valid, pre-place state.
	*/
	public int place(Piece piece, int x, int y) {
		// flag !committed problem
		if (!committed) throw new RuntimeException("place commit problem");
		this.backup = updateBackup();
		int result;
		// check that the piece doesn't go over the board's top, bottom or sides
		if (y < 0 || (y + piece.getHeight()) > height || x < 0 || (x + piece.getWidth()) > width){
			result = PLACE_OUT_BOUNDS;
			sanityCheck();
			return result;
		}
		else {
			//iterate over figure, check fields and modify them. If occupied return PLACE_BAD 
			TPoint[] points = piece.getBody(); boolean rowFilled = false;
			for (TPoint point : points){
				if (grid[point.x + x][point.y + y]) {
					result = PLACE_BAD;
					return result;
				}
				//if spot not occupied, place spot and update metrics
				else {
					committed = false;
					grid[point.x + x][point.y + y] = true;
					widths[point.y + y]++;
					//update heights and maxHeight if necessary
					if (heights[point.x + x] < 	point.y + y +1){
						heights[point.x + x] = point.y + y +1;
					}
					if (maxHeight < point.y + y + 1){
						maxHeight = point.y + y + 1;
					}
					if (widths[point.y + y] == width){
						rowFilled = true;
					}
				}
			}
			if (rowFilled) result = PLACE_ROW_FILLED;
			else result = PLACE_OK;
			
		}
		//grid[1][5] = true; // used for debugging sanitycheck()
		sanityCheck();
		return result;
	}
	
	
	/**
	 Deletes rows that are filled all the way across, moving
	 things above down. Returns the number of rows cleared.
	*/
	public int clearRows() {
		int rowsCleared = 0;
		int top = 1;
		committed = false;
		for (int bot = 0; bot < height; bot++, top++){
			if (widths[bot] == width){
				//first filled column triggers a loop where all rows now have to be copied
				rowsCleared++;
				//check if top has reached height
				while(bot < height){
					if(top >= height){
						//if top has reached height update with false
						for(int i = 0; i < width; i++){
							grid[i][bot] = false;
							widths[bot] = 0;
						}
					}
					else {
						//check if top is at a filled row, if so skip it
						if (widths[top] == width){
							rowsCleared++;
							bot--;
						}
						else {
							for(int i = 0; i < width; i++){
								grid[i][bot] = grid[i][top];
								widths[bot] = widths[top];
							}
						}
					}
					top++;
					bot++;
				}
				//for loop that updates heights and maxHeight
				int maxHeightTot = 0;
				for(int k = 0; k < width; k++) {
					int maxHeightL = 0;
					for(int l = 0; l < height; l++){
						if(grid[k][l] == true){
							if(l + 1 > maxHeightL) maxHeightL = l + 1;
							if(maxHeightL > maxHeightTot) maxHeightTot = maxHeightL;
						}
					}
					heights[k] = maxHeightL;
				}
				maxHeight = maxHeightTot;
				sanityCheck();
				return rowsCleared;
			}
		}
		sanityCheck();
		return rowsCleared;
	}



	/**
	 Reverts the board to its state before up to one place
	 and one clearRows();
	 If the conditions for undo() are not met, such as
	 calling undo() twice in a row, then the second undo() does nothing.
	 See the overview docs.
	*/
	public void undo() {
		if(committed) return;
		else{
			//go back to backup
			Board backupBoard = this.backup;
			System.arraycopy(backupBoard.grid, 0, this.grid, 0, backupBoard.grid.length);
			System.arraycopy(backupBoard.heights, 0, this.heights, 0, backupBoard.heights.length);
			System.arraycopy(backupBoard.widths, 0, this.widths, 0, backupBoard.widths.length);
			this.maxHeight = backupBoard.maxHeight;
			committed = true;	
		}
		sanityCheck();
	}
	
	
	/**
	 Puts the board in the committed state.
	*/
	public void commit() {
		if(committed) return;
		else{
			backup = updateBackup();
			committed = true;	
		}
	}

	/**
	 Creates a backup of the current state
	*/
	private Board updateBackup() {
		Board backupBoard = new Board(width,height);
		for (int i = 0; i < this.grid.length; i++){
			System.arraycopy(this.grid[i], 0, backupBoard.grid[i], 0, this.grid[i].length);
		}
		System.arraycopy(this.heights, 0, backupBoard.heights, 0, this.heights.length);
		System.arraycopy(this.widths, 0, backupBoard.widths, 0, this.widths.length);
		backupBoard.maxHeight = this.maxHeight;
		return backupBoard;
	}

	
	/*
	 Renders the board state as a big String, suitable for printing.
	 This is the sort of print-obj-state utility that can help see complex
	 state change over time.
	 (provided debugging utility) 
	 */
	public String toString() {
		StringBuilder buff = new StringBuilder();
		for (int y = height-1; y>=0; y--) {
			buff.append('|');
			for (int x=0; x<width; x++) {
				if (getGrid(x,y)) buff.append('+');
				else buff.append(' ');
			}
			buff.append("|\n");
		}
		for (int x=0; x<width+2; x++) buff.append('-');
		return(buff.toString());
	}
}


