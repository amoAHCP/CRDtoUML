package org.jacpfx.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProcessExecutor {


    public static final String DELIMITER = "\n";

    public static ProcessResult executeUNIXProcess(String[] arg) throws IOException {
        Objects.nonNull(arg);
        final ProcessBuilder processBuilder = new ProcessBuilder();
        final Process process = processBuilder.command("/bin/bash", "-c", getCommand(arg)).start();
        return getProcessResult(process);
    }

    private static ProcessResult getProcessResult(Process process) throws IOException {
        Objects.nonNull(process);
        int exitCode = getExitCode(process);
        switch (exitCode) {
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
        return String.join(" ", Arrays.asList(arg));
    }

    private static String getOutputAsString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            return bufferedReader.
                    lines().
                    collect(Collectors.joining(DELIMITER));
        } finally {
            bufferedReader.close();
        }

    }
}
