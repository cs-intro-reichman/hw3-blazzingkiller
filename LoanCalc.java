public class LoanCalc {

    static double epsilon = 0.001;  // Approximation accuracy
    static int iterationCounter;    // Number of iterations

    public LoanCalc() {}

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
            // Round the result to the nearest integer and print
            System.out.println((int) (bruteForcePayment));  
            System.out.println("Number of iterations: " + iterationCounter);
        }

        // Compute payment using bisection search
        System.out.print("\nPeriodical payment, using bisection search: ");
        double bisectionPayment = bisectionSolver(loan, rate, n, epsilon);
        if (bisectionPayment < 0) {
            System.out.println("Invalid input.");
        } else {
            // Round the result to the nearest integer and print
            System.out.println((int) (bisectionPayment));  
            System.out.println("Number of iterations: " + iterationCounter);
        }
    }

    // Computes the ending balance of a loan, given the loan amount, the periodical
    // interest rate (as a percentage), the number of periods (n), and the periodical payment.
    private static double endBalance(double loan, double rate, int n, double payment) {
        double balance = loan;
        double annualRate = rate / 100.0; 

        for (int i = 0; i < n; i++) {
            balance -= payment;               
            balance += balance * annualRate; 
        }

        return balance;  
    }

    // Uses sequential search to compute an approximation of the periodical payment
    // that will bring the ending balance of a loan close to 0.
    public static double bruteForceSolver(double loan, double rate, int n, double epsilon) {
        iterationCounter = 0;  
        
        double payment = loan / n;  
        double balance = loan;    
        
        if (loan <= 0 || rate < 0 || n <= 0 || epsilon <= 0) {
            return -1; 
        }
        
        while (balance > epsilon) {
            iterationCounter++;
            balance = loan;  
            
            for (int i = 0; i < n; i++) {
                balance -= payment;              
                balance += balance * (rate / 100.0);  
            }
            
            if (balance > epsilon) {
                payment += epsilon;  
            } else {
                return payment;      
            }
            
            if (payment > loan) {
                break;
            }
        }
        return Math.floor(payment);  
    }

    // Uses bisection search to compute an approximation of the periodical payment
    // that will bring the ending balance of a loan close to 0.
    public static double bisectionSolver(double loan, double rate, int n, double epsilon) {
        iterationCounter = 0;  

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
            }

            if (balance > 0) { 
                low = mid; 
            } else { 
                high = mid;  
            }
        }

        return Math.floor((low + high) / 2);  
    }
}
