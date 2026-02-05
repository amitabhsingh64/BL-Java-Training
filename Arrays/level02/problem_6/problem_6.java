package Arrays.level02.problem_6;

import java.util.Scanner;

public class problem_6 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter the number of persons: ");
        int numPersons = scanner.nextInt();
        
        if (numPersons <= 0) {
            System.out.println("Invalid input! Number of persons must be positive.");
            scanner.close();
            return;
        }
        
        double[] weights = new double[numPersons];
        double[] heights = new double[numPersons];
        double[] bmis = new double[numPersons];
        String[] statuses = new String[numPersons];
        
        for (int i = 0; i < numPersons; i++) {
            System.out.println("\nPerson " + (i + 1) + ":");
            
            while (true) {
                System.out.print("Enter weight (in kg): ");
                weights[i] = scanner.nextDouble();
                
                if (weights[i] <= 0) {
                    System.out.println("Invalid weight! Weight must be positive.");
                } else {
                    break;
                }
            }
            
            while (true) {
                System.out.print("Enter height (in cm): ");
                heights[i] = scanner.nextDouble();
                
                if (heights[i] <= 0) {
                    System.out.println("Invalid height! Height must be positive.");
                } else {
                    break;
                }
            }
            scanner.close();
            double heightInMeters = heights[i] / 100.0;
            bmis[i] = weights[i] / (heightInMeters * heightInMeters);
            
            if (bmis[i] < 18.5) {
                statuses[i] = "Underweight";
            } else if (bmis[i] < 25.0) {
                statuses[i] = "Normal weight";
            } else if (bmis[i] < 30.0) {
                statuses[i] = "Overweight";
            } else {
                statuses[i] = "Obese";
            }
        }
        
        System.out.println("\n--- BMI Results ---");
        
        for (int i = 0; i < numPersons; i++) {
            System.out.println("\nPerson " + (i + 1) + ":");
            System.out.println("Weight: " + weights[i] + " kg");
            System.out.println("Height: " + heights[i] + " cm");
            System.out.println("BMI: " + bmis[i]);
            System.out.println("Status: " + statuses[i]);
        }
        
    }
}
