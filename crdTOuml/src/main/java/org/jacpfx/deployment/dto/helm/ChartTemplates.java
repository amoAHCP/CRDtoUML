package org.jacpfx.deployment.dto.helm;

import org.jacpfx.process.ProcessResult;

import java.io.File;
import java.nio.file.Path;

public class ChartTemplates {
    private final String chartCommand;
    private final String chartName;
    private final String chartVersion;
    private final Path chartOutputFolder;
    private final File chartValues;
    private final ProcessResult downloadResult;

    public ChartTemplates(String chartCommand, String chartName, String chartVersion, Path chartOutputFolder, File chartValues, ProcessResult downloadResult) {
        this.chartCommand = chartCommand;
        this.chartName = chartName;
        this.chartVersion = chartVersion;
        this.chartOutputFolder = chartOutputFolder;
        this.chartValues = chartValues;
        this.downloadResult = downloadResult;
    }

    public String getChartCommand() {
        return chartCommand;
    }

    public String getChartName() {
        return chartName;
    }

    public String getChartVersion() {
        return chartVersion;
    }

    public Path getChartOutputFolder() {
        return chartOutputFolder;
    }

    public File getChartValues() {
        return chartValues;
    }

    public ProcessResult getDownloadResult() {
        return downloadResult;
    }

    public boolean isValid() {
        return downloadResult.getStatus()==0;
    }
}
