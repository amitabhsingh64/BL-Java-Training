package Arrays.level01.problem_3;

import java.util.Scanner;

public class problem_3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter a number: ");
        int number = scanner.nextInt();
        scanner.close();
        
        int[] results = new int[10];
        
        for (int i = 0; i < 10; i++) {
            results[i] = number * (i + 1);
        }
        
        System.out.println("\nMultiplication table for " + number + ":");
        for (int i = 0; i < 10; i++) {
            System.out.println(number + " * " + (i + 1) + " = " + results[i]);
        }
    }
}
