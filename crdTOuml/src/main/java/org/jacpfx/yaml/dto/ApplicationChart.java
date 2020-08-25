package org.jacpfx.yaml.dto;

import org.jacpfx.yaml.model.Application;
import org.jacpfx.yaml.model.RelationalDatabaseRequirement;

public class ApplicationChart {
    private Application application;
    private RelationalDatabaseRequirement databaseRequirement;

    public ApplicationChart(Application applications) {
        this.application = applications;
    }

    public ApplicationChart(Application application, RelationalDatabaseRequirement databaseRequirement) {
        this.application = application;
        this.databaseRequirement = databaseRequirement;
    }

    public ApplicationChart(RelationalDatabaseRequirement databaseRequirement) {
        this.databaseRequirement = databaseRequirement;
    }

    public RelationalDatabaseRequirement getDatabaseRequirement() {
        return databaseRequirement;
    }

    public Application getApplication() {
        return application;
    }
}
