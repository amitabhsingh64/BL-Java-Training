package java.methods.level_1.Problem_1;

import java.util.Scanner;

public class problem_1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter the Principal amount: ");
        double principal = scanner.nextDouble();
        
        System.out.print("Enter the Rate of Interest (in %): ");
        double rate = scanner.nextDouble();
        
        System.out.print("Enter the Time period (in years): ");
        double time = scanner.nextDouble();
        
        double simpleInterest = calculateSimpleInterest(principal, rate, time);
        
        System.out.println("The Simple Interest is " + simpleInterest +  " for Principal " + principal +  ", Rate of Interest " + rate + "%" +  " and Time " + time + " years");
        
    }
    
    public static double calculateSimpleInterest(double principal, double rate, double time) {
        return (principal * rate * time) / 100;
    }
}
