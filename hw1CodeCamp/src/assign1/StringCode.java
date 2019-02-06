package assign1;

import java.lang.StringBuilder;

// CS108 HW1 -- String static methods

public class StringCode {

	/**
	 * Given a string, returns the length of the largest run.
	 * A a run is a series of adajcent chars that are the same.
	 * @param str
	 * @return max run length
	 */
	public static int maxRun(String str) {
		int counter = 0; int max = 0;
		//iterate over the string
		for (int i = 0; i < str.length(); i++) {
			//exception for the first char
			if(i == 0) {
				counter = 1;
				max = 1;
			}
			else {
				//compare current char vs previous, update the char count
				if (str.charAt(i) == str.charAt(i-1)) {
					counter++;
				}
				else {
					counter = 1;
				}
				//compare current run vs largest run and updates the max
				if (counter > max) {
					max = counter;
				}
			}

		}
		return max;
	}

	
	/**
	 * Given a string, for each digit in the original string,
	 * replaces the digit with that many occurrences of the character
	 * following. So the string "a3tx2z" yields "attttxzzz".
	 * @param str
	 * @return blown up string
	 */
	public static String blowup(String str) {	
		StringBuilder newString = new StringBuilder();
		//iterate over the string
		for (int i = 0; i < str.length(); i++) {
			//if it's a digit and not the last char
			if(Character.isDigit(str.charAt(i)) && i < str.length() - 1) {
				//append the following char as many times as the value of the digit
				for (int j = 0; j < Character.getNumericValue(str.charAt(i)); j++) {
					newString.append(str.charAt(i+1));
				}
			}
			//if the character is not a digit
			else if (!Character.isDigit(str.charAt(i))) {
				newString.append(str.charAt(i));
			}
			//case where the last character is a digit is ignored
		}
		return newString.toString(); // 
	}
	
}
