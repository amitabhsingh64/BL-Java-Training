package java_training.methods.level_1.problem_2;

import java.util.Scanner;

public class problem_2 {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        int numOfStudents = sc.nextInt();
        sc.close();
        System.out.println(calculateHandshakes(numOfStudents));
    }

    public static int calculateHandshakes(int numOfStudents){
        int possibleHandshakes = (int)(numOfStudents *(numOfStudents-1))/2;
        return possibleHandshakes;
    }
}
