package projectSOLID;

public class MainLoop {
    //I am using 2 interfaces shape2D and shape3D
    static void main(String[] args) {
        Cube a = new Cube(5);
        Rectangle b = new Rectangle(5,5);
        System.out.println("Cube's Surface Area    " + a.getArea());
        System.out.println("Cube's Volume   " + a.getVolume());
        System.out.println("Rectangle's area    " + b.getArea());
    }
}
