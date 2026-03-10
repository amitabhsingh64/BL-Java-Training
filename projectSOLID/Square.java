package projectSOLID;

public class Square implements Shape2D {
    private double side;
    public Square(double side) {
        this.side = side;
    }

    public double getArea() {
        return side*side;
    }
}
