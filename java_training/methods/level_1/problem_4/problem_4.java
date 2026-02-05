package java_training.methods.level_1.problem_4;
import java.util.*;;


public class problem_4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println(PositiveNegativeZero(sc.nextInt()));
        sc.close();
    }

    public static int PositiveNegativeZero(int num){
        if(num==0){
            return 0;
        }else if(num>0){
            return 1;
        }
        return -1;
    }
}
