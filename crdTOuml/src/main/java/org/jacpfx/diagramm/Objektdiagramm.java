package org.jacpfx.diagramm;

import org.jacpfx.application.Application;
import org.jacpfx.application.ApplicationDependency;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Objektdiagramm {
    private final List<Application> applications;

    public Objektdiagramm(List<Application> applications) {
        this.applications = applications;
    }

    @Override
    public String toString() {
        String linebreak = "\n";
        String start = "@startuml" + linebreak;
       // String objects = getObjects(applications);
        String relations = getRelations(applications);
        String end = "@enduml" + linebreak;
        return start  + relations + linebreak + end;
    }

    private String getRelations(List<Application> applications) {
        Stream<String> stringStream = applications.stream().flatMap(app -> {

            String name = app.getMetadata().getName();
            System.out.println("NAME: "+name);
            List<ApplicationDependency> applicationDependencies = Optional.ofNullable(app.getSpec().getApplicationDependencies()).orElse(Collections.EMPTY_LIST);

            System.out.println(applicationDependencies);
            return applicationDependencies.stream().filter(d -> d!=null).map(dep -> "["+name+"]" + Relation.ARROW.getValue() + "["+dep.getName()+"]");
        });
        return String.join("\n", stringStream.collect(Collectors.toList()));
    }

    private String getObjects(List<Application> applications) {
        return String.join("\n", applications.stream().map(application -> application.getMetadata().getName()).map(name -> "object " + name).collect(Collectors.toList()));
    }
}
