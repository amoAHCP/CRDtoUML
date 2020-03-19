package org.jacpfx;

import org.jacpfx.application.Application;
import org.jacpfx.diagramm.Objektdiagramm;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.representer.Representer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Hello world!
 */
public class App {
    public static final Constructor CONSTRUCTOR = new Constructor(Application.class);
    public static final String YAML = ".yaml";
    public static final String YML = ".yml";
    private static Representer REPRESENTER = getREPRESENTER();
    public static final Yaml YAMLOBJ = new Yaml(CONSTRUCTOR, REPRESENTER);

    public static void main(String[] args) throws IOException {
        App app = new App();
        List<Application> applicationFiles = app.getApplicationFiles("/Users/andy.moncsek/Documents/development/experiments/CRDtoUML/crdTOuml/files");
        System.out.println(new Objektdiagramm(applicationFiles).toString());
    }

    private List<Application> getApplicationFiles(String folder) throws IOException {
        Path path1 = Paths.get(folder);

        return Files.list(path1).
                filter(path -> path.toString().endsWith(YAML) || path.toString().endsWith(YML)).
                map(dir -> dir.toAbsolutePath().toString()).
                map(this::getApplication).collect(Collectors.toList());


    }

    private static Representer getREPRESENTER() {
        Representer representer = new Representer();
        representer.getPropertyUtils().setSkipMissingProperties(true);
        return representer;
    }


    private Application getApplication(String path) {

        InputStream inputStream = null;
        try {
            inputStream = Files.newInputStream(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return inputStream != null ? new Yaml(CONSTRUCTOR, REPRESENTER).load(inputStream) : new Application();
    }

    public void print() {


    }
}
