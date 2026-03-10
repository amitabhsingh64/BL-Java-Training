package projectSOLID;

public class Rectangle implements Shape2D {
    private double side;
    private double length;
    public Rectangle(double side, double length) {
        this.side = side;
        this.length = length;
    }
    public double getArea(){
        return side*length;
    }
}
