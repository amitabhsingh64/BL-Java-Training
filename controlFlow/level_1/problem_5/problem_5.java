package controlFlow.level_1.problem_5;

import java.util.Scanner;

public class problem_5 {
    public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int age = sc.nextInt();
		if(age>=18) {
			System.out.println("The person's age is "+age+" and can vote.");
		}else {
			System.out.println("The person's age is "+age+" and cannot vote.");			
		}
		sc.close();
	}
}
