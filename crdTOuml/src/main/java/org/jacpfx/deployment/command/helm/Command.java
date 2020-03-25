package org.jacpfx.deployment.command.helm;

public class Command {
    private final String[] command;

    public Command(String ...command) {
        this.command = command;
    }

    public String[] getCommand() {
        return command;
    }
}
