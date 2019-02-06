// HW1 2-d array Problems
// CharGrid encapsulates a 2-d grid of chars and supports
// a few operations on the grid.

package assign1;

import java.lang.Math;

public class CharGrid {
	private char[][] grid;

	/**
	 * Constructs a new CharGrid with the given grid.
	 * Does not make a copy.
	 * @param grid
	 */
	public CharGrid(char[][] grid) {
		this.grid = grid;
	}
	
	/**
	 * Returns the area for the given char in the grid. (see handout).
	 * @param ch char to look for
	 * @return area for given char
	 */
	public int charArea(char ch) {
		int minX = 0; int minY = 0; int maxX = 0; int maxY = 0; boolean check = false;
		//iterate over grid
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[1].length; j++) {
				//if we find ch then update min and max
				if (grid[i][j] == ch) {
					//for the first check found, initialize the value of minX and minY
					if (!check) {
						minX = i;
						minY = j;
					}
					check = true;
					minX = Math.min(minX,i);
					minY = Math.min(minY,j);
					maxX = Math.max(maxX,i);
					maxY = Math.max(maxY,j);
					
				}
			}
		}
		//calculate area
		int area = (maxX - minX + 1) * (maxY - minY +1); 
		return area;
	}
	
	/**
	 * Returns the count of '+' figures in the grid (see handout).
	 * @return number of + in grid
	 */
	public int countPlus() {
		int pluses = 0;
		// iterate over grid excluding borders to find a center
		for (int i = 1; i <= grid.length - 1; i++) {
			for (int j = 1; j < grid[1].length - 1; j++) {
				int k;
				int l;
				int m;
				int n;
				// check upper arm
				for (k = 0; k <= i; k++) {
					if (grid[i][j] != grid[i - k][j]) {
						break;
					}
				}
				// check lower arm
				for (l = 0; l < (grid.length - i); l++) {
					if (grid[i][j] != grid[i + l][j]) {
						break;
					}
				}
				// check left arm
				for (m = 0; m <= j; m++) {
					if (grid[i][j] != grid[i][j - m]) {
						break;
					}
				}
				// check right arm
				for (n = 0; n < (grid[0].length - j); n++) {
					if (grid[i][j] != grid[i][j+n]) {
						break;
					}
				}
				// if all arms are equal size and bigger than 0, you've found a "+"
				if (k == l && m == n && k == m && k > 1) {
					pluses++;
				}
			}
		}
		return pluses;
	}

}
