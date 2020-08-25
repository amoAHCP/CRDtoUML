package org.jacpfx.deployment.descriptor.helm;

import java.util.List;

public class Charts {
    private String name;
    private List<Chart> charts;

    public Charts(String name, List<Chart> charts) {
        this.name = name;
        this.charts = charts;
    }

    public Charts() {
    }

    public List<Chart> getCharts() {
        return charts;
    }

    public void setCharts(List<Chart> charts) {
        this.charts = charts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
