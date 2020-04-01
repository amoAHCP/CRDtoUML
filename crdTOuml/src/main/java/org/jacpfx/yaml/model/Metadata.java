package org.jacpfx.yaml.model;

import java.util.Map;

public class Metadata {
    private String name;
    private Map<String, Object> labels;

    public Map<String, Object> getLabels() {
        return labels;
    }

    public void setLabels(Map<String, Object> labels) {
        this.labels = labels;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Metadata{" +
                "name='" + name + '\'' +
                ", labels=" + labels +
                '}';
    }
}
