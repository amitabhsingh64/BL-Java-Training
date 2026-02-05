package java_training.methods.level_3.problem_3;

import java.util.Scanner;

public class problem_3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter a number: ");
        long number = scanner.nextLong();
        scanner.close();
        
        int digitCount = countDigits(number);
        System.out.println("Number of digits: " + digitCount);
        
        int[] digits = storeDigits(number, digitCount);
        
        System.out.print("Digits: ");
        for (int digit : digits) {
            System.out.print(digit + " ");
        }
        System.out.println();
        
        int sum = findSumOfDigits(digits);
        System.out.println("Sum of digits: " + sum);
        
        double sumOfSquares = findSumOfSquaresOfDigits(digits);
        System.out.println("Sum of squares of digits: " + sumOfSquares);
        
        boolean isHarshad = isHarshadNumber(number, digits);
        System.out.println("Is Harshad number: " + isHarshad);
        
        int[][] frequency = findDigitFrequency(digits);
        System.out.println("Digit frequencies:");
        for (int i = 0; i < frequency.length; i++) {
            if (frequency[i][1] > 0) {
                System.out.println("Digit " + frequency[i][0] + " appears " + frequency[i][1] + " times");
            }
        }
        
    }
    
    public static int countDigits(long number) {
        if (number == 0) {
            return 1;
        }
        
        number = Math.abs(number);
        
        int count = 0;
        while (number > 0) {
            count++;
            number /= 10;
        }
        return count;
    }
    
    public static int[] storeDigits(long number, int digitCount) {
        number = Math.abs(number);
        int[] digits = new int[digitCount];
        
        for (int i = digitCount - 1; i >= 0; i--) {
            digits[i] = (int)(number % 10);
            number /= 10;
        }
        
        return digits;
    }
    
    public static int findSumOfDigits(int[] digits) {
        int sum = 0;
        for (int digit : digits) {
            sum += digit;
        }
        return sum;
    }
    
    public static double findSumOfSquaresOfDigits(int[] digits) {
        double sum = 0;
        for (int digit : digits) {
            sum += Math.pow(digit, 2);
        }
        return sum;
    }
    
    public static boolean isHarshadNumber(long number, int[] digits) {
        number = Math.abs(number);
        int sum = findSumOfDigits(digits);
        return number % sum == 0;
    }
    
    public static int[][] findDigitFrequency(int[] digits) {
        int[][] frequency = new int[10][2];
        
        for (int i = 0; i < 10; i++) {
            frequency[i][0] = i;
            frequency[i][1] = 0;  
        }
        
        for (int digit : digits) {
            frequency[digit][1]++;
        }
        
        return frequency;
    }
}
