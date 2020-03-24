package org.jacpfx.files;

import java.io.IOException;

public interface ProduceIO {

    public <T>T get() throws IOException;
}
