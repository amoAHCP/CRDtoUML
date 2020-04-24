package org.jacpfx;

import org.jacpfx.configuration.PropertyLoader;
import org.jacpfx.controller.ChartController;
import org.jacpfx.deployment.descriptor.helm.Chart;
import org.jacpfx.deployment.descriptor.helm.Charts;
import org.jacpfx.deployment.dto.helm.ChartTemplates;
import org.jacpfx.renderer.diagramm.ObjectDiagram;
import org.jacpfx.renderer.file.FileWriter;
import org.jacpfx.renderer.file.dto.Source;
import org.jacpfx.yaml.ApplicationLoader;
import org.jacpfx.yaml.YAMLParser;
import org.jacpfx.yaml.model.Application;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CMD implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
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
                    peek(app-> System.out.println(app.toString())).
                    collect(Collectors.toList());
            //  List<Application> applicationFiles = getApplicationFiles(folder);
            // String svg = new ObjectDiagram(applicationFiles).toSVG();
            String png = new ObjectDiagram(applicationFiles).toString();
            System.out.println(png);
            //    new FileWriter(new Content(svg), folder + "/output.svg").writeContentToFile();
            new FileWriter(new Source(png), folder + "/output.png").writeSourceToFile();
        }
        System.out.println("---");
    }
    private static List<Application> getApplicationFiles(String s)  {
        try {
            return new ApplicationLoader(s).getApplicationFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.EMPTY_LIST;
    }
}