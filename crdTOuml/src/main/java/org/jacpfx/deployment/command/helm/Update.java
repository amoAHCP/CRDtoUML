package org.jacpfx.deployment.command.helm;

public class Update extends Command {

    public Update(String ...command) {
        super(command);
    }

    public Update() {
        super("helm repo update");
    }
}
