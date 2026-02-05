package java_training.methods.level_3.problem_7;

import java.util.Scanner;

public class problem_7 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter a positive integer: ");
        int number = scanner.nextInt();
        scanner.close();
        if (number <= 0) {
            System.out.println("Please enter a positive integer.");
            return;
        }
        
        int[] factors = findFactors(number);
        
        System.out.print("Factors of " + number + ": ");
        for (int factor : factors) {
            System.out.print(factor + " ");
        }
        System.out.println();
        
        int greatestFactor = findGreatestFactor(factors);
        System.out.println("Greatest factor (besides itself): " + greatestFactor);
        
        int sum = findSumOfFactors(factors);
        System.out.println("Sum of factors: " + sum);
        
        long product = findProductOfFactors(factors);
        System.out.println("Product of factors: " + product);
        
        double productOfCubes = findProductOfCubeOfFactors(factors);
        System.out.println("Product of cube of factors: " + productOfCubes);
        
        scanner.close();
    }
    
    public static int[] findFactors(int number) {
        int count = 0;
        for (int i = 1; i <= number; i++) {
            if (number % i == 0) {
                count++;
            }
        }
        
        int[] factors = new int[count];
        
        int index = 0;
        for (int i = 1; i <= number; i++) {
            if (number % i == 0) {
                factors[index] = i;
                index++;
            }
        }
        
        return factors;
    }
    
    public static int findGreatestFactor(int[] factors) {
        if (factors.length <= 1) {
            return 1;
        }
        
        return factors[factors.length - 2];
    }
    
    public static int findSumOfFactors(int[] factors) {
        int sum = 0;
        for (int factor : factors) {
            sum += factor;
        }
        return sum;
    }
    
    public static long findProductOfFactors(int[] factors) {
        long product = 1;
        for (int factor : factors) {
            product *= factor;
        }
        return product;
    }
    
    public static double findProductOfCubeOfFactors(int[] factors) {
        double product = 1;
        for (int factor : factors) {
            product *= Math.pow(factor, 3);
        }
        return product;
    }
}
