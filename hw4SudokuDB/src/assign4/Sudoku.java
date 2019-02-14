package assign4;

import java.util.*;

/*
 * Encapsulates a Sudoku grid to be solved.
 * CS108 Stanford.
 */
public class Sudoku {
	int[][] grid;
	Sudoku.Spot[][] spotGrid;
	Pair[] gridOrder;
	int solutionsFound;
	String firstSolution;
	long timecheck;

	// Provided easy 1 6 grid
	// (can paste this text into the GUI too)
	public static final int[][] easyGrid = Sudoku.stringsToGrid(
			"1 6 4 0 0 0 0 0 2",
			"2 0 0 4 0 3 9 1 0",
			"0 0 5 0 8 0 4 0 7",
			"0 9 0 0 0 6 5 0 0",
			"5 0 0 1 0 2 0 0 8",
			"0 0 8 9 0 0 0 3 0",
			"8 0 9 0 4 0 2 0 0",
			"0 7 3 5 0 9 0 0 1",
			"4 0 0 0 0 0 6 7 9");


	// Provided medium 5 3 grid
	public static final int[][] mediumGrid = Sudoku.stringsToGrid(
			"530070000",
			"600195000",
			"098000060",
			"800060003",
			"400803001",
			"700020006",
			"060000280",
			"000419005",
			"000080079");

	// Provided hard 3 7 grid
	// 1 solution this way, 6 solutions if the 7 is changed to 0
	public static final int[][] hardGrid = Sudoku.stringsToGrid(
			"3 7 0 0 0 0 0 8 0",
			"0 0 1 0 9 3 0 0 0",
			"0 4 0 7 8 0 0 0 3",
			"0 9 3 8 0 0 0 1 2",
			"0 0 0 0 4 0 0 0 0",
			"5 2 0 0 0 6 7 9 0",
			"6 0 0 0 2 1 0 4 0",
			"0 0 0 5 3 0 9 0 0",
			"0 3 0 0 0 0 0 5 1");


	public static final int SIZE = 9;  // size of the whole 9x9 puzzle
	public static final int PART = 3;  // size of each 3x3 part
	public static final int MAX_SOLUTIONS = 100; 

	// Provided various static utility methods to
	// convert data formats to int[][] grid.

	/**
	 * Returns a 2-d grid parsed from strings, one string per row.
	 * The "..." is a Java 5 feature that essentially
	 * makes "rows" a String[] array.
	 * (provided utility)
	 * @param rows array of row strings
	 * @return grid
	 */
	public static int[][] stringsToGrid(String... rows) {
		int[][] result = new int[rows.length][];
		for (int row = 0; row<rows.length; row++) {
			result[row] = stringToInts(rows[row]);
		}
		return result;
	}


	/**
	 * Given a single string containing 81 numbers, returns a 9x9 grid.
	 * Skips all the non-numbers in the text.
	 * (provided utility)
	 * @param text string of 81 numbers
	 * @return grid
	 */
	public static int[][] textToGrid(String text) {
		int[] nums = stringToInts(text);
		if (nums.length != SIZE*SIZE) {
			throw new RuntimeException("Needed 81 numbers, but got:" + nums.length);
		}

		int[][] result = new int[SIZE][SIZE];
		int count = 0;
		for (int row = 0; row<SIZE; row++) {
			for (int col=0; col<SIZE; col++) {
				result[row][col] = nums[count];
				count++;
			}
		}
		return result;
	}


	/**
	 * Given a string containing digits, like "1 23 4",
	 * returns an int[] of those digits {1 2 3 4}.
	 * (provided utility)
	 * @param string string containing ints
	 * @return array of ints
	 */
	public static int[] stringToInts(String string) {
		int[] a = new int[string.length()];
		int found = 0;
		for (int i=0; i<string.length(); i++) {
			if (Character.isDigit(string.charAt(i))) {
				a[found] = Integer.parseInt(string.substring(i, i+1));
				found++;
			}
		}
		int[] result = new int[found];
		System.arraycopy(a, 0, result, 0, found);
		return result;
	}


	public static void main(String[] args) {
		Sudoku sudoku;
		sudoku = new Sudoku(hardGrid);
		System.out.println(sudoku); // print the raw problem
		int count = sudoku.solve(); //solves
		System.out.println("solutions:" + count);
		
		System.out.println("elapsed:" + sudoku.getElapsed() + "ms \n");
		System.out.println(sudoku.getSolutionText());
	}


	/**
	 * Sets up sudoku based on the given ints.
	 */
	public Sudoku(int[][] ints) {
		// YOUR CODE HERE
		this.timecheck = 0;
		this.solutionsFound = 0;
		this.grid = ints;
		//Initialize the spotGrid
		spotGrid = new Spot[SIZE][SIZE];
		for(int i = 0; i < SIZE; i++) {
			for(int j = 0; j < SIZE; j++) {
				spotGrid[i][j] = new Spot(i,j);
			}
		}
		//initialize an array with the x,y values of the spots by order of possible solutions
		gridOrder = new Pair[81];
		int count = 0;
		//order the spots based on how many possible numbers they have
		for(int k = 1; k < 10; k++) {
			for(int i = 0; i < SIZE; i++) {
				for(int j = 0; j < SIZE; j++) {
					if(spotGrid[i][j].getPossibilitySize() == k){
						gridOrder[count] = new Pair(i,j);
						count++;
					}
				}
			}
		}
	}
	/**
	 * Private class Pair stores the possible values of a spot
	 * @param x,y location of the spot
	 */
	private class Pair{
		int x;
		int y;
		public Pair(int x, int y){
			this.x = x;
			this.y = y;
		}
	}

	//spot inner class 
	private class Spot{
		int spotX; int spotY;
		HashSet<Integer> possible;

		/**
		 * Private class Spot stores the possible values of a spot
		 * @param x,y location of the spot and ints (original int grid)
		 */
		//spot constructor 
		public Spot(int x, int y){
			spotX = x;
			spotY = y;
			if(grid[spotX][spotY] == 0) {
				possible = new HashSet<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9));
			}
			else {
				possible = new HashSet<Integer>();
				possible.add(grid[spotX][spotY]);
			}
		}

		/**cleanSpot method updates the possible values
		*@return returns 0 if no possible values (error), returns 1 if only 1 possible value, returns 2 if more than 1 possible value*/
		public int cleanSpot(){
			//check line
			for(int i = 0; i < SIZE; i++){
				if(spotGrid[spotX][i].getPossibilitySize() == 1 
						&& possible.contains(spotGrid[spotX][i].getPossible(1)) && (i != spotY)){
					possible.remove(spotGrid[spotX][i].getPossible(1));
				}
			}
			//check column
			for(int i = 0; i < SIZE; i++){
				if(spotGrid[i][spotY].getPossibilitySize() == 1 
						&& possible.contains(spotGrid[i][spotY].getPossible(1)) && (i != spotX)){
					possible.remove(spotGrid[i][spotY].getPossible(1));
				}
			}
			//check sub grid
			int mini = spotX / PART * PART;
			int minj = spotY / PART * PART;
			for(int i = mini; i < (mini+3); i++){
				for(int j = minj; j < (minj + PART); j++){
					if(spotGrid[i][j].getPossibilitySize() == 1 
							&& possible.contains(spotGrid[i][j].getPossible(1)) && ((i != spotX) && (j != spotY))){
						possible.remove(spotGrid[i][j].getPossible(1));
					}
				}
			}
			if(possible.size() < 1) return 0;
			else if(possible.size() == 1) return 1;
			else return 2;
		}

		/** setSpot(int value) method tries to set a spot to a value
		 * @param int value: the value you want to try 
		*returns false if value is not available
		*returns true if valid */
		public boolean setSpot(int value){
			if (possible.contains(value)){
				possible.clear();
				possible.add(value);
				return true;
			}
			else return false;
		}

		/**getPossibilitySize() returns the count of possible values for a spot */
		public int getPossibilitySize(){
			return possible.size();
		}

		/** getPossibilities() returns the HashSet of possible values for a spot */
		public HashSet<Integer> getPossibilities(){
			return possible;
		}

		/**getPossible returns the ith value a spot will admit
		 *@param value of ith
		 *@returns ith possible value for the spot */
		public int getPossible(int ith){
			int counter = 0;
			if(ith > possible.size()) return 0;
			for (int i = 0; i < SIZE + 1; i++) {
				if(possible.contains(i))
					counter++;
				if(counter == ith) return i;
			}
			return 0;
		}
	}

	/**
	 * Solves the puzzle, invoking the underlying recursive search.
	 */
	public int solve() {
		timecheck = this.getElapsed();
		pon();
		return solutionsFound; 
	}

	/**
	 * Recursively tries to solve the the puzzle
	 */
	public void pon() {
		int test = 1;
		while(true) {

			//check if we've found too many solutions
			if(solutionsFound > 100) return;
			//find the first element without at least 2 possibilities
			int smallestX = -1; int smallestY = -1;
			for(int i = 0; i < 81; i++){
				if(spotGrid[gridOrder[i].x][gridOrder[i].y].getPossibilitySize() > 1) { 
					smallestX = gridOrder[i].x;
					smallestY = gridOrder[i].y;
					break;
				}
			}
			//if there are no more elements with at least 2 possibilities
			// YEI! WE FOUND A SOLUTION!!
			if (smallestX == -1){
				solutionsFound++;
				if(solutionsFound == 1) recordSolution();
				return;
			}

			//when found the first element, check if we've run out of numbers to test
			if (spotGrid[smallestX][smallestY].getPossibilitySize() < test) {
				return;
			}
			//if all good, try setting the next value for such spot
			//copy current set of possibilities, so if the cleaning fails we can restore
			Spot[][] backup = new Spot[9][9];
			backup = backupSpotGrid();

			spotGrid[smallestX][smallestY].setSpot(spotGrid[smallestX][smallestY].getPossible(test));
			//if when cleaning the grid we found no error, we try the next item
			if (spotGrid[smallestX][smallestY].getPossible(1) == 0 || cleanSpotGrid() != 1){
				restoreBackup(backup);
				test++;
			}
			else {
				pon();
				//if the pon returns, try the next item
				//restore the possibilities
				restoreBackup(backup);
				test++;
			}
		}
	}

	/** stores a backup for the current spot grid*/
	public Sudoku.Spot[][] backupSpotGrid(){
		Sudoku.Spot[][] backup = new Sudoku.Spot[9][9];
		//iterates to initialize the spots in backup
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				backup[i][j] = new Spot(i,j);
			}
		}
		//iterates over the grid to do a backup
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				Iterator iterator = spotGrid[i][j].possible.iterator();
				backup[i][j].possible.clear();
				while (iterator.hasNext()) {
					backup[i][j].possible.add((Integer) iterator.next());  
				}

				backup[i][j].spotX = spotGrid[i][j].spotX;
				backup[i][j].spotY = spotGrid[i][j].spotY;
			}
		}
		return backup;
	}

	/**restores grid to backup stage */
	public void restoreBackup(Spot[][] backup){
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				Iterator<Integer> iterator = backup[i][j].possible.iterator();
				spotGrid[i][j].possible.clear();
				while (iterator.hasNext()) {
					spotGrid[i][j].possible.add((Integer) iterator.next());  
				}
				spotGrid[i][j].spotX = backup[i][j].spotX;
				spotGrid[i][j].spotY = backup[i][j].spotY;
			}
		}
		return;
	}

	/** records the first solution for printing */
	public void recordSolution() {
		String solution = "";
		for (int i = 0; i < 9 ; i++){
			for (int j = 0; j < 9 ; j++){
				solution = solution + spotGrid[i][j].getPossible(1)+ " ";
			}
			solution = solution+"\n";
		}
		firstSolution = solution;
	}

	/** returns the string with the first solution found */
	public String getSolutionText() {
		return firstSolution;
	}

	/** when first invoked returns the current time
	 * when invoked for a second time returns the difference between the first time it was invoked and now*/
	public long getElapsed() {
		if(timecheck == 0) return System.currentTimeMillis();
		else return System.currentTimeMillis() - timecheck;
	}

	/** checks the possibilities for all spots and updates based on current numbers
	 * @return 0 if there's an inconsistency in the board, else returns 1*/
	public int cleanSpotGrid() {
		int error = 1;
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				error = spotGrid[i][j].cleanSpot();
				if (error == 0) return 0;
			}
		}
		return 1;
	}
	
	public String toString() {
		String gridString = "";
		for (int i = 0; i < 9 ; i++){
			for (int j = 0; j < 9 ; j++){
				gridString += grid[i][j] + " ";
			}
			gridString = gridString + "\n";
		}
		 return gridString;
	}
	

}
