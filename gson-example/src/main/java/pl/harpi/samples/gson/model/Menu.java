package pl.harpi.samples.gson.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Menu implements Serializable {
    private String name;
    private List<MenuItem> items;
}
