package practice;

import java.util.regex.Pattern;

public class email {
    public static void main(String[] args) { // Added 'public static' and 'String[] args'
        String emailTest = "Amitabh128@gmail.comm";
        String regex = "^[a-zA-Z0-9]+@[a-zA-Z]+\\.[a-zA-Z]{2,}$";


        // Regex MUST be the first argument
        boolean valid = Pattern.matches(regex, emailTest); //--->



        System.out.println("Is valid: " + valid);
        String input = "My email is amitabh128@gmail.com and work is amit@work.com";
        String hidden = input.replaceAll("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}", "[HIDDEN]");

        System.out.println(hidden);

    }
}
