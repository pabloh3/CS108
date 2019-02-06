// Test cases for CharGrid -- a few basic tests are provided.
package assign1;

import static org.junit.Assert.*;
import org.junit.Test;

public class CharGridTest {
	
	@Test
	public void testCharArea1() {
		char[][] grid = new char[][] {
				{'a', 'y', ' '},
				{'x', 'a', 'z'},
			};
		
		
		CharGrid cg = new CharGrid(grid);
				
		assertEquals(4, cg.charArea('a'));
		assertEquals(1, cg.charArea('z'));
	}
	
	
	@Test
	public void testCharArea2() {
		char[][] grid = new char[][] {
				{'c', 'a', ' '},
				{'b', ' ', 'b'},
				{' ', ' ', 'a'}
			};
		
		CharGrid cg = new CharGrid(grid);
		
		assertEquals(6, cg.charArea('a'));
		assertEquals(3, cg.charArea('b'));
		assertEquals(1, cg.charArea('c'));
	}
	
	@Test
	public void testcountPlus1() {
		char[][] grid = new char[][] {
				{' ', ' ', 'p', ' ', ' ', ' ', ' ', ' ', ' '},
				{' ', ' ', 'p', ' ', ' ', ' ', ' ', 'x', ' '},
				{'p', 'p', 'p', 'p', 'p', ' ', 'x', 'x', 'x'},
				{' ', ' ', 'p', ' ', ' ', 'y', ' ', 'x', ' '},
				{' ', ' ', 'p', ' ', 'y', 'y', 'y', ' ', ' '},
				{'z', 'z', 'z', 'z', 'z', 'y', 'z', 'z', 'z'},
				{' ', ' ', 'x', 'x', ' ', 'y', ' ', ' ', ' '},
			};
		
		CharGrid cg = new CharGrid(grid);
		
		assertEquals(2, cg.countPlus());
	}
	
	@Test
	public void testcountPlus2() {
		char[][] grid = new char[][] {
				{' '}
			};
		
		CharGrid cg = new CharGrid(grid);
		
		assertEquals(0, cg.countPlus());
	}
	
	@Test
	public void testcountPlus3() {
		char[][] grid = new char[][] {
				{' ', 'p', 'p', ' '},
				{'p', 'p', 'p', ' '},
				{'p', 'p', 'p', 'p'},

			};
		
		CharGrid cg = new CharGrid(grid);
		
		assertEquals(1, cg.countPlus());
	}
	
	@Test
	public void testcountPlus4() {
		char[][] grid = new char[][] {
				{' ', ' ', 'p', ' ', ' ', ' ', ' ', ' ', ' '},
				{' ', ' ', 'p', ' ', ' ', ' ', ' ', 'x', ' '},
				{'p', 'p', 'p', 'p', 'p', 'y', 'x', '0', 'x'},
				{' ', ' ', 'p', ' ', ' ', 'y', ' ', 'x', ' '},
				{' ', ' ', 'p', 'y', 'y', ' ', 'y', 'y', ' '},
			};
		
		CharGrid cg = new CharGrid(grid);
		
		assertEquals(1, cg.countPlus());
	}
	
	@Test
	public void testcountPlus5() {
		char[][] grid = new char[][] {
				{' ', ' ', 'p', ' ', ' ', ' ', ' ', ' ', ' '},
				{' ', ' ', 'p', ' ', ' ', ' ', ' ', 'x', ' '},
				{'p', 'p', 'p', 'p', 'p', 'p', 'x', 'x', 'x'},
				{' ', ' ', 'p', ' ', ' ', 'y', ' ', 'x', ' '},
				{' ', ' ', 'p', 'y', 'y', ' ', 'y', 'y', ' '},
			};
		
		CharGrid cg = new CharGrid(grid);
		
		assertEquals(1, cg.countPlus());
	}	
}
