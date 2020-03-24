package org.jacpfx.files;

import java.util.Objects;

public class Content {
    private final String content;

    public Content(String content) {
        this.content = content;
        Objects.nonNull(content);
    }

    public String getContent() {
        return content;
    }
}
