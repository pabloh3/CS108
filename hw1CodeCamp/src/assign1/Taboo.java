/*
 HW1 Taboo problem class.
 Taboo encapsulates some rules about what objects
 may not follow other objects.
 (See handout).
*/
package assign1;

import java.util.*;

public class Taboo<T> {
	private List <T> rulesAll;
	
	/**
	 * Constructs a new Taboo using the given rules (see handout.)
	 * @param rules rules for new Taboo
	 */
	public Taboo(List<T> rules) {
		this.rulesAll = rules;
	}
	
	/**
	 * Returns the set of elements which should not follow
	 * the given element.
	 * @param elem
	 * @return elements which should not follow the given element
	 */
	public Set<T> noFollow(T elem) {
		ListIterator<T> it = rulesAll.listIterator(); 
		Set<T> set = new HashSet<T>();
		//iterate over rules, if you find the searched element, store the next item in set
		while (it.hasNext()) {
			T thisA = it.next();
			if (thisA == null) {
				//do nothing
			}
			else {
				if(thisA.equals(elem) && it.hasNext()){
					T thisB = it.next();
					//skip cases where next is null
					if (thisB == null){
						//do nothing
						it.previous();
					}
					else {
						set.add(thisB);
						it.previous();
					}
				}
			}
		}
		return set; 
	}

	/**
	 * Removes elements from the given list that
	 * violate the rules (see handout).
	 * @param list collection to reduce
	 */
	public void reduce(List<T> list) {
		Set<T> noFollows = new HashSet<T>(Arrays.asList()); 
		ListIterator<T> it = list.listIterator(); 
		//iterate over rules, if you find the searched element, store the next item in set
		while (it.hasNext()) {
			//get the elements that can't follow current element
			noFollows = this.noFollow(it.next());
			Iterator<T> itNoFollows = noFollows.iterator();
			//make sure this element is not the last one
			if(it.hasNext()){
				T elem = it.next();
				//iterate over the list of forbidden elements to check if we should remove
				while (itNoFollows.hasNext()) {
					if(elem == itNoFollows.next()){
						it.remove();
						break;
					}
				}
				it.previous();
			}
			else {
				return;

			}
		}
	}
}
