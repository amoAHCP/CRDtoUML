package org.jacpfx.yaml.dto;

import java.util.List;

public class ApplicationPackage {
    private String name;
    private List<ApplicationChart> charts;

    public ApplicationPackage(String name, List<ApplicationChart> charts) {
        this.name = name;
        this.charts = charts;
    }

    public String getName() {
        return name;
    }

    public List<ApplicationChart> getCharts() {
        return charts;
    }
}
