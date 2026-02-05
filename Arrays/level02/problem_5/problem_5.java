package Arrays.level02.problem_5;

import java.util.Scanner;

public class problem_5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter a number: ");
        long number = scanner.nextLong();
        scanner.close();
        boolean isNegative = false;
        if (number < 0) {
            isNegative = true;
            number = -number;
        }
        
        int digitCount = 0;
        long tempNumber = number;
        
        if (number == 0) {
            digitCount = 1;
        } else {
            while (tempNumber > 0) {
                digitCount++;
                tempNumber /= 10;
            }
        }
        
        int[] digits = new int[digitCount];
        tempNumber = number;
        
        for (int i = 0; i < digitCount; i++) {
            digits[i] = (int)(tempNumber % 10);
            tempNumber /= 10;
        }
        
        System.out.print("Original number: ");
        if (isNegative) {
            System.out.print("-");
        }
        System.out.println(number);
        
        System.out.print("Digits: ");
        for (int i = 0; i < digitCount; i++) {
            System.out.print(digits[i] + " ");
        }
        System.out.println();
        
        System.out.print("Reversed number: ");
        if (isNegative) {
            System.out.print("-");
        }
        
        for (int i = 0; i < digitCount; i++) {
            System.out.print(digits[i]);
        }
        
    }
}
