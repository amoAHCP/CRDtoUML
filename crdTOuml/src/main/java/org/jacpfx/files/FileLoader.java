package org.jacpfx.files;

import org.jacpfx.yaml.Application;
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

public class FileLoader {
    private static final Constructor CONSTRUCTOR = new Constructor(Application.class);
    private static final String YAML = ".yaml";
    private static final String YML = ".yml";
    private static final String KIND = "Application";
    private static Representer REPRESENTER = getREPRESENTER();
    private final String folder;

    public FileLoader(String folder) {
        this.folder = folder;
    }

    public List<Application> getApplicationFiles() throws IOException {
        return Files.list(Paths.get(folder)).
                filter(this::isYaml).
                map(this::getPath).
                map(this::getApplication).
                filter(this::isApplication).
                collect(Collectors.toList());
    }

    private String getPath(Path dir) {
        return dir.toAbsolutePath().toString();
    }

    private boolean isApplication(Application app) {
        return app.getKind() == null ? false : app.getKind().equalsIgnoreCase(KIND);
    }

    private boolean isYaml(Path path) {
        return path.toString().endsWith(YAML) || path.toString().endsWith(YML);
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

}