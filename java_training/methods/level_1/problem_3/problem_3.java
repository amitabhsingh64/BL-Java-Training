package java_training.methods.level_1.problem_3;

import java.util.Scanner;

public class problem_3 {
    public static final int distanceToCover = 5;
    public static void main(String[] args) {
        //assuming sides are also in KMs
        Scanner sc = new Scanner(System.in);
        int sum = 0;
        for(int i=0;i<3;i++){
            sum +=sc.nextInt();
        }
        sc.close();
        System.out.println("Rounds to cover 5km is" + numberOfRounds(sum));
    }
    public static float numberOfRounds(int sum){
        return (float)distanceToCover/sum;
    }
}
