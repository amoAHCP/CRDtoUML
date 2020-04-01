package org.jacpfx.deployment.command.helm;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;

public class DownloadTemplates extends Command {
    public static final String VERSION = "--version";
    public static final String VALUES = "--values=";
    public static final String OUTPUT = "--output-dir";
    private final String chartName;
    private final String chartVersion;
    private final Path chartOutputFolder;
    private final File chartValues;





    public DownloadTemplates(String chartName, String chartVersion, Path chartOutputFolder, File chartValues) {
        super("helm template");
        this.chartName = chartName;
        this.chartVersion = chartVersion;
        this.chartOutputFolder = chartOutputFolder;
        this.chartValues = chartValues;


    }

    @Override
    public String[] getCommand() {
        String[] command = super.getCommand();
        String[] fullCommand = Arrays.copyOf(command, command.length+7);
        fullCommand[command.length] = this.chartName;
        fullCommand[command.length+1] = VERSION;
        fullCommand[command.length+2] = this.chartVersion;
        fullCommand[command.length+3] = VALUES;
        fullCommand[command.length+4] = this.chartValues.getAbsolutePath();
        fullCommand[command.length+5] = OUTPUT;
        fullCommand[command.length+6] = this.chartOutputFolder.toAbsolutePath().toString();
        return fullCommand;
    }

    @Override
    public String toString() {
        return "DownloadTemplates{" +
                "chartName='" + chartName + '\'' +
                ", chartVersion='" + chartVersion + '\'' +
                ", chartOutputFolder=" + chartOutputFolder +
                ", chartValues=" + chartValues +
                '}';
    }
}
