package org.jacpfx.yaml.model;

public class Ingress {
    private Http http;

    public Http getHttp() {
        return http;
    }

    public void setHttp(Http http) {
        this.http = http;
    }

    @Override
    public String toString() {
        return "Ingress{" +
                "http=" + http +
                '}';
    }
}
