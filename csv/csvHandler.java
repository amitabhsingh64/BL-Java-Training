package csv;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class csvHandler {

    static final String FILE = "/Users/amitabhsingh/code/BL-java-Training/BL-Java-Training/csv/abc.csv";
    public static void write(List<String[]> rows) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(FILE));
        for (String[] row : rows) {
            writer.write(String.join(",", row));
            writer.newLine();
        }
        writer.close();
        System.out.println("Written to " + FILE);
    }
    public static List<String[]> read() throws IOException {
        List<String[]> rows = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(FILE));
        String line;
        while ((line = reader.readLine()) != null) {
            rows.add(line.split(","));
        }
        reader.close();
        return rows;
    }
    public static void append(String[] row) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(FILE, true));
        writer.write(String.join(",", row));
        writer.newLine();
        writer.close();
        System.out.println("Appended row to " + FILE);
    }

    public static void main(String[] args) throws IOException {
        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"Name", "Age", "City"});
        data.add(new String[]{"Amitabh", "22", "Agra"});
        data.add(new String[]{"Ayushmaan", "22", "Gurgoan"});
        write(data);

        append(new String[]{"Shobhit", "23", "Noida"});

        System.out.println("\nReading " + FILE + ":");
        List<String[]> rows = read();
        for (String[] row : rows) {
            for (String col : row) System.out.printf("%-12s", col);
            System.out.println();
        }
    }
}
