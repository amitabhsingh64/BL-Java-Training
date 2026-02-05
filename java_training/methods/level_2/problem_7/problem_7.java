package java_training.methods.level_2.problem_7;

import java.util.Scanner;

public class problem_7 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        int[] ages = new int[10];
        
        System.out.println("Enter the age of 10 students:");
        for (int i = 0; i < ages.length; i++) {
            System.out.print("Student " + (i + 1) + ": ");
            ages[i] = scanner.nextInt();
            scanner.close();
        }
        
        System.out.println("\nVoting Eligibility Results:");
        for (int i = 0; i < ages.length; i++) {
            boolean canVote = canStudentVote(ages[i]);
            if (canVote) {
                System.out.println("Student " + (i + 1) + " (Age: " + ages[i] + ") can vote.");
            } else {
                System.out.println("Student " + (i + 1) + " (Age: " + ages[i] + ") cannot vote.");
            }
        }
        
    }
    
    public static boolean canStudentVote(int age) {
        if (age < 0) {
            return false;
        }
        
        return age >= 18;
    }
}
