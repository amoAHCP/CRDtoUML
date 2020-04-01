package org.jacpfx.controller;

import org.jacpfx.configuration.PropertyLoader;
import org.jacpfx.deployment.Helm;
import org.jacpfx.deployment.command.helm.DownloadTemplates;
import org.jacpfx.deployment.command.helm.ShowValues;
import org.jacpfx.deployment.descriptor.helm.Chart;
import org.jacpfx.deployment.dto.helm.ChartTemplates;
import org.jacpfx.process.ProcessResult;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ChartController {
    private final static Logger LOGGER = Logger.getLogger(ChartController.class.getName());
    public static final String ARRAY_DELIMITER = " ";
    public static final String EMPTY_VALUES = PropertyLoader.getProperty("emptyValues");
    public static final String DELIMITER = "/";

    public List<ChartTemplates> downloadCharts(Chart... charts) {
        return Arrays.stream(charts).map(this::getChartTemplate).collect(Collectors.toList());

    }

    private ChartTemplates getChartTemplate(Chart chart) {
        ShowValues showValues = new ShowValues(chart.getChartName(), chart.getChartVersion());
        try {
            ProcessResult processResult = Helm.showValues(showValues);
            if (processResult.getStatus() == 0) {
                File valuesFile = createValuesFile(chart, processResult, EMPTY_VALUES);
                Path outputFolder = Files.createTempDirectory(chart.getChartName().contains(DELIMITER) ? chart.getChartName().substring(chart.getChartName().lastIndexOf(DELIMITER) + 1) : chart.getChartName());
                DownloadTemplates myChart = new DownloadTemplates(chart.getChartName(), chart.getChartVersion(), outputFolder, valuesFile);
                ProcessResult downloadResult = Helm.downloadTemplates(myChart);
                LOGGER.info(myChart.toString());
                return new ChartTemplates(String.join(ARRAY_DELIMITER, Arrays.asList(myChart.getCommand())), chart.getChartName(), chart.getChartVersion(), outputFolder, valuesFile, downloadResult);
            } else {
                return new ChartTemplates(String.join(ARRAY_DELIMITER, Arrays.asList(showValues.getCommand())), chart.getChartName(), chart.getChartVersion(), null, null, processResult);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ChartTemplates("", chart.getChartName(), chart.getChartVersion(), null, null, new ProcessResult(256, e.getMessage()));
        }
    }

    private File createValuesFile(Chart chart, ProcessResult processResult, String emptyValues) {
        try {
            File valuesFile = File.createTempFile(chart.getChartName() + "values", ".yml");
            valuesFile.deleteOnExit();
            FileWriter writer = new FileWriter(valuesFile);
            writer.write(processResult.getResult().replace("\"\"", emptyValues));
            writer.close();
            return valuesFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
