package org.jacpfx;

import org.jacpfx.deployment.Helm;
import org.jacpfx.deployment.command.helm.ShowValues;
import org.jacpfx.process.ProcessResult;

import java.io.*;

public class RenderValuesTest {

    public static void main(String[] args) throws IOException {
        ShowValues showValues = new ShowValues("adcubum/syrius-productmgmt-bl", "0.10.1");
        ProcessResult processResult = Helm.showValues(showValues);
        // Helm.helmRepoUpdate();
       // ProcessResult processResult = ProcessExecutor.executeUNIXProcess(new String[]{"helm show values adcubum/syrius-productmgmt-bl --version 0.10.1"});
        System.out.println("status: "+processResult.getStatus()+"  value: "+processResult.getResult().replace("\"\"", "\"123\""));

    }

    // InputStream -> File
    private static void copyInputStreamToFile(InputStream inputStream, File file)
            throws IOException {

        try (FileOutputStream outputStream = new FileOutputStream(file)) {

            int read;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }


        }

    }

}
