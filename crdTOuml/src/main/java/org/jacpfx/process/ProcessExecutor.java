package org.jacpfx.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProcessExecutor {


    public static ProcessResult executeUNIXProcess(String[] arg) throws IOException {
        Objects.nonNull(arg);
        final ProcessBuilder processBuilder = new ProcessBuilder();
        final String command = getCommand(arg);
        // processBuilder.command("/bin/bash", "-c", "helms show values adcubum/syrius-productmgmt-bl --version 0.10.1");
        processBuilder.command("/bin/bash", "-c", command);
        final Process process = processBuilder.start();
        final InputStream inputStream = process.getInputStream();
        return getProcessResult(process, inputStream);
    }

    private static ProcessResult getProcessResult(Process process, InputStream inputStream) {
        String result = getOutputAsString(inputStream);
        System.out.println(result);

        int exitCode = getExitCode(process);
        switch (exitCode){
            case 0:
                return new ProcessResult(exitCode,result.replace("\"\"", "\"123\""));
            default:
                return new ProcessResult(exitCode,getOutputAsString(process.getErrorStream()));
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

    private static String getOutputAsString(InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(inputStream))
                .lines().collect(Collectors.joining("\n"));
    }
}
