package org.jacpfx.renderer.diagramm;

import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;
import org.jacpfx.yaml.model.Application;
import org.jacpfx.yaml.model.ApplicationDependency;
import org.jacpfx.yaml.model.Container;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ObjectDiagram {
    private final List<Application> applications;

    public ObjectDiagram(List<Application> applications) {
        this.applications = applications;
    }

    @Override
    public String toString() {
        String linebreak = "\n";
        String start = "@startuml" + linebreak;
        //System.out.println("meta: "+getMetadata(applications));
        String objects = getObjects(applications);
        String relations = getRelations(applications);
        String end = "@enduml" + linebreak;
        return start + objects + linebreak + relations + linebreak + end;
    }


    public String toSVG() throws IOException {

        SourceStringReader reader = new SourceStringReader(toString());
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        // Write the first image to "os"
        try {
            String desc = reader.generateImage(os, new FileFormatOption(FileFormat.SVG));
        } catch (IOException e) {
            e.printStackTrace();
        }


        // The XML is stored into svg
        final String svg = new String(os.toByteArray(), Charset.forName("UTF-8"));

        os.close();
        return svg;
    }

    private String getMetadata(List<Application> applications) {
        Stream<String> stringStream = applications.
                stream().
                flatMap(app -> Optional.
                        ofNullable(app.getMetadata().getLabels()).
                        orElse(Collections.<String, Object>emptyMap()).
                        entrySet().
                        stream().
                        filter(Objects::nonNull).
                        map(dep -> dep.getKey() + Relation.EQUALS.getValue() + dep.getValue().toString()));
        return String.join("\n", stringStream.collect(Collectors.toList()));
    }

    private String getRelations(List<Application> applications) {
        Stream<String> stringStream = applications.
                stream().
                flatMap(app -> Optional.
                        ofNullable(app.getSpec().getApplicationDependencies()).
                        orElse(Collections.<ApplicationDependency>emptyList()).
                        stream().
                        filter(Objects::nonNull).
                        map(dep -> app.getMetadata().getName().replace("-", "") + Relation.ARROW.getValue() + dep.getName().replace("-", "") + ":" + dep.getType()));
        return String.join("\n", stringStream.collect(Collectors.toList()));
    }

    private String getContainer(List<Application> applications) {
        List<String> collect = applications.
                stream().
                map(app -> Optional.
                        ofNullable(app.getSpec().getContainer()).
                        orElse(new Container())).map(container -> {
            String result = "image = " + container.getImage();
            result += "\n";
            return result;
        }).collect(Collectors.toList());
        return String.join("\n", collect);
    }

    private String getObjects(List<Application> applications) {
        List<String> collect = applications.stream().map(application -> {
            String name = application.getMetadata().getName().replace("-", "");
            String result = "object " + name + " { \n";
            result += "__ metadata __";
            result += "\n";
            result += getMetadata(List.of(application));
            result += "\n";
            result += " -- container -- ";
            result += "\n";
            result += getContainer(List.of(application));
            result += "\n";
            result += "}";
            return result;
        }).collect(Collectors.toList());
        //return String.join("\n", applications.stream().map(application -> application.getMetadata().getName()).map(appName -> appName.replace("-", "")).map(name -> "object " + name).collect(Collectors.toList()));
        return String.join("\n", collect);
    }
}
