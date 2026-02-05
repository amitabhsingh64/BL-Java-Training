package Arrays.level02.problem_3;

import java.util.Scanner;

public class problem_3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter a number: ");
        long number = scanner.nextLong();
        scanner.close();
        if (number < 0) {
            number = -number;
        }
        
        if (number == 0) {
            System.out.println("The number has only one digit: 0");
            System.out.println("Largest digit: 0");
            System.out.println("Second largest digit: None (only one digit)");
            return;
        }
        
        int maxDigit = 10;
        int[] digits = new int[maxDigit];
        int index = 0;
        long tempNumber = number;
        
        while (tempNumber > 0) {
            if (index == maxDigit) {
                maxDigit += 10;
                int[] temp = new int[maxDigit];
                
                for (int i = 0; i < index; i++) {
                    temp[i] = digits[i];
                }
                
                digits = temp;
                System.out.println("Array resized to accommodate more digits. New size: " + maxDigit);
            }
            
            int digit = (int)(tempNumber % 10);
            digits[index] = digit;
            index++;
            tempNumber /= 10;
        }
        
        int largest = Integer.MIN_VALUE;
        int secondLargest = Integer.MIN_VALUE;
        
        for (int i = 0; i < index; i++) {
            if (digits[i] > largest) {
                secondLargest = largest;
                largest = digits[i];
            } else if (digits[i] > secondLargest && digits[i] != largest) {
                secondLargest = digits[i];
            }
        }
        
        System.out.println("Original number: " + number);
        System.out.print("Digits: ");
        for (int i = 0; i < index; i++) {
            System.out.print(digits[i] + " ");
        }
        System.out.println();
        
        System.out.println("Largest digit: " + largest);
        
        if (secondLargest == Integer.MIN_VALUE) {
            System.out.println("Second largest digit: None (all digits are the same)");
        } else {
            System.out.println("Second largest digit: " + secondLargest);
        }
    }
}
