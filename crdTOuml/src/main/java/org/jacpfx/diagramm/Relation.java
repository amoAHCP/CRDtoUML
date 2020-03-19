package org.jacpfx.diagramm;

public enum Relation {
    EXTENSION(" <|-- "),
    COMPOSITION(" *-- "),
    AGGREGATION(" o-- "),
    CONNECTION(" .. "),
    ARROW(" --> ");
    private String value;

    Relation(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
