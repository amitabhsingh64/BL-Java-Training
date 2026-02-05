package java_training.methods.level_1.problem_6;

import java.util.Scanner;

public class problem_6 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter a natural number: ");
        int n = scanner.nextInt();
        scanner.close();
        
        if (n <= 0) {
            System.out.println("Please enter a positive integer.");
            return;
        }
        int sum = calculateSum(n);
        System.out.println("Sum of first " + n + " natural numbers: " + sum);
    }
    
    public static int calculateSum(int n) {
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += i;
        }
        return sum;
    }
}
