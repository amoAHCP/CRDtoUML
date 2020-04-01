package org.jacpfx;

import org.jacpfx.configuration.PropertyLoader;
import org.jacpfx.controller.ChartController;
import org.jacpfx.deployment.descriptor.helm.Chart;
import org.jacpfx.deployment.descriptor.helm.Charts;
import org.jacpfx.deployment.dto.helm.ChartTemplates;

import org.jacpfx.yaml.YAMLParser;
import org.jacpfx.yaml.model.Application;
import org.jacpfx.renderer.diagramm.SimpleObjectDiagram;
import org.jacpfx.yaml.ApplicationLoader;


import org.jacpfx.renderer.file.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Hello world!
 */
public class App {


    public static void main(String[] args) throws IOException {
        ChartController ctr = new ChartController();
        String folder = PropertyLoader.getProperty("outputFolder");
        try (InputStream input = App.class.getClassLoader().getResourceAsStream(PropertyLoader.getProperty("charts"))) {
            Charts charts = YAMLParser.parse(Charts.class, input);
            List<ChartTemplates> chartTemplates = ctr.downloadCharts(charts.getCharts().stream().toArray(Chart[]::new));
            List<Application> applicationFiles = chartTemplates.
                    stream().
                    filter(template -> template.isValid()).
                    map(t -> t.getChartOutputFolder().toString()).
                    map(fold -> getApplicationFiles(fold)).
                    flatMap(List::stream).
                    collect(Collectors.toList());
            //  List<Application> applicationFiles = getApplicationFiles(folder);
            String svg = new SimpleObjectDiagram(applicationFiles).toSVG();
            new FileWriter(new Content(svg), folder + "/output.svg").writeContentToFile();
            new FileWriter(new Source(new SimpleObjectDiagram(applicationFiles).toString()), folder + "/output.png").writeSourceToFile();
        }
        System.out.println("---");
     /**   List<ChartTemplates> chartTemplates = ctr.downloadCharts(
                new Chart("adcubum/syrius-productmgmt-bl", "0.10.1"),
                new Chart("adcubum/syrius-mktgdist-salessupport-webapp", "0.1.0-rc.1"),
                new Chart("adcubum/syrius-erp-salessupport-bff-expert", "0.1.0-rc.1"),
                new Chart("adcubum/syrius-erp-salessupport-bl", "0.1.0-rc.1"));**/

    }

    private static List<Application> getApplicationFiles(String s)  {
        try {
            return new ApplicationLoader(s).getApplicationFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.EMPTY_LIST;
    }




    public void print() {


    }
}
