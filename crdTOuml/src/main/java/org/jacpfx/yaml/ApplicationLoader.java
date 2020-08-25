package org.jacpfx.yaml;

import org.jacpfx.yaml.model.Application;
import org.jacpfx.yaml.model.RelationalDatabaseRequirement;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.representer.Representer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.Files.newInputStream;

public class ApplicationLoader {
    private static final Constructor CONSTRUCTOR = new Constructor(Application.class);
    private static final String YAML = ".yaml";
    private static final String YML = ".yml";
    private static final String KIND_APPLICATION = "Application";
    private static final String KIND_DBREQ = "RelationalDatabaseRequirement";
    private static Representer REPRESENTER = getREPRESENTER();
    private final String folder;

    public ApplicationLoader(String folder) {
        this.folder = folder;
    }

    public List<Application> getApplicationFiles() throws IOException {
        return Files.walk(Paths.get(folder))
                .filter(Files::isRegularFile).
                filter(this::isYaml).
                map(this::getPath).
                map(this::getApplication).
                filter(this::isApplication).
                collect(Collectors.toList());
    }
    public List<RelationalDatabaseRequirement> getDBRequirementFiles() throws IOException {
        return Files.walk(Paths.get(folder))
                .filter(Files::isRegularFile).
                        filter(this::isYaml).
                        map(this::getPath).
                        map(this::getRelationalDatabaseRequirement).
                        filter(this::isDBRequiremen).
                        collect(Collectors.toList());
    }

    private String getPath(Path dir) {
        return dir.toAbsolutePath().toString();
    }

    private boolean isApplication(Application app) {
        return app.getKind() == null ? false : app.getKind().equalsIgnoreCase(KIND_APPLICATION);
    }

    private boolean isDBRequiremen(RelationalDatabaseRequirement app) {
        return app.getKind() == null ? false : app.getKind().equalsIgnoreCase(KIND_DBREQ);
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
            inputStream = newInputStream(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream != null ? YAMLParser.parse(Application.class,inputStream) : new Application();

    }

    private RelationalDatabaseRequirement getRelationalDatabaseRequirement(String path) {

        InputStream inputStream = null;
        try {
            inputStream = newInputStream(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream != null ? YAMLParser.parse(RelationalDatabaseRequirement.class,inputStream) : new RelationalDatabaseRequirement();

    }





}
