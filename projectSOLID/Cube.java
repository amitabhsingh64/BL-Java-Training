package projectSOLID;

public class Cube implements Shape3D{
    private double side;
    public Cube(double side){
        this.side = side;
    }

    public double getArea(){
        return 6*(side*side);
    }
    public double getVolume(){
        return side*side*side;
    }
}
