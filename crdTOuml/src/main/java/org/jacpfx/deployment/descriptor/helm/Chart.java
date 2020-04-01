package org.jacpfx.deployment.descriptor.helm;

import java.io.File;

public class Chart {
    private String chartName;
    private String chartVersion;
    private File values;

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

    public Chart() {
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

    public void setChartName(String chartName) {
        this.chartName = chartName;
    }

    public void setChartVersion(String chartVersion) {
        this.chartVersion = chartVersion;
    }

    public void setValues(File values) {
        this.values = values;
    }
}
