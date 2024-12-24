import java.util.ArrayList;
import java.util.List;

public class PrimeNumbers {
    public static List<Integer> primeNumbers(int start, int end) {
        List<Integer> primes = new ArrayList<>();
        for (int num = start; num <= end; num++) {
            if (isPrime(num)) {
                primes.add(num);
            }
        }
        return primes;
    }

    private static boolean isPrime(int num) {
        if (num < 2) return false;
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(primeNumbers(1, 20)); // [2, 3, 5, 7, 11, 13, 17, 19]
        System.out.println(primeNumbers(30, 100)); // [31, 37, 41, 43, ...]
    }
}
