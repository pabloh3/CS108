// TabooTest.java
// Taboo class tests -- nothing provided.
package assign1;

import static org.junit.Assert.*;
import java.util.*;
import org.junit.Test;

public class TabooTest {

	@Test
	public void testNoFollow1() {
		List<String> listA = new ArrayList<String>(Arrays.asList("a","c","a","b"));
		Taboo<String> taboo1 = new Taboo<String>(listA); 
		
		Set<String> result1 = new HashSet<String>(Arrays.asList("c", "b")); 
		assertEquals(result1, taboo1.noFollow("a"));
		
		Set<String> result2 = new HashSet<String>(Arrays.asList("a"));
		assertEquals(result2, taboo1.noFollow("c"));
	}
	
	@Test
	public void testNoFollow2() {
		List<Integer> listB = new ArrayList<Integer>(Arrays.asList(1,2,3,1,2,3,null,2,5));
		Taboo<Integer> taboo2 = new Taboo<Integer>(listB); 
		
		Set<Integer> result1 = new HashSet<Integer>(Arrays.asList(3,5)); 
		assertEquals(result1, taboo2.noFollow(2));
		
		Set<Integer> result2 = new HashSet<Integer>(Arrays.asList()); 
		assertEquals(result2, taboo2.noFollow(5));
		
		Set<Integer> result3 = new HashSet<Integer>(Arrays.asList()); 
		assertEquals(result3, taboo2.noFollow(9));
	}

	@Test
	public void testReduce1() {
		List<String> listC = new ArrayList<String>(Arrays.asList("a","c","a","b","d"));
		Taboo<String> taboo3 = new Taboo<String>(listC);
		
		List<String> listToReduce1 = new ArrayList<String>(Arrays.asList("a","f","a","c","a","b"));
		List<String> result1 = new ArrayList<String>(Arrays.asList("a", "f", "a","a"));
		taboo3.reduce(listToReduce1);
		assertEquals(result1, listToReduce1);
		
		List<String> listToReduce2 = new ArrayList<String>(Arrays.asList("a","c","a","c","b"));
		List<String> result2 = new ArrayList<String>(Arrays.asList("a", "a"));
		taboo3.reduce(listToReduce2);
		assertEquals(result2, listToReduce2);
		
		List<String> listToReduce3 = new ArrayList<String>(Arrays.asList());
		List<String> result3 = new ArrayList<String>(Arrays.asList());
		taboo3.reduce(listToReduce3);
		assertEquals(result3, listToReduce3);
	}

	@Test
	public void testReduce2() {
		List<Integer> listD = new ArrayList<Integer>(Arrays.asList(1,1,3,2,7,2));
		Taboo<Integer> taboo4 = new Taboo<Integer>(listD);
		
		List<Integer> listToReduce1 = new ArrayList<Integer>(Arrays.asList(1,1,1,1,3));
		List<Integer> result1 = new ArrayList<Integer>(Arrays.asList(1));
		taboo4.reduce(listToReduce1);
		assertEquals(result1, listToReduce1);
		
		List<Integer> listToReduce2 = new ArrayList<Integer>(Arrays.asList());
		List<Integer> result2 = new ArrayList<Integer>(Arrays.asList());
		taboo4.reduce(listToReduce2);
		assertEquals(result2, listToReduce2);

	}
	
	
}
