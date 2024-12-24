/* */

import java.util.ArrayList;
import java.util.List;


/*Clase principal para determinar numero primos en un rango */
public class PrimeNumbers {
    public static List<Integer> primeNumbers(int start, int end) {
        /*Crea una lista para almacenar los numeros primos */
        List<Integer> primes = new ArrayList<>();

        /*Intera desde el el rango hasta el final */
        for (int num = start; num <= end; num++) {
            if (isPrime(num)) {
                primes.add(num);
            }/*/Agrega a la lista los números primos */
        }
        return primes;
    }

    /*Verifica si el numero es primo */
    private static boolean isPrime(int num) {
        if (num < 2) return false;
        /*Si el numero es divisible entre i, el número no es primo */
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) return false;
        }
        return true;
    }

    public static void main(String[] args) {

        /*Imprime los numeros primos de ese rango */
        System.out.println(primeNumbers(1, 20)); 
        System.out.println(primeNumbers(30, 100)); 
    }
}
