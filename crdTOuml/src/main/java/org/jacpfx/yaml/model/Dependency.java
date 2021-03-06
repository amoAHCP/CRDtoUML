package org.jacpfx.yaml.model;

public class Dependency {
    private String name;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Dependency{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
