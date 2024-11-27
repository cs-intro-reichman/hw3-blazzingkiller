/** Functions for checking if a given string is an anagram. */
public class Anagram {
    public static void main(String args[]) {
        // Tests the isAnagram function.
        System.out.println(isAnagram("silent", "listen"));  // true
        System.out.println(isAnagram("William Shakespeare", "I am a weakish speller")); // true
        System.out.println(isAnagram("Madam Curie", "Radium came")); // true
        System.out.println(isAnagram("Tom Marvolo Riddle", "I am Lord Voldemort")); // true

        // Tests the preProcess function.
        System.out.println(preProcess("What? No way!!!")); // "what no way"

        // Tests the randomAnagram function.
        System.out.println("silent and " + randomAnagram("silent") + " are anagrams.");

        // Stress test for randomAnagram
        String str = "1234567";
        boolean pass = true; // Assume the test passes
        for (int i = 0; i < 10; i++) {
            String randomAnagram = randomAnagram(str);
            System.out.println(randomAnagram);
            if (!isAnagram(str, randomAnagram)) {
                pass = false; // Test failed if not an anagram
                break;
            }
        }
        System.out.println(pass ? "test passed" : "test failed");
    }

    // Function to check if two strings are anagrams
	public static boolean isAnagram(String str1, String str2) {
		str1 = preProcess(str1);
		str2 = preProcess(str2);
		str1 = removeSpaces(str1);
		str2 = removeSpaces(str2);
	
		if (str1.length() != str2.length()) {
			return false;
		}
		String mutableStr2 = str2;
	
		for (int i = 0; i < str1.length(); i++) {
			char currentChar = str1.charAt(i);
			int indexInStr2 = mutableStr2.indexOf(currentChar);
	
			if (indexInStr2 == -1) {
				return false;
			}
	
			mutableStr2 = mutableStr2.substring(0, indexInStr2) + mutableStr2.substring(indexInStr2 + 1);
		}
	
		return true;
	}

	public static String removeSpaces(String str) {
		String result = ""; 
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) != ' ') { 
				result += str.charAt(i); 
			}
		}
		return result;
	}
	
    // Function to preprocess a string: Remove special characters and convert to lowercase
	public static String preProcess(String str) {
		String result = ""; 
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			if (ch == ' ' || (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')) {
				result += (ch >= 'A' && ch <= 'Z') ? (char) (ch + 32) : ch;
			}
		}
		return result;
	}

    // Function to generate a random anagram
	public static String randomAnagram(String str) {
		String result = "";  
		String remainingChars = str;  
		
		while (remainingChars.length() > 0) {
			int randomIndex = (int) (Math.random() * remainingChars.length());
			char randomChar = remainingChars.charAt(randomIndex);
			result += randomChar;
			remainingChars = remainingChars.substring(0, randomIndex) + remainingChars.substring(randomIndex + 1);
		}
		
		return result;
	}
	
	
}
	
