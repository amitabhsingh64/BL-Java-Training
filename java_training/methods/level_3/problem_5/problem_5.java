package java_training.methods.level_3.problem_5;

import java.util.Scanner;

public class problem_5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter a number: ");
        long number = scanner.nextLong();
        scanner.close();
        
        boolean isPrime = isPrimeNumber(number);
        System.out.println("Is prime number: " + isPrime);
        
        boolean isNeon = isNeonNumber(number);
        System.out.println("Is neon number: " + isNeon);
        
        boolean isSpy = isSpyNumber(number);
        System.out.println("Is spy number: " + isSpy);
        
        boolean isAutomorphic = isAutomorphicNumber(number);
        System.out.println("Is automorphic number: " + isAutomorphic);
        
        boolean isBuzz = isBuzzNumber(number);
        System.out.println("Is buzz number: " + isBuzz);
        
        scanner.close();
    }
    
    public static boolean isPrimeNumber(long number) {
        if (number <= 1) {
            return false;
        }
        
        if (number <= 3) {
            return true;
        }
        
        if (number % 2 == 0 || number % 3 == 0) {
            return false;
        }
        
        for (int i = 5; i * i <= number; i += 6) {
            if (number % i == 0 || number % (i + 2) == 0) {
                return false;
            }
        }
        
        return true;
    }
    
    public static boolean isNeonNumber(long number) {
        long square = number * number;
        long sum = 0;
        
        while (square > 0) {
            sum += square % 10;
            square /= 10;
        }
        
        return sum == number;
    }
    
    public static boolean isSpyNumber(long number) {
        long sum = 0;
        long product = 1;
        
        while (number > 0) {
            long digit = number % 10;
            sum += digit;
            product *= digit;
            number /= 10;
        }
        
        return sum == product;
    }
    
    public static boolean isAutomorphicNumber(long number) {
        long square = number * number;
        
        while (number > 0) {
            if (number % 10 != square % 10) {
                return false;
            }
            number /= 10;
            square /= 10;
        }
        
        return true;
    }
    
    public static boolean isBuzzNumber(long number) {
        return number % 7 == 0 || number % 10 == 7;
    }
}
