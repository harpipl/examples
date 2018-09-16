package pl.harpi.samples.lang.main;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main {
    private static String getValue(String key, Locale locale) {
        return ResourceBundle.getBundle("strings", locale).getString(key);
    }

    public static void main(String[] args) {
        Locale locale = new Locale("pl_PL");

        System.out.println(getValue("name", locale) + ": country=" + locale.getCountry() + ", language=" + locale.getLanguage());

    }
}
