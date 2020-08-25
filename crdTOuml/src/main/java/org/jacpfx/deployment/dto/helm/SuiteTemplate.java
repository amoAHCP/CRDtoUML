package org.jacpfx.deployment.dto.helm;

import java.util.List;

public class SuiteTemplate {
    private String name;
    private List<ChartTemplates> charts;

    public SuiteTemplate(String name, List<ChartTemplates> charts) {
        this.name = name;
        this.charts = charts;
    }

    public String getName() {
        return name;
    }

    public List<ChartTemplates> getCharts() {
        return charts;
    }
}
