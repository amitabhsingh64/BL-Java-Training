package practice;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.*;
import practice.ExceptionType;
public class tryWithResource {
    static void main() {
        try (FileOutputStream output = new FileOutputStream("filename.txt")) {
            output.write("Hello".getBytes());
            System.out.println("Successfully wrote to file.");
            //throw new ExceptionType("exception");
        } catch (IOException e) {
            System.out.println("Error writing file.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
