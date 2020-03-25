package org.jacpfx.deployment.command.helm;

import java.util.Arrays;

public class ShowValues extends Command {
    public static final String VERSION = "--version";
    private final String chartName;
    private final String chartVersion;

    public ShowValues(String chartName, String chartVersion, String... command) {
        super(command);
        this.chartName = chartName;
        this.chartVersion = chartVersion;
    }


    public ShowValues(String chartName, String chartVersion) {
        super("helm show values");
        this.chartName = chartName;
        this.chartVersion = chartVersion;
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
