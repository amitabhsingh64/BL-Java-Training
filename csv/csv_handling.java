package csv;

import java.io.*;

public class csv_handling {
    static void main (String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("/Users/amitabhsingh/code/BL-java-Training/BL-Java-Training/csv/file.csv"));
        String line;

//        line = br.readLine();
//        String[] parts = line.split(",");
//
//        int id = Integer.parseInt(parts[0]);
//        String name = parts[1];
//        int marks = Integer.parseInt(parts[2]);
//
//        System.out.println("Id->" + id);
//        System.out.println("Name->" + name);
//        System.out.println("Marks->" + marks);



        while((line = br.readLine()) != null) {
//            line = br.readLine();
            String[] parts = line.split(",");

            int id = Integer.parseInt(parts[0]);
            String name = parts[1];
            int marks = Integer.parseInt(parts[2]);

            System.out.println("Id->" + id);
            System.out.println("Name->" + name);
            System.out.println("Marks->" + marks);

        }

        br.close();
    }
}