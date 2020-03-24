package org.jacpfx.files;

import java.util.Objects;

public class Source {
    private final String source;

    public Source(String source) {
        this.source = source;
        Objects.nonNull(source);
    }

    public String getSource() {
        return source;
    }
}
