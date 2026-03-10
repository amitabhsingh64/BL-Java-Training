package json;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JsonHandler {

    static final String FILE = "/Users/amitabhsingh/code/BL-java-Training/BL-Java-Training/json/abc.json";

    public static void write(List<String[][]> records) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(FILE));
        writer.write("[\n");
        for (int i = 0; i < records.size(); i++) {
            writer.write("  {");
            String[][] fields = records.get(i);
            for (int j = 0; j < fields.length; j++) {
                writer.write("\"" + fields[j][0] + "\": \"" + fields[j][1] + "\"");
                if (j < fields.length - 1) writer.write(", ");
            }
            writer.write(i < records.size() - 1 ? "},\n" : "}\n");
        }
        writer.write("]");
        writer.close();
        System.out.println("Written to " + FILE);
    }
    public static String read() throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(FILE));
        String line;
        while ((line = reader.readLine()) != null) sb.append(line).append("\n");
        reader.close();
        return sb.toString();
    }

    public static void append(String[][] fields) throws IOException {
        String content = read().trim();
        StringBuilder newEntry = new StringBuilder("  {");
        for (int j = 0; j < fields.length; j++) {
            newEntry.append("\"").append(fields[j][0]).append("\": \"").append(fields[j][1]).append("\"");
            if (j < fields.length - 1) newEntry.append(", ");
        }
        newEntry.append("}");
        String updated = content.substring(0, content.lastIndexOf("]"))
                + ",\n" + newEntry + "\n]";

        BufferedWriter writer = new BufferedWriter(new FileWriter(FILE));
        writer.write(updated);
        writer.close();
        System.out.println("Appended record to " + FILE);
    }

    public static void main(String[] args) throws IOException {
        List<String[][]> data = new ArrayList<>();
        data.add(new String[][]{{"name", "Amitabh"}, {"age", "22"}, {"city", "Mathura"}});
        data.add(new String[][]{{"name", "Ayushmaan"},   {"age", "22"}, {"city", "Noida"}});
        write(data);
        append(new String[][]{{"name", "Shobhit"}, {"age", "23"}, {"city", "Gurgoan"}});
        System.out.println("\nReading " + FILE + ":");
        System.out.println(read());
    }
}
