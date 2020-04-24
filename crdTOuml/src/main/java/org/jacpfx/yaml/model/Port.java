package org.jacpfx.yaml.model;

import java.util.Optional;

public class Port {
    private Integer servicePort;
    private Integer containerPort;
    private String protocol;
    private String name;
    private Ingress ingress;

    public Ingress getIngress() {
        return ingress;
    }

    public void setIngress(Ingress ingress) {
        this.ingress = ingress;
    }

    public String getName() {
        return name;
    }

    public Integer getContainerPort() {
        return Optional.ofNullable(containerPort).orElse(8080);
    }

    public Integer getServicePort() {
        return Optional.ofNullable(servicePort).orElse(8080);
    }

    public String getProtocol() {
        return Optional.ofNullable(protocol).orElse("http");
    }

    public void setServicePort(Integer servicePort) {
        this.servicePort = servicePort;
    }

    public void setContainerPort(Integer containerPort) {
        this.containerPort = containerPort;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Port{" +
                "servicePort=" + servicePort +
                ", containerPort=" + containerPort +
                ", protocol='" + protocol + '\'' +
                ", name='" + name + '\'' +
                ", ingress=" + ingress +
                '}';
    }
}
