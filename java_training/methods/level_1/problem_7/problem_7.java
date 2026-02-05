package java_training.methods.level_1.problem_7;

import java.util.Scanner;

public class problem_7 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter first number: ");
        int number1 = scanner.nextInt();
        
        System.out.print("Enter second number: ");
        int number2 = scanner.nextInt();
        
        System.out.print("Enter third number: ");
        int number3 = scanner.nextInt();
        scanner.close();
        int[] result = findSmallestAndLargest(number1, number2, number3);
        
        System.out.println("Smallest number: " + result[0]);
        System.out.println("Largest number: " + result[1]);
        
    }
    
    public static int[] findSmallestAndLargest(int number1, int number2, int number3) {
        int[] result = new int[2];
        
        result[0] = Math.min(Math.min(number1, number2), number3);
        
        result[1] = Math.max(Math.max(number1, number2), number3);
        
        return result;
    }
}
