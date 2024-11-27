// Computes the periodical payment necessary to pay a given loan.
public class LoanCalc {

    static double epsilon = 0.001;  // Approximation accuracy
    static int iterationCounter;    // Number of iterations

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
            System.out.println(String.format("%.2f", bruteForcePayment));  // Round to 2 decimal places
            System.out.println("Number of iterations: " + iterationCounter);
        }

        // Compute payment using bisection search
        System.out.print("\nPeriodical payment, using bisection search: ");
        double bisectionPayment = bisectionSolver(loan, rate, n, epsilon);
        if (bisectionPayment < 0) {
            System.out.println("Invalid input.");
        } else {
            System.out.println(String.format("%.2f", bisectionPayment));  // Round to 2 decimal places
            System.out.println("Number of iterations: " + iterationCounter);
        }
    }

    // Computes the ending balance of a loan, given the loan amount, the periodical
    // interest rate (as a percentage), the number of periods (n), and the periodical payment.
    private static double endBalance(double loan, double rate, int n, double payment) {
        double balance = loan; 
        double annualRate = rate / 100.0;  // Convert percentage to decimal

        // Apply interest and payments for each period
        for (int i = 0; i < n; i++) {
            balance = (balance - payment) + balance * annualRate;  // Subtract payment, then apply interest
        }

        return balance;  // Return remaining balance
    }

    // Uses sequential search to compute an approximation of the periodical payment
    // that will bring the ending balance of a loan close to 0.
    public static double bruteForceSolver(double loan, double rate, int n, double epsilon) {
        iterationCounter = 0;  // Use the class-level iterationCounter
        
        double payment = loan / n;  // Initial payment estimate
        double balance = loan;      // Starting loan balance
        
        // Edge case: Invalid input
        if (loan <= 0 || rate < 0 || n <= 0 || epsilon <= 0) {
            return -1; 
        }
        
        // Brute-force search for payment that brings balance close to 0
        while (balance > epsilon) {
            iterationCounter++;
            balance = loan;  // Reset balance to the loan amount
            
            // Loop over the number of periods
            for (int i = 0; i < n; i++) {
                balance -= payment;              // Subtract payment from balance
                balance += balance * (rate / 100.0);  // Apply interest rate yearly
            }
            
            // If the balance is still greater than epsilon, adjust the payment
            if (balance > epsilon) {
                payment += epsilon;  // Adjust payment if balance is still above epsilon
            } else {
                return payment;      // Return the payment amount if balance is within epsilon
            }
            
            // If payment exceeds loan, break the loop to prevent infinite loop
            if (payment > loan) {
                break;
            }
        }
        return payment;  // Return the final payment value
    }

    // Uses bisection search to compute an approximation of the periodical payment
    // that will bring the ending balance of a loan close to 0.
    public static double bisectionSolver(double loan, double rate, int n, double epsilon) {
        iterationCounter = 0;  // Reset the iteration counter

        double low = loan / n; 
        double high = loan; 
        double mid;
        double balance;

        while (high - low > epsilon) {
            mid = (low + high) / 2; 
            balance = endBalance(loan, rate, n, mid);
            iterationCounter++;

            if (Math.abs(balance) <= epsilon) { 
                return mid;
            } else if (balance > 0) { 
                low = mid;  // Increase payment
            } else { // If balance is negative, the payment is too high
                high = mid;  // Decrease payment
            }
        }

        return (low + high) / 2; // Return the approximate payment
    }
}
