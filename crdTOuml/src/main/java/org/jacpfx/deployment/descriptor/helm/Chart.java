package org.jacpfx.deployment.descriptor.helm;

import java.io.File;

public class Chart {
    private final String chartName;
    private final String chartVersion;
    private final File values;

    public Chart(String chartName, String chartVersion, File values) {
        this.chartName = chartName;
        this.chartVersion = chartVersion;
        this.values = values;
    }

    public Chart(String chartName, String chartVersion) {
        this.chartName = chartName;
        this.chartVersion = chartVersion;
        this.values = null;
    }

    public String getChartName() {
        return chartName;
    }

    public String getChartVersion() {
        return chartVersion;
    }

    public File getValues() {
        return values;
    }
}
