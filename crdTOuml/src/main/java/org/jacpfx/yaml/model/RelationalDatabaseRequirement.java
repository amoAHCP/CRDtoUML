package org.jacpfx.yaml.model;

public class RelationalDatabaseRequirement {
    private String apiVersion;
    private String kind;
    private Metadata metadata;
    private DBSpec spec;

    public RelationalDatabaseRequirement(String apiVersion, String kind, Metadata metadata, DBSpec spec) {
        this.apiVersion = apiVersion;
        this.kind = kind;
        this.metadata = metadata;
        this.spec = spec;
    }

    public RelationalDatabaseRequirement() {
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public DBSpec getSpec() {
        return spec;
    }

    public void setSpec(DBSpec spec) {
        this.spec = spec;
    }
}
