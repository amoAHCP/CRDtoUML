package org.jacpfx.yaml.model;

public class Route {
    private String subHost;
    private String path;

    public String getSubHost() {
        return subHost;
    }

    public void setSubHost(String subHost) {
        this.subHost = subHost;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Route{" +
                "subHost='" + subHost + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
