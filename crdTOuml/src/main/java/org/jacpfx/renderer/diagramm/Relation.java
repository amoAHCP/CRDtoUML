package org.jacpfx.renderer.diagramm;

public enum Relation {
    EXTENSION(" <|-- "),
    COMPOSITION(" *-- "),
    AGGREGATION(" o-- "),
    CONNECTION(" .. "),
    EQUALS(" = "),
    ARROW(" --> ");

    private String value;

    Relation(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
