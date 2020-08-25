package org.jacpfx;

import org.jacpfx.configuration.PropertyLoader;
import org.jacpfx.controller.ChartController;
import org.jacpfx.deployment.descriptor.helm.Chart;
import org.jacpfx.deployment.descriptor.helm.Charts;
import org.jacpfx.deployment.descriptor.helm.Suite;
import org.jacpfx.deployment.dto.helm.ChartTemplates;
import org.jacpfx.deployment.dto.helm.SuiteTemplate;
import org.jacpfx.renderer.diagramm.ObjectDiagram;
import org.jacpfx.renderer.diagramm.SuiteObjectDiagram;
import org.jacpfx.renderer.file.FileWriter;
import org.jacpfx.renderer.file.dto.Source;
import org.jacpfx.yaml.ApplicationLoader;
import org.jacpfx.yaml.YAMLParser;
import org.jacpfx.yaml.dto.ApplicationChart;
import org.jacpfx.yaml.dto.ApplicationPackage;
import org.jacpfx.yaml.model.Application;
import org.jacpfx.yaml.model.RelationalDatabaseRequirement;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class CMD implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        //downloadSimpleChartStructure();
        downloadPackageStructure();
        System.out.println("---");
    }

    private void downloadPackageStructure() throws IOException {
        ChartController ctr = new ChartController();
        String folder = PropertyLoader.getProperty("outputFolder");
        try (InputStream input = App.class.getClassLoader().getResourceAsStream(PropertyLoader.getProperty("charts"))) {
            Suite suite = YAMLParser.parse(Suite.class, input);
            List<SuiteTemplate> suiteTemplates = ctr.downloadSuite(suite);
            List<ApplicationPackage> applicationPackageList = suiteTemplates.stream().map(this::getApplicationPackage).collect(Collectors.toList());

            SuiteObjectDiagram suiteObjectDiagram = new SuiteObjectDiagram(applicationPackageList,true,true);
            String diagram = suiteObjectDiagram.toString();
            System.out.println(diagram);
            //    new FileWriter(new Content(svg), folder + "/output.svg").writeContentToFile();
            new FileWriter(new Source(diagram), folder + "/output.png").writeSourceToFile();
        }
    }

    private ApplicationPackage getApplicationPackage(SuiteTemplate suiteTemplate) {
        List<ApplicationChart> applications = suiteTemplate.getCharts().stream().
                filter(template -> template.isValid()).
                map(t -> t.getChartOutputFolder().toString()).
                map(fold -> getApplicationFiles(fold)).
                flatMap(List::stream).
                map(application -> new ApplicationChart(application)).

                collect(Collectors.toList());

        List<ApplicationChart> dbReqList = suiteTemplate.getCharts().stream().
                filter(template -> template.isValid()).
                map(t -> t.getChartOutputFolder().toString()).
                map(fold -> getDBRequirementFiles(fold)).
                flatMap(List::stream).
                map(dbReq -> new ApplicationChart(dbReq)).

                collect(Collectors.toList());

        return new ApplicationPackage(suiteTemplate.getName(), Stream.concat(applications.stream(), dbReqList.stream ()) .collect(Collectors.toList()));
    }



    private static List<Application> getApplicationFiles(String s)  {
        try {
            return new ApplicationLoader(s).getApplicationFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.EMPTY_LIST;
    }

    private static List<RelationalDatabaseRequirement> getDBRequirementFiles(String s)  {
        try {
            return new ApplicationLoader(s).getDBRequirementFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.EMPTY_LIST;
    }
}