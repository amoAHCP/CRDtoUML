package org.jacpfx.renderer.diagramm;

import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;
import org.jacpfx.yaml.model.Application;
import org.jacpfx.yaml.model.ApplicationDependency;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SimpleObjectDiagram {
    private final List<Application> applications;

    public SimpleObjectDiagram(List<Application> applications) {
        this.applications = applications;
    }

    @Override
    public String toString() {
        String linebreak = "\n";
        String start = "@startuml" + linebreak;
        // String objects = getObjects(applications);
        String relations = getRelations(applications);
        String end = "@enduml" + linebreak;
        return start + relations + linebreak + end;
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

    private String getRelations(List<Application> applications) {
        Stream<String> stringStream = applications.
                stream().
                flatMap(app -> Optional.
                        ofNullable(app.getSpec().getApplicationDependencies()).
                        orElse(Collections.<ApplicationDependency>emptyList()).
                        stream().
                        filter(Objects::nonNull).
                        map(dep -> "[" + app.getMetadata().getName() + "]" + Relation.ARROW.getValue() + "[" + dep.getName() + "]"));
        return String.join("\n", stringStream.collect(Collectors.toList()));
    }

    private String getObjects(List<Application> applications) {
        return String.join("\n", applications.stream().map(application -> application.getMetadata().getName()).map(name -> "object " + name).collect(Collectors.toList()));
    }
}
