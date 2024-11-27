// Implements algebraic operations and the square root function without using 
// the Java operations a + b, a - b, a * b, a / b, a % b, and without calling 
// Math.sqrt. All the functions in this class operate on int values and
// return int values.

public class Algebra {
    public static void main(String args[]) {
        // Tests some of the operations
        System.out.println(plus(2, 3));   // 2 + 3
        System.out.println(minus(7, 2));  // 7 - 2
        System.out.println(minus(2, 7));  // 2 - 7
        System.out.println(times(3, 4));  // 3 * 4
        System.out.println(times(-3, 4)); // -3 * 4
        System.out.println(times(3, -4)); // 3 * -4
        System.out.println(times(-3, -4));// -3 * -4
        System.out.println(plus(2, times(4, 2)));  // 2 + 4 * 2
        System.out.println(pow(5, 3));      // 5^3
        System.out.println(pow(3, 5));      // 3^5
        System.out.println(div(12, 3));   // 12 / 3    
        System.out.println(div(-12, 3));  // -12 / 3
        System.out.println(div(12, -3));  // 12 / -3
        System.out.println(div(-12, -3)); // -12 / -3
        System.out.println(mod(25, 7));   // 25 % 7
        System.out.println(mod(-25, 7));  // -25 % 7
        System.out.println(mod(25, -7));  // 25 % -7
        System.out.println(mod(-25, -7)); // -25 % -7
        System.out.println(sqrt(36));     // 6
        System.out.println(sqrt(-36));    // -1 (invalid)
        System.out.println(sqrt(0));      // 0
    }

    // Returns x1 + x2
    public static int plus(int x1, int x2) {
        int sum = x1;
        if (x2 > 0) {
            for (int i = 0; i < x2; i++) {
                sum++;
            }
        } else {
            for (int i = 0; i > x2; i--) {
                sum--;
            }
        }
        return sum;
    }

    // Returns x1 - x2
    public static int minus(int x1, int x2) {
        return plus(x1, -x2);
    }

    // Returns x1 * x2
    public static int times(int x1, int x2) {
        int product = 0;
        boolean isNegative = (x1 < 0 && x2 > 0) || (x1 > 0 && x2 < 0);

        x1 = x1 < 0 ? minus(0, x1) : x1; // Make x1 positive
        x2 = x2 < 0 ? minus(0, x2) : x2; // Make x2 positive

        for (int i = 0; i < x2; i++) {
            product = plus(product, x1);
        }

        return isNegative ? minus(0, product) : product;
    }

    // Returns x^n (for n >= 0)
    public static int pow(int x, int n) {
        if (x < 0 || n < 0) {
            return -1; // Indicating invalid input
        }

        int result = 1;
        for (int i = 0; i < n; i++) {
            result = times(result, x);
        }
        return result;
    }

    // Returns the integer part of x1 / x2
    public static int div(int x1, int x2) {
        if (x2 == 0) {
            return 0; // Indicating division by zero is not allowed
        }

        int quotient = 0;
        boolean isNegative = (x1 < 0 && x2 > 0) || (x1 > 0 && x2 < 0);

        x1 = x1 < 0 ? minus(0, x1) : x1; // Make x1 positive
        x2 = x2 < 0 ? minus(0, x2) : x2; // Make x2 positive

        int sum = 0;
        while (sum + x2 <= x1) {
            sum = plus(sum, x2);
            quotient = plus(quotient, 1);
        }

        return isNegative ? minus(0, quotient) : quotient;
    }

    // Returns x1 % x2
    public static int mod(int x1, int x2) {
        if (x2 == 0) {
            return 0; // Indicating modulo by zero is not allowed
        }

        boolean isNegative = x1 < 0;

        x1 = x1 < 0 ? minus(0, x1) : x1; // Make x1 positive
        x2 = x2 < 0 ? minus(0, x2) : x2; // Make x2 positive

        int sum = 0;
        while (sum + x2 <= x1) {
            sum = plus(sum, x2);
        }

        int remainder = minus(x1, sum);
        return isNegative ? minus(0, remainder) : remainder;
    }

    // Returns the integer part of sqrt(x)
    public static int sqrt(int x) {
        if (x < 0) {
            return -1; // Indicating invalid input for square root of negative numbers
        }

        int root = 0;
        while (times(root, root) <= x) {
            root = plus(root, 1);
        }
        return minus(root, 1);
    }
}
