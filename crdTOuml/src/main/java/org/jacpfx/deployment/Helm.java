package org.jacpfx.deployment;

import org.jacpfx.deployment.command.helm.Command;
import org.jacpfx.deployment.command.helm.DownloadTemplates;
import org.jacpfx.deployment.command.helm.ShowValues;
import org.jacpfx.deployment.command.helm.Update;
import org.jacpfx.process.ProcessExecutor;
import org.jacpfx.process.ProcessResult;

import java.io.IOException;
import java.util.logging.Logger;

public class Helm {
    public static final int EXIT_CODE = 127;
    private final static Logger LOGGER = Logger.getLogger(Helm.class.getName());

    public static int repoUpdate() {
        return repoUpdate(new Update());
    }

    public static int repoUpdate(Update update) {
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

    public static ProcessResult showValues(ShowValues showValues) throws IOException {
        return getProcessResult(showValues);
    }

    public static ProcessResult downloadTemplates(DownloadTemplates downloadTemplates) throws IOException {
        return getProcessResult(downloadTemplates);
    }

    private static ProcessResult getProcessResult(Command command) throws IOException {
        ProcessResult processResult = ProcessExecutor.executeUNIXProcess(command.getCommand());
        LOGGER.info(processResult.getResult());
        return processResult;
    }

}
