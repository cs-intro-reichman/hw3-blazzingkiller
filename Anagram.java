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
	
		if (str1.length() != str2.length()) return false;
	
		int[] charCounts = new int[256]; 
		for (int i = 0; i < str1.length(); i++) {
			charCounts[str1.charAt(i)]++;
			charCounts[str2.charAt(i)]--;
		}
		for (int count : charCounts) {
			if (count != 0) return false; 
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
		// Convert the input string to a character array
		char[] chars = str.toCharArray();
		
		// Shuffle the array using a simple approach (Fisher-Yates shuffle)
		for (int i = 0; i < chars.length; i++) {
			// Generate a random index to swap with the current element
			int randomIndex = (int) (Math.random() * chars.length);
			
			// Swap characters at positions i and randomIndex
			char temp = chars[i];
			chars[i] = chars[randomIndex];
			chars[randomIndex] = temp;
		}
		
		// Convert the shuffled array back to a string
		return new String(chars);
	}
	
	
}
	
