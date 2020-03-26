package org.jacpfx.deployment.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ProcessExecutor {
    private final static Logger LOGGER = Logger.getLogger(ProcessExecutor.class.getName());
    public static final String LINE_DELIMITER = "\n";
    public static final String ARRAY_DELIMITER = " ";

    public static ProcessResult executeUNIXProcess(String... arg) throws IOException {
        Objects.nonNull(arg);
        final ProcessBuilder processBuilder = new ProcessBuilder();
        final String command = getCommand(arg);
        LOGGER.info("execute command: " + command);
        return getProcessResult(
                processBuilder.
                        command("/bin/bash", "-c", command).
                        start()
        );
    }

    private static ProcessResult getProcessResult(Process process) throws IOException {
        Objects.nonNull(process);
        int exitCode = getExitCode(process);
        switch (getExitCode(process)) {
            case 0:
                return new ProcessResult(exitCode, getOutputAsString(process.getInputStream()));
            default:
                return new ProcessResult(exitCode, getOutputAsString(process.getErrorStream()));
        }
    }

    private static int getExitCode(Process process) {
        int exitCode = 127;
        try {
            exitCode = process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return exitCode;
    }

    private static String getCommand(String[] arg) {
        return String.join(ARRAY_DELIMITER, Arrays.asList(arg));
    }

    private static String getOutputAsString(InputStream inputStream) throws IOException {
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            return bufferedReader.
                    lines().
                    collect(Collectors.joining(LINE_DELIMITER));
        } finally {
            bufferedReader.close();
        }

    }
}
