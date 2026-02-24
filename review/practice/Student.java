package practice;

public class Student <T extends Number,K>{
    private T fees;
    public Student (T thingInFees) {
        this.fees = thingInFees;
    }

    public T getfees(){
        return fees;
    }
}
