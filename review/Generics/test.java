package Generics;

enum Day{
SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY;
}

public class test {
    static void main() {
        Day day = Day.SUNDAY;
        Day day2 = Day.MONDAY;
        System.out.println(day.equals(day2));
    }

    //Generic method
    public <T> void print(T value){
        System.out.println(value);
    }
}
