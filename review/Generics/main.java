package Generics;

public class main {
    static void main() {
        Box<Integer> box1 = new Box<>(25);
        box1.print();
        Box<Integer> box2 = new Box<>();
        box2.setThingInBox(45);
        box2.print();
    }
}
