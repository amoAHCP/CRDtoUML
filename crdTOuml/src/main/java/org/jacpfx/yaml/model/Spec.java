package org.jacpfx.yaml.model;

import java.util.List;

public class Spec {
    private List<ApplicationDependency> applicationDependencies;

    private Container container;

    public List<ApplicationDependency> getApplicationDependencies() {
        return applicationDependencies;
    }

    public void setApplicationDependencies(List<ApplicationDependency> applicationDependencies) {
        this.applicationDependencies = applicationDependencies;
    }



    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    @Override
    public String toString() {
        return "Spec{" +
                "applicationDependencies=" + applicationDependencies +
                ", container=" + container +
                '}';
    }
}
