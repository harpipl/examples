package pl.harpi.samples.gson.main;

import com.google.gson.Gson;
import pl.harpi.samples.gson.model.Menu;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Gson gson = new Gson();

        Reader reader = new FileReader("src/main/resources/menu.json");

        Menu json = gson.fromJson(reader, Menu.class);

        System.out.println("OK");
    }
}
