package Arrays.level01.problem_4;

import java.util.Scanner;

public class problem_4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        double[] numbers = new double[10];
        int index = 0;
        double total = 0.0;
        
        System.out.println("Enter up to 10 positive numbers (enter 0 or negative to stop):");
        
        while (true) {
            System.out.print("Enter number " + (index + 1) + ": ");
            double input = scanner.nextDouble();
            
            if (input <= 0) {
                System.out.println("Input terminated by zero or negative number.");
                break;
            }
            
            if (index == 10) {
                System.out.println("Maximum array size reached (10 elements).");
                break;
            }
            
            numbers[index] = input;
            index++;
        }
        scanner.close();
        
        for (int i = 0; i < index; i++) {
            total += numbers[i];
        }
        
        System.out.println("\nNumbers entered:");
        for (int i = 0; i < index; i++) {
            System.out.println("Element " + i + ": " + numbers[i]);
        }
        
        System.out.println("\nTotal of all numbers: " + total);
        System.out.println("Number of values entered: " + index);
        
    }
}
