package Arrays.level01.problem_1;

import java.util.Scanner;

public class problem_1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Define an array to store ages of 10 students
        int[] ages = new int[10];
        
        System.out.println("Enter the age of 10 students:");
        for (int i = 0; i < ages.length; i++) {
            System.out.print("Student " + (i + 1) + ": ");
            ages[i] = scanner.nextInt();
        }
        scanner.close();
        System.out.println("\nVoting Eligibility Results:");
        for (int i = 0; i < ages.length; i++) {
            if (ages[i] < 0) {
                System.out.println("Student " + (i + 1) + ": Invalid age");
            } else if (ages[i] >= 18) {
                System.out.println("Student " + (i + 1) + ": The student with the age " + ages[i] + " can vote");
            } else {
                System.out.println("Student " + (i + 1) + ": The student with the age " + ages[i] + " cannot vote");
            }
        }
    }
}
