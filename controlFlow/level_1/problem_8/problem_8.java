package controlFlow.level_1.problem_8;

import java.util.Scanner;

public class problem_8 {
    public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int counter = sc.nextInt();
		while(counter!=1) {
			System.out.println(counter);
			counter--;
		}
		System.out.println("Launch");
		sc.close();
	}
}
