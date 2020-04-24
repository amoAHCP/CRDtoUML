package org.jacpfx.renderer.file;

import net.sourceforge.plantuml.SourceStringReader;
import org.jacpfx.deployment.descriptor.helm.Chart;
import org.jacpfx.process.ProcessExecutor;
import org.jacpfx.process.ProcessResult;
import org.jacpfx.renderer.file.dto.Content;
import org.jacpfx.renderer.file.dto.Source;

import java.io.File;
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

    public void write() throws IOException {
        ProcessResult plantuml = ProcessExecutor.executeUNIXProcess("plantuml");
        System.out.println(plantuml.getResult());
    }
    private File createValuesFile(String name , ProcessResult processResult, String emptyValues) {
        try {
            File valuesFile = File.createTempFile(name + "values", ".yml");
            valuesFile.deleteOnExit();
            java.io.FileWriter writer = new java.io.FileWriter(valuesFile);
            writer.write(processResult.getResult().replace("\"\"", emptyValues));
            writer.close();
            return valuesFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    // TODO create Bi-consumer and remove dependency
    public void writeSourceToFile() throws IOException {
        Objects.nonNull(source);
        Objects.nonNull(name);
        FileOutputStream outputStream = new FileOutputStream(name);
        SourceStringReader reader = new SourceStringReader(source.getSource());
        try {
            reader.generateImage(outputStream);
        } finally {
            outputStream.close();
        }
    }


    public void writeContentToFile()
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
