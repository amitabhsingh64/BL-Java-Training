package controlFlow.level_1.problem_6;

import java.util.Scanner;

public class problem_6 {
    public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int number = sc.nextInt();
		
		if(number>0) {
			System.out.println("Positive");
		}else if(number<0) {
			System.out.println("Negative");
		}else {
			System.out.println("Zero");			
		}
		sc.close();
	}
}
