package org.jacpfx.application;

import java.util.List;

public class Spec {
    private List<ApplicationDependency> applicationDependencies;

    public List<ApplicationDependency> getApplicationDependencies() {
        return applicationDependencies;
    }

    public void setApplicationDependencies(List<ApplicationDependency> applicationDependencies) {
        this.applicationDependencies = applicationDependencies;
    }

    @Override
    public String toString() {
        return "Spec{" +
                "applicationDependencies=" + applicationDependencies +
                '}';
    }
}
