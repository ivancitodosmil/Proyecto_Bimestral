package Logica;

import java.util.regex.Pattern;

public class LPersona {

    public static boolean validaCedula(String cedula) {
        String regex = "\\d{10}";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(cedula).matches();
    }

    public static boolean validaEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
}
