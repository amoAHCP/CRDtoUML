package org.jacpfx;

import org.jacpfx.process.ProcessExecutor;
import org.jacpfx.process.ProcessResult;

import java.io.*;
import java.util.stream.Collectors;

public class RenderValuesTest {

    public static void main(String[] args) throws IOException {


        ProcessResult processResult = ProcessExecutor.executeUNIXProcess(new String[]{"helms show values adcubum/syrius-productmgmt-bl --version 0.10.1"});
        System.out.println("status: "+processResult.getStatus()+"  value: "+processResult.getResult());

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
