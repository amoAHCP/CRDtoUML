package org.jacpfx.yaml.model;

import java.util.List;

public class DBSpec {
    private List<String> vendors;

    public DBSpec(List<String> vendors) {
        this.vendors = vendors;
    }

    public DBSpec() {
    }

    public List<String> getVendors() {
        return vendors;
    }

    public void setVendors(List<String> vendors) {
        this.vendors = vendors;
    }
}
