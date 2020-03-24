package org.jacpfx.deployment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Helm {

    public static int helmRepoUpdate() {
        int exitCode = 127;
        ProcessBuilder processBuilder = new ProcessBuilder();
        // Windows
        processBuilder.command("/bin/bash", "-c", "helms repo update");

        try {

            Process process = processBuilder.start();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            exitCode = process.waitFor();
            System.out.println("\nExited with error code : " + exitCode);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return exitCode;
    }
}
