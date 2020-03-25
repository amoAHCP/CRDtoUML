package org.jacpfx.deployment;

import org.jacpfx.deployment.command.helm.Command;
import org.jacpfx.deployment.command.helm.ShowValues;
import org.jacpfx.deployment.command.helm.Update;
import org.jacpfx.deployment.process.ProcessExecutor;
import org.jacpfx.deployment.process.ProcessResult;

import java.io.IOException;
import java.util.logging.Logger;

public class Helm {
    public static final int EXIT_CODE = 127;
    private final static Logger LOGGER = Logger.getLogger(Helm.class.getName());

    public static int helmRepoUpdate() {
        return helmRepoUpdate(new Update());
    }

    public static int helmRepoUpdate(Update update) {
        ProcessResult processResult = new ProcessResult(EXIT_CODE, "");
        try {
            LOGGER.info("start >>  " + update.getCommand());
            processResult = ProcessExecutor.executeUNIXProcess(update.getCommand());
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.info(processResult.getResult());
        return processResult.getStatus();
    }

    public static ProcessResult helmShowValues(ShowValues showValues) {
        return getProcessResult(showValues);
    }

    private static ProcessResult getProcessResult(Command command) {
        ProcessResult processResult = new ProcessResult(EXIT_CODE, "");
        try {
            LOGGER.info("start >>  " + command.getCommand());
            processResult = ProcessExecutor.executeUNIXProcess(command.getCommand());
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.info(processResult.getResult());
        return processResult;
    }

    public static ProcessResult downloadTemplates(ShowValues showValues) {
        return getProcessResult(showValues);
    }
}
