package org.jacpfx.file;

import java.io.IOException;

public interface ProduceIO {

    public <T>T get() throws IOException;
}
