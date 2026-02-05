package controlFlow.level_1.problem_7;

import java.util.Scanner;

public class problem_7 {
    public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String month = sc.next();
		int date = sc.nextInt();
		
		if(month.equals("April") || month.equals("May")) {
			System.out.println("Its a Spring Season");
		}else if((month.equals("March") && date>19) || (month.equals("June") && date<21)) {
			System.out.println("Its a Spring Season");
		}else {
			System.out.println("Not a Spring Season");
		}
		sc.close();
	}
}
