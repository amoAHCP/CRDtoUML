package org.jacpfx.yaml.model;

import io.fabric8.kubernetes.api.model.Probe;
import io.fabric8.kubernetes.api.model.ResourceRequirements;

import java.util.ArrayList;
import java.util.List;

public class Container {
    private List<String> args = new ArrayList();
    private List<String> command = new ArrayList();
    private String image;
    private List<Port> ports = new ArrayList();
    private Probe livenessProbe;;
    private Probe readinessProbe;
    private ResourceRequirements resources;

    public List<String> getArgs() {
        return args;
    }

    public void setArgs(List<String> args) {
        this.args = args;
    }

    public List<String> getCommand() {
        return command;
    }

    public void setCommand(List<String> command) {
        this.command = command;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Port> getPorts() {
        return ports;
    }

    public void setPorts(List<Port> ports) {
        this.ports = ports;
    }

    public Probe getLivenessProbe() {
        return livenessProbe;
    }

    public void setLivenessProbe(Probe livenessProbe) {
        this.livenessProbe = livenessProbe;
    }

    public Probe getReadinessProbe() {
        return readinessProbe;
    }

    public void setReadinessProbe(Probe readinessProbe) {
        this.readinessProbe = readinessProbe;
    }

    public ResourceRequirements getResources() {
        return resources;
    }

    public void setResources(ResourceRequirements resources) {
        this.resources = resources;
    }

    @Override
    public String toString() {
        return "Container{" +
                "args=" + args +
                ", command=" + command +
                ", image='" + image + '\'' +
                ", ports=" + ports +
                ", livenessProbe=" + livenessProbe +
                ", readinessProbe=" + readinessProbe +
                ", resources=" + resources +
                '}';
    }
}
