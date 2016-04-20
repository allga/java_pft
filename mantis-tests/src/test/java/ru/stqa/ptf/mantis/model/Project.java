package ru.stqa.ptf.mantis.model;

/**
 * Created by Olga on 20.04.2016.
 */
public class Project {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public Project setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Project setName(String name) {
        this.name = name;
        return this;
    }
}
