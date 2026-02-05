package java.controlFlow.level_1.problem_4;

import java.util.Scanner;

public class problem_4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int number = sc.nextInt();

        if (number > 0) {
            System.out.println("The sum of " + number + " natural numbers is " + (number * (number - 1) / 2));
        } else {
            System.out.println("The number " + number + " is not a natural number");
        }
        sc.close();
    }
}
