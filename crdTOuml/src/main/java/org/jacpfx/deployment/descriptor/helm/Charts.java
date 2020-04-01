package org.jacpfx.deployment.descriptor.helm;

import java.util.List;

public class Charts {
    private List<Chart> charts;

    public Charts(List<Chart> charts) {
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
}
