package jdbc;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        StudentDAO dao = new StudentDAO();


        dao.createTable();

        dao.insert("Amitabh", 22);
        dao.insert("Bob", 25);


        System.out.println("\nAll students:");
        List<Student> students = dao.findAll();
        students.forEach(System.out::println);


        System.out.println("\nUpdating first student...");
        dao.update(1, "Alice Updated 23");


        System.out.println("\nAfter update:");
        dao.findAll().forEach(System.out::println);


        System.out.println("\nDeleting student with id=2...");
        dao.delete(2);


        System.out.println("\nFinal state:");
        dao.findAll().forEach(System.out::println);
    }
}
