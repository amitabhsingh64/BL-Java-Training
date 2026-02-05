package java_training.methods.level_2.problem_4;

import java.util.Scanner;

public class problem_4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Unit Converter - Distance");
        System.out.println("1. Kilometers to Miles");
        System.out.println("2. Miles to Kilometers");
        System.out.println("3. Meters to Feet");
        System.out.println("4. Feet to Meters");
        System.out.print("Enter your choice (1-4): ");
        
        int choice = scanner.nextInt();
        
        System.out.print("Enter the value to convert: ");
        double value = scanner.nextDouble();
        scanner.close();
        
        double result = 0;
        String fromUnit = "";
        String toUnit = "";
        
        switch (choice) {
            case 1:
                result = convertKmToMiles(value);
                fromUnit = "kilometers";
                toUnit = "miles";
                break;
            case 2:
                result = convertMilesToKm(value);
                fromUnit = "miles";
                toUnit = "kilometers";
                break;
            case 3:
                result = convertMetersToFeet(value);
                fromUnit = "meters";
                toUnit = "feet";
                break;
            case 4:
                result = convertFeetToMeters(value);
                fromUnit = "feet";
                toUnit = "meters";
                break;
            default:
                System.out.println("Invalid choice!");
                scanner.close();
                return;
        }
        
        System.out.println(value + " " + fromUnit + " = " + result + " " + toUnit);
        
    }
    
    public static double convertKmToMiles(double km) {
        double km2miles = 0.621371;
        return km * km2miles;
    }
    
    public static double convertMilesToKm(double miles) {
        double miles2km = 1.60934;
        return miles * miles2km;
    }
    
    public static double convertMetersToFeet(double meters) {
        double meters2feet = 3.28084;
        return meters * meters2feet;
    }
    
    public static double convertFeetToMeters(double feet) {
        double feet2meters = 0.3048;
        return feet * feet2meters;
    }
}
