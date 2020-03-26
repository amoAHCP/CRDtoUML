package org.jacpfx.file;

import net.sourceforge.plantuml.SourceStringReader;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class FileWriter {

    private final Content content;
    private final Source source;
    private final String name;

    public FileWriter(Content content, String name) {
        this.source = null;
        this.content = content;
        this.name = name;
    }

    public FileWriter(Source source, String name) {
        this.source = source;
        this.content = null;
        this.name = name;
    }

    public void writeToPNG() throws IOException {
        Objects.nonNull(source);
        Objects.nonNull(name);
        FileOutputStream outputStream = new FileOutputStream(name);
        SourceStringReader reader = new SourceStringReader(source.getSource());
        // Write the first image to "png"
        try {
            reader.generateImage(outputStream);
        } finally {
            outputStream.close();
        }
    }


    public void writeToFile()
            throws IOException {
        Objects.nonNull(content);
        Objects.nonNull(name);
        FileOutputStream outputStream = new FileOutputStream(name);
        byte[] contentToBytes = content.getContent().getBytes();
       try {
           outputStream.write(contentToBytes);
       } finally {
           outputStream.close();
       }

    }


}
