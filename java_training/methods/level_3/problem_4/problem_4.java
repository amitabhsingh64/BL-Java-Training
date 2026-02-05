package java_training.methods.level_3.problem_4;

import java.util.Scanner;

public class problem_4 {
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
        
        int[] reversedDigits = reverseDigitsArray(digits);
        System.out.print("Reversed digits: ");
        for (int digit : reversedDigits) {
            System.out.print(digit + " ");
        }
        System.out.println();
        
        boolean areEqual = areArraysEqual(digits, reversedDigits);
        System.out.println("Are original and reversed arrays equal: " + areEqual);
        
        boolean isPalindrome = isPalindrome(digits);
        System.out.println("Is palindrome: " + isPalindrome);
        
        boolean isDuck = isDuckNumber(digits);
        System.out.println("Is Duck number: " + isDuck);
        
        scanner.close();
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
    
    public static int[] reverseDigitsArray(int[] digits) {
        int[] reversed = new int[digits.length];
        
        for (int i = 0; i < digits.length; i++) {
            reversed[i] = digits[digits.length - 1 - i];
        }
        
        return reversed;
    }
    
    public static boolean areArraysEqual(int[] array1, int[] array2) {
        if (array1.length != array2.length) {
            return false;
        }
        
        for (int i = 0; i < array1.length; i++) {
            if (array1[i] != array2[i]) {
                return false;
            }
        }
        
        return true;
    }
    
    public static boolean isPalindrome(int[] digits) {
        int[] reversed = reverseDigitsArray(digits);
        return areArraysEqual(digits, reversed);
    }
    
    public static boolean isDuckNumber(int[] digits) {
        if (digits.length <= 1 || digits[0] == 0) {
            return false;
        }
        
        for (int i = 1; i < digits.length; i++) {
            if (digits[i] == 0) {
                return true;
            }
        }
        
        return false;
    }
}
