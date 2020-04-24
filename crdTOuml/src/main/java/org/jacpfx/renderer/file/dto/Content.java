package org.jacpfx.renderer.file.dto;

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
