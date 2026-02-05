package Arrays.level02.problem_1;

import java.util.Scanner;

public class problem_1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        final int EMPLOYEES = 10;
        
        double[] salaries = new double[EMPLOYEES];
        double[] yearsOfService = new double[EMPLOYEES];
        double[] bonusAmounts = new double[EMPLOYEES];
        double[] newSalaries = new double[EMPLOYEES];
        
        double totalBonus = 0;
        double totalOldSalary = 0;
        double totalNewSalary = 0;
        
        System.out.println("Enter details for " + EMPLOYEES + " employees:");
        
        for (int i = 0; i < EMPLOYEES; i++) {
            System.out.println("\nEmployee " + (i + 1) + ":");
            
            while (true) {
                System.out.print("Enter salary: ");
                salaries[i] = scanner.nextDouble();
                
                if (salaries[i] <= 0) {
                    System.out.println("Invalid salary! Please enter a positive number.");
                } else {
                    break;
                }
            }
            
            while (true) {
                System.out.print("Enter years of service: ");
                yearsOfService[i] = scanner.nextDouble();
                
                if (yearsOfService[i] < 0) {
                    System.out.println("Invalid years of service! Please enter a non-negative number.");
                } else {
                    break;
                }
            }
            
            totalOldSalary += salaries[i];
        }
        scanner.close();
        for (int i = 0; i < EMPLOYEES; i++) {
            if (yearsOfService[i] > 5) {
                bonusAmounts[i] = salaries[i] * 0.05;
            } else {
                bonusAmounts[i] = salaries[i] * 0.02;
            }
            
            newSalaries[i] = salaries[i] + bonusAmounts[i];
            
            totalBonus += bonusAmounts[i];
            totalNewSalary += newSalaries[i];
        }
        
        System.out.println("\n--- Employee Bonus Summary ---");
        
        for (int i = 0; i < EMPLOYEES; i++) {
            System.out.println("\nEmployee " + (i + 1) + ":");
            System.out.println("Salary: $" + salaries[i]);
            System.out.println("Years of Service: " + yearsOfService[i]);
            System.out.println("Bonus: $" + bonusAmounts[i]);
            System.out.println("New Salary: $" + newSalaries[i]);
        }
        
        System.out.println("\nSummary:");
        System.out.println("Total Old Salary: $" + totalOldSalary);
        System.out.println("Total Bonus: $" + totalBonus);
        System.out.println("Total New Salary: $" + totalNewSalary);
    }
}
