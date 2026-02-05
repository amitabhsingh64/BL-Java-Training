package Arrays.level02.problem_2;

import java.util.Scanner;

public class problem_2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        String[] names = {"Amar", "Akbar", "Anthony"};
        int[] ages = new int[3];
        double[] heights = new double[3];
        
        System.out.println("Enter details for the three friends:");
        
        for (int i = 0; i < 3; i++) {
            System.out.println("\nEnter details for " + names[i] + ":");
            
            System.out.print("Age: ");
            ages[i] = scanner.nextInt();
            
            System.out.print("Height (in cm): ");
            heights[i] = scanner.nextDouble();
        }
        scanner.close();
        int minAgeIndex = 0;
        for (int i = 1; i < 3; i++) {
            if (ages[i] < ages[minAgeIndex]) {
                minAgeIndex = i;
            }
        }
        int maxHeightIndex = 0;
        for (int i = 1; i < 3; i++) {
            if (heights[i] > heights[maxHeightIndex]) {
                maxHeightIndex = i;
            }
        }
        
        System.out.println("\n--- Results ---");
        System.out.println("Youngest friend: " + names[minAgeIndex] + " (Age: " + ages[minAgeIndex] + ")");
        System.out.println("Tallest friend: " + names[maxHeightIndex] + " (Height: " + heights[maxHeightIndex] + " cm)");
    }
}
