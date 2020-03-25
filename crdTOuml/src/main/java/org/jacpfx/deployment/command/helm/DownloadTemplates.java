package org.jacpfx.deployment.command.helm;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;

public class DownloadTemplates extends Command {
    public static final String VERSION = "--version";
    public static final String VALUES = "--values=";
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
        String[] fullCommand = Arrays.copyOf(command, command.length+3);
        fullCommand[command.length] = this.chartName;
        fullCommand[command.length+1] = VERSION;
        fullCommand[command.length+2] = this.chartVersion;
        return fullCommand;
    }
}
