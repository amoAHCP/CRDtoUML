package org.jacpfx.deployment.descriptor.helm;

import java.util.List;

public class Suite {

    private List<Charts> suite;

    public Suite(List<Charts> suite) {
        this.suite = suite;
    }

    public Suite() {
    }

    public List<Charts> getSuite() {
        return suite;
    }

    public void setSuite(List<Charts> suite) {
        this.suite = suite;
    }
}
