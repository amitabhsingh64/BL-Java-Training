package java_training.methods.level_3.problem_6;

import java.util.Scanner;

public class problem_6 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter a number: ");
        int number = scanner.nextInt();
        scanner.close();
        
        boolean isPerfect = isPerfectNumber(number);
        System.out.println("Is perfect number: " + isPerfect);
        
        boolean isAbundant = isAbundantNumber(number);
        System.out.println("Is abundant number: " + isAbundant);
        
        boolean isDeficient = isDeficientNumber(number);
        System.out.println("Is deficient number: " + isDeficient);
        
        boolean isStrong = isStrongNumber(number);
        System.out.println("Is strong number: " + isStrong);
        
        scanner.close();
    }
    
    public static boolean isPerfectNumber(int number) {
        if (number <= 1) {
            return false;
        }
        
        int sum = 1; 
        
        for (int i = 2; i * i <= number; i++) {
            if (number % i == 0) {
                sum += i;
                
                if (i != number / i) {
                    sum += number / i;
                }
            }
        }
        
        return sum == number;
    }
    
    public static boolean isAbundantNumber(int number) {
        if (number <= 1) {
            return false;
        }
        
        int sum = 1; 
        
        for (int i = 2; i * i <= number; i++) {
            if (number % i == 0) {
                sum += i;
                
                if (i != number / i) {
                    sum += number / i;
                }
            }
        }
        
        return sum > number;
    }
    
    public static boolean isDeficientNumber(int number) {
        if (number <= 1) {
            return true;
        }
        
        int sum = 1; 
        
        for (int i = 2; i * i <= number; i++) {
            if (number % i == 0) {
                sum += i;
                
                if (i != number / i) {
                    sum += number / i;
                }
            }
        }
        
        return sum < number;
    }
    
    public static boolean isStrongNumber(int number) {
        if (number <= 0) {
            return false;
        }
        
        int originalNumber = number;
        int sum = 0;
        
        while (number > 0) {
            int digit = number % 10;
            sum += factorial(digit);
            number /= 10;
        }
        
        return sum == originalNumber;
    }
    
    public static int factorial(int n) {
        if (n <= 1) {
            return 1;
        }
        
        int result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        
        return result;
    }
}
