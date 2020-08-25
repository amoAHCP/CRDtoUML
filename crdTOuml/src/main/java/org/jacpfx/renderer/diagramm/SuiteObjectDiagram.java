package org.jacpfx.renderer.diagramm;


import org.jacpfx.yaml.dto.ApplicationChart;
import org.jacpfx.yaml.dto.ApplicationPackage;
import org.jacpfx.yaml.model.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SuiteObjectDiagram {
    private final List<ApplicationPackage> applicationPackageList;
    public static final String LINEBREAK = "\n";
    private final boolean renderPorts;
    private final boolean renderMetadata;

    public SuiteObjectDiagram(List<ApplicationPackage> applicationPackageList,boolean renderPorts,boolean renderMetadata) {
        this.applicationPackageList = applicationPackageList;
        this.renderPorts = renderPorts;
        this.renderMetadata = renderMetadata;
    }

    @Override
    public String toString() {
        String start = "@startuml" + LINEBREAK;
        // TODO externalize as property
        start += "skinparam dpi 72" + LINEBREAK;
        start += "allow_mixing" + LINEBREAK;
       // start += "scale max 3072*2048"+ LINEBREAK;
        List<String> packages = this.applicationPackageList.
                stream().
                map(this::getPackage).
                collect(Collectors.toList());
        String packageString = String.join(LINEBREAK, packages);
        List<Application> applications = this.applicationPackageList.
                stream().
                flatMap(pack -> pack.getCharts().stream()).
                map(ApplicationChart::getApplication).
                filter(app->app!=null).
                collect(Collectors.toList());
        String relations = getRelations(applications);
        String end = "@enduml" + LINEBREAK;
        return start + packageString + LINEBREAK + relations + LINEBREAK + end;
    }

    private String getPackage(ApplicationPackage pack) {
        String packageString = "package " + pack.getName().strip() + " {" + LINEBREAK;
        List<Application> applications = pack.getCharts().
                stream().
                map(ApplicationChart::getApplication).
                collect(Collectors.toList());
        List<RelationalDatabaseRequirement> dbReq = pack.getCharts().
                stream().
                map(ApplicationChart::getDatabaseRequirement).
                collect(Collectors.toList());
        packageString += getObjects(applications);
        packageString += getDBObjects(dbReq);
        packageString += LINEBREAK + "}" + LINEBREAK;

        return packageString;
    }



    private String getDBObjects(List<RelationalDatabaseRequirement> applications) {
        List<String> collect = applications.stream().filter(a-> a!=null).map(application -> {
            String plainName = application.getMetadata().getName();
            String name = plainName.replace("-", "")+"DB";
           // String result = "database " + "\"" + name + "\" " + "as " + name + " { \n";
            String result = "database " + "\"" + plainName + "\" " + "as " + name + "  \n";
          //  result += "__ metadata __";
            result += "\n";
           // result += renderMetadata?getMetadata(List.of(application)):"";
            result += "\n";
          //  result += " -- container -- ";
            result += "\n";
           // result += getContainer(List.of(application));
            result += "\n";
           // result += "}";
            result += "\n";
           // result += renderPorts?getPorts(application):"";
            return result;
        }).collect(Collectors.toList());
        return String.join("\n", collect);
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
        List<String> collect = applications.stream().filter(a-> a!=null).map(application -> {
            String plainName = application.getMetadata().getName();
            String name = plainName.replace("-", "");
            String result = "object " + "\"" + plainName + "\" " + "as " + name + " { \n";
            result += "__ metadata __";
            result += "\n";
            result += renderMetadata?getMetadata(List.of(application)):"";
            result += "\n";
            result += " -- container -- ";
            result += "\n";
            result += getContainer(List.of(application));
            result += "\n";
            result += "}";
            result += "\n";
            result += renderPorts?getPorts(application):"";
            return result;
        }).collect(Collectors.toList());
        return String.join("\n", collect);
    }

    private String getPorts(Application application) {
        String plainAppName = application.getMetadata().getName();
        String nameApp = plainAppName.replace("-", "");
        List<String> ports = getPortList(application).
                stream().map(port -> {

            String plainName = port.getName()+ "Port";
            String name = nameApp+plainName.replace("-", "") ;
            String result = "object " + "\"" + plainName + "\" " + "as " + name + " { \n";
            result+= "portnumber:"+port.getContainerPort()+ "\n";
            result+= "protocol:"+port.getProtocol()+ "\n";
            result += "\n";
            result += "}";
            result += "\n";


            //String relation = nameApp + Relation.ARROW.getValue() + name;

            //result += relation;
            result += "\n";
            return result;
        }).collect(Collectors.toList());
        String relation = nameApp + Relation.ARROW.getValue() + nameApp+"Ports";
        String packageString = "package " + nameApp+"Ports" + " {" + LINEBREAK;
        packageString+= String.join("\n", ports);
        packageString += LINEBREAK + "}" + LINEBREAK;
        packageString += relation +  LINEBREAK;
        return packageString;
    }

    private List<Port> getPortList(Application application) {
        return Optional.
                ofNullable(Optional.
                        ofNullable(application.getSpec().getContainer()).
                        orElse(new Container()).getPorts()).
                orElse(Collections.emptyList());
    }
}
