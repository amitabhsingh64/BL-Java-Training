package Generics;

public class Box <T extends Number> {
    private T thingInBox;

    //Generic Constructor
    public Box(T thingInBox) {
        this.thingInBox = thingInBox;
    }

    public Box() {}

    public T getThingInBox() {
        return thingInBox;
    }

    public void setThingInBox(T thingInBox) {
        this.thingInBox = thingInBox;
    }
    public void print(){
        System.out.println(thingInBox);
    }
}
