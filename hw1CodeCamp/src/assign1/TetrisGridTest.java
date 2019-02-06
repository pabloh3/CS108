package assign1;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.*;

public class TetrisGridTest {

	// Provided simple clearRows() test
	// width 2, height 3 grid
	@Test
	public void testClear1() {
		boolean[][] before =
			{	
				{true, true, false},
				{false, true, true}
			};

		boolean[][] after =
			{	
				{true, false, false},
				{false, true, false}
			};

		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();

		assertTrue( Arrays.deepEquals(after, tetris.getGrid()) );
	}

	@Test
	public void testClear2() {
		boolean[][] before =
			{	
				{true, false, false},
				{false, true, true}
			};

		boolean[][] after =
			{	
				{true, false, false},
				{false, true, true}
			};

		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();

		assertTrue( Arrays.deepEquals(after, tetris.getGrid()) );
	}

	@Test
	public void testClear3() {
		boolean[][] before =
			{	
				{true, true, true, false},
				{true, false, true, true}
			};

		boolean[][] after =
			{	
				{true, false, false, false},
				{false, true, false, false}
			};

		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();

		assertTrue( Arrays.deepEquals(after, tetris.getGrid()) );
	}


	@Test
	public void testClear4() {
		boolean[][] before =
			{	
				{true, true, true, false},
				{true, true, false, true},
				{true, false, true, true}
			};

		boolean[][] after =
			{	
				{true, true, false, false},
				{true, false, true, false},
				{false, true, true, false}
			};

		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();

		assertTrue( Arrays.deepEquals(after, tetris.getGrid()) );
	}

	@Test
	public void testClear5() {
		boolean[][] before =
			{	
				{true, true},
				{true, true}
			};

		boolean[][] after =
			{	
				{false, false},
				{false, false}
			};

		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();

		assertTrue( Arrays.deepEquals(after, tetris.getGrid()) );
	}

}
