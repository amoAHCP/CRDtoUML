package org.jacpfx;

import org.jacpfx.file.Content;
import org.jacpfx.file.FileWriter;
import org.jacpfx.file.Source;
import org.jacpfx.yaml.Application;
import org.jacpfx.diagramm.SimpleObjectDiagram;
import org.jacpfx.file.FileLoader;

import java.io.IOException;
import java.util.List;

/**
 * Hello world!
 */
public class App {


    public static void main(String[] args) throws IOException {

        String folder = "/Users/andy.moncsek/Documents/development/experiments/CRDtoUML/crdTOuml/files";
        List<Application> applicationFiles = new FileLoader(folder).getApplicationFiles();
        new FileWriter(new Content(new SimpleObjectDiagram(applicationFiles).toSVG()), folder + "/output.svg").writeToFile();
        new FileWriter(new Source(new SimpleObjectDiagram(applicationFiles).toString()), folder + "/output.png").writeToPNG();
    }


    public void print() {


    }
}
