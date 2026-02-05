package java_training.methods.level_2.problem_3;

import java.util.Scanner;

public class problem_3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter a year: ");
        int year = scanner.nextInt();
        scanner.close(); 
        
        if (isLeapYear(year)) {
            System.out.println(year + " is a Leap Year");
        } else {
            System.out.println(year + " is not a Leap Year");
        }
        
    }
    
    public static boolean isLeapYear(int year) {
        if (year < 1582) {
            System.out.println("Warning: The Gregorian calendar was adopted in 1582.");
            return false;
        }
        
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }
}
