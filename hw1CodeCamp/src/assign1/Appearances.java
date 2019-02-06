package assign1;

import java.util.*;

public class Appearances {
	
	/**
	 * Returns the number of elements that appear the same number
	 * of times in both collections. Static method. (see handout).
	 * @return number of same-appearance elements
	 */
	public static <T> int sameCount(Collection<T> a, Collection<T> b) {
		//initialize counter
		List<T> equalElements = new ArrayList<T>();
		//iterate each element of a to compare with each element of b
		Iterator<T> itA = a.iterator();
		while (itA.hasNext()) {
			T thisA = itA.next();
			Iterator<T> itB = b.iterator();
			while (itB.hasNext()) {
				//if it founds to equal elements, increase count
				T thisB = itB.next();
				if(thisA.equals(thisB)){
					//check if the case has already been accounted for
					boolean repeated = false;
					Iterator<T> itEqualElements = equalElements.iterator();
					for(int i = 0; i < equalElements.size(); i++){
						repeated = repeated || itEqualElements.next().equals(thisA);
					}
					// if the case has not been accounted for, add it to the list of equal elements
					if(!repeated){
					equalElements.add(thisA);
					}
					//no need to keep checking B if we already found the element is repeated at least once
					break;
				}
			}
		}	
		//return the size of the list of repeated elements
		int size = equalElements.size();
		return size;
	}
}
