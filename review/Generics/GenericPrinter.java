package Generics;

public class GenericPrinter {
    public static class Printer<T> {
        T thingToPrint;

        public Printer(T thingToPrint) {
            this.thingToPrint = thingToPrint;
        }

        public void print() {
            System.out.println(thingToPrint);
        }
    }

    void main() {
        Printer<Integer> p = new Printer<>(123);
        p.print();

    }
}
