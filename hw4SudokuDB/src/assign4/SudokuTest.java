package assign4;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SudokuTest {
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
	Sudoku sudoku;

	@Before
	public void setUp() throws Exception {
		//sudoku = new Sudoku(hardGrid);
		sudoku = new Sudoku(easyGrid);
		
		//make Spot and Pair public for testing
	}

	@Test
	public void testSetSpot() {
		//filled spot
		assertFalse(sudoku.spotGrid[0][0].setSpot(3));
		//unfilled spot
		assertTrue(sudoku.spotGrid[2][0].setSpot(3));
	}
	
	@Test
	public void testCleanSpot() {
		assertEquals(sudoku.spotGrid[0][0].cleanSpot(),1);
		assertEquals(sudoku.spotGrid[2][0].cleanSpot(),2);
	}
	
	@Test
	public void testGetPossible() {
		assertEquals(sudoku.spotGrid[0][1].getPossible(1),6);
		assertEquals(sudoku.spotGrid[3][2].getPossible(1),1);
	}
	
	@Test
	public void cleanSpotGrid() {
		assertEquals(sudoku.cleanSpotGrid(),1);
	}

}
