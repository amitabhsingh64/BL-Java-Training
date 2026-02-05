package Arrays.level01.problem_2;

import java.util.Scanner;

public class problem_2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        int[] numbers = new int[5];
        
        System.out.println("Enter 5 numbers:");
        for (int i = 0; i < numbers.length; i++) {
            System.out.print("Number " + (i + 1) + ": ");
            numbers[i] = scanner.nextInt();
        }
        scanner.close();
        System.out.println("\nNumber Analysis:");
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] > 0) {
                System.out.print("Number " + (i + 1) + ": " + numbers[i] + " is positive");
                if (numbers[i] % 2 == 0) {
                    System.out.println(" and even");
                } else {
                    System.out.println(" and odd");
                }
            } else if (numbers[i] < 0) {
                System.out.println("Number " + (i + 1) + ": " + numbers[i] + " is negative");
            } else {
                System.out.println("Number " + (i + 1) + ": " + numbers[i] + " is zero");
            }
        }
        
        System.out.println("\nComparing first and last elements:");
        if (numbers[0] == numbers[numbers.length - 1]) {
            System.out.println("First element (" + numbers[0] + ") is equal to last element (" + numbers[numbers.length - 1] + ")");
        } else if (numbers[0] > numbers[numbers.length - 1]) {
            System.out.println("First element (" + numbers[0] + ") is greater than last element (" + numbers[numbers.length - 1] + ")");
        } else {
            System.out.println("First element (" + numbers[0] + ") is less than last element (" + numbers[numbers.length - 1] + ")");
        } 
    }
}
