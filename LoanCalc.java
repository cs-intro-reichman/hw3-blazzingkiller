// Computes the periodical payment necessary to pay a given loan.
public class LoanCalc {

    static double epsilon = 0.001;  // Approximation accuracy
    static int iterationCounter;   // Number of iterations

    // Gets the loan data and computes the periodical payment.
    // Expects to get three command-line arguments: loan amount (double),
    // interest rate (double, as a percentage), and number of payments (int).
    public static void main(String[] args) {
        // Get loan data
        double loan = Double.parseDouble(args[0]);
        double rate = Double.parseDouble(args[1]);
        int n = Integer.parseInt(args[2]);

        System.out.println("Loan = " + loan + ", interest rate = " + rate + "%, periods = " + n);

        // Compute payment using brute force search
        System.out.print("\nPeriodical payment, using brute force: ");
        double bruteForcePayment = bruteForceSolver(loan, rate, n, epsilon);
        if (bruteForcePayment < 0) {
            System.out.println("Invalid input.");
        } else {
            System.out.println((int) bruteForcePayment);
            System.out.println("Number of iterations: " + iterationCounter);
        }

        // Compute payment using bisection search
        System.out.print("\nPeriodical payment, using bisection search: ");
        double bisectionPayment = bisectionSolver(loan, rate, n, epsilon);
        if (bisectionPayment < 0) {
            System.out.println("Invalid input.");
        } else {
            System.out.println((int) bisectionPayment);
            System.out.println("Number of iterations: " + iterationCounter);
        }
    }

    // Computes the ending balance of a loan, given the loan amount, the periodical
    // interest rate (as a percentage), the number of periods (n), and the periodical payment.
	private static double endBalance(double loan, double rate, int n, double payment) {
		double balance = loan; // Start with the total loan
		double monthlyRate = rate / 100.0; // Convert rate from percentage to decimal
	
		for (int i = 0; i < n; i++) {
			balance = balance + balance * monthlyRate - payment; // Add interest and subtract payment
		}
	
		return balance; // Return the remaining balance after n payments
	}
	

    // Uses sequential search to compute an approximation of the periodical payment
    // that will bring the ending balance of a loan close to 0.
    public static double bruteForceSolver(double loan, double rate, int n, double epsilon) {
		iterationCounter = 0; // Reset the iteration counter
		double payment = 0.01; // Start with a small positive payment
		double balance;
	
		// Edge case: Invalid input
		if (loan <= 0 || rate < 0 || n <= 0) {
			return -1; // Indicating invalid input
		}
	
		while (payment < loan * 2) { // Safety condition to prevent infinite loop
			balance = endBalance(loan, rate, n, payment);
			iterationCounter++;
	
			if (Math.abs(balance) <= epsilon) { // Check if the balance is close enough to 0
				return payment; // Found a valid payment
			}
			payment += 0.01; // Increment the payment slightly for the next iteration
		}
	
		return -1; // Return -1 to indicate that no valid payment was found
	}
	

    // Uses bisection search to compute an approximation of the periodical payment
    // that will bring the ending balance of a loan close to 0.
    public static double bisectionSolver(double loan, double rate, int n, double epsilon) {
        iterationCounter = 0; // Reset the iteration counter

        double low = 0; // Minimum possible payment
        double high = loan; // Maximum possible payment
        double mid;
        double balance;

        while (high - low > epsilon) {
            mid = (low + high) / 2; // Middle point of the range
            balance = endBalance(loan, rate, n, mid);
            iterationCounter++;

            if (Math.abs(balance) <= epsilon) { // Check if the balance is close enough to 0
                return mid;
            } else if (balance > 0) { // If balance is positive, the payment is too low
                low = mid;
            } else { // If balance is negative, the payment is too high
                high = mid;
            }
        }

        return (low + high) / 2; // Return the approximate payment
    }
}
