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
		// Preprocess both strings
		str1 = preProcess(str1);
		str2 = preProcess(str2);
	
		// Remove spaces from both strings
		str1 = removeSpaces(str1);
		str2 = removeSpaces(str2);
	
		// If lengths don't match after preprocessing and space removal, they're not anagrams
		if (str1.length() != str2.length()) return false;
	
		// Use a single array to count occurrences of characters
		int[] charCounts = new int[256]; // Full ASCII range
	
		// Increment counts for str1 and decrement counts for str2
		for (int i = 0; i < str1.length(); i++) {
			charCounts[str1.charAt(i)]++;
			charCounts[str2.charAt(i)]--;
		}
	
		// Check if all counts are zero
		for (int count : charCounts) {
			if (count != 0) return false; // Mismatch in character counts
		}
	
		return true; // Strings are anagrams
	}

	public static String removeSpaces(String str) {
		String result = ""; // Start with an empty string
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) != ' ') { // Skip spaces
				result += str.charAt(i); // Append non-space characters
			}
		}
		return result;
	}
	
	
	
	
    // Function to preprocess a string: Remove special characters and convert to lowercase
	public static String preProcess(String str) {
		String result = ""; // Initialize an empty string
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			if (ch == ' ' || (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')) {
				// Convert uppercase to lowercase and append to result
				result += (ch >= 'A' && ch <= 'Z') ? (char) (ch + 32) : ch;
			}
		}
		return result;
	}
	
	

    // Function to generate a random anagram
	public static String randomAnagram(String str) {
		char[] chars = str.toCharArray(); // Convert string to array of characters
		boolean[] used = new boolean[chars.length]; // Track used characters
		String result = ""; // Start with an empty string
	
		while (result.length() < chars.length) {
			int randomIndex = (int) (Math.random() * chars.length); // Pick a random index
			if (!used[randomIndex]) { // If this character isn't used yet
				result += chars[randomIndex]; // Add it to the result
				used[randomIndex] = true; // Mark it as used
			}
		}
	
		return result;
	}
	
}
	
