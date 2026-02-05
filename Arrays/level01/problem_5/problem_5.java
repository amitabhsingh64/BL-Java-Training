package Arrays.level01.problem_5;

import java.util.Scanner;

public class problem_5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter a number: ");
        int number = scanner.nextInt();
        scanner.close();
        
        int[] multiplicationResult = new int[4]; 
        
        for (int i = 0; i < 4; i++) {
            multiplicationResult[i] = number * (i + 6);
        }
        
        System.out.println("\nMultiplication table for " + number + " from 6 to 9:");
        for (int i = 0; i < 4; i++) {
            System.out.println(number + " * " + (i + 6) + " = " + multiplicationResult[i]);
        }
        
    }
}
