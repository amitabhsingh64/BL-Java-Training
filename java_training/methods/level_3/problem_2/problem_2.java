package java_training.methods.level_3.problem_2;

import java.util.Scanner;

public class problem_2 {
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
        
        boolean isDuck = isDuckNumber(digits);
        System.out.println("Is Duck number: " + isDuck);
        
        boolean isArmstrong = isArmstrongNumber(number, digits, digitCount);
        System.out.println("Is Armstrong number: " + isArmstrong);
        
        int[] largestElements = findLargestAndSecondLargest(digits);
        System.out.println("Largest digit: " + largestElements[0]);
        System.out.println("Second largest digit: " +  (largestElements[1] == Integer.MIN_VALUE ? "None" : largestElements[1]));
        
        int[] smallestElements = findSmallestAndSecondSmallest(digits);
        System.out.println("Smallest digit: " + smallestElements[0]);
        System.out.println("Second smallest digit: " + (smallestElements[1] == Integer.MAX_VALUE ? "None" : smallestElements[1]));
        
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
    
    public static boolean isArmstrongNumber(long number, int[] digits, int digitCount) {
        number = Math.abs(number);
        long sum = 0;
        
        for (int digit : digits) {
            sum += Math.pow(digit, digitCount);
        }
        
        return sum == number;
    }
    
    public static int[] findLargestAndSecondLargest(int[] digits) {
        int largest = Integer.MIN_VALUE;
        int secondLargest = Integer.MIN_VALUE;
        
        for (int digit : digits) {
            if (digit > largest) {
                secondLargest = largest;
                largest = digit;
            } else if (digit > secondLargest && digit != largest) {
                secondLargest = digit;
            }
        }
        
        return new int[]{largest, secondLargest};
    }
    
    public static int[] findSmallestAndSecondSmallest(int[] digits) {
        int smallest = Integer.MAX_VALUE;
        int secondSmallest = Integer.MAX_VALUE;
        
        for (int digit : digits) {
            if (digit < smallest) {
                secondSmallest = smallest;
                smallest = digit;
            } else if (digit < secondSmallest && digit != smallest) {
                secondSmallest = digit;
            }
        }
        
        return new int[]{smallest, secondSmallest};
    }
}
