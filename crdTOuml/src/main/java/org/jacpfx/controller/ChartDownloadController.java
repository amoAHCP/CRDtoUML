package org.jacpfx.controller;

import org.jacpfx.deployment.Helm;
import org.jacpfx.deployment.command.helm.DownloadTemplates;
import org.jacpfx.deployment.command.helm.ShowValues;
import org.jacpfx.deployment.descriptor.helm.Chart;
import org.jacpfx.process.ProcessResult;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class ChartDownloadController {

    public void downloadCharts(Chart... charts) {
        Arrays.stream(charts).forEach(chart -> {
            ShowValues showValues = new ShowValues(chart.getChartName(), chart.getChartVersion());
            try {
                ProcessResult processResult = Helm.showValues(showValues);
                if (processResult.getStatus() == 0) {
                    File valuesFile = createValuesFile(chart, processResult);
                    Path outputFolder = Files.createTempDirectory(chart.getChartName() + "-");
                    DownloadTemplates myChart = new DownloadTemplates(chart.getChartName(), chart.getChartVersion(), outputFolder, valuesFile);
                    ProcessResult downloadResult = Helm.downloadTemplates(myChart);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    private File createValuesFile(Chart chart, ProcessResult processResult) {
        try {
            File valuesFile = File.createTempFile(chart.getChartName() + "values", null);
            valuesFile.deleteOnExit();
            FileWriter writer = new FileWriter(valuesFile);
            writer.write(processResult.getResult());
            writer.close();
            return valuesFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
