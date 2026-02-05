import java.util.*;
public class problem_1 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int number = sc.nextInt();
		
		System.out.print("Is the number "+number+" divisible by 5? "+(number%5==0));
		sc.close();
	}

}