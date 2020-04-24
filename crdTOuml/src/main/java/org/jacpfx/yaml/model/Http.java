package org.jacpfx.yaml.model;

import java.util.List;

public class Http {
    private boolean enabled;
    private List<Route> routes;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    @Override
    public String toString() {
        return "Http{" +
                "enabled=" + enabled +
                ", routes=" + routes +
                '}';
    }
}
