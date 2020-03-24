package org.jacpfx;

import java.io.*;
import java.util.stream.Collectors;

public class RenderValues {

    public static void main(String[] args) {

        ProcessBuilder processBuilder = new ProcessBuilder();
        // Windows
        processBuilder.command("/bin/bash", "-c", "helms show values adcubum/syrius-productmgmt-bl --version 0.10.1");
      //  processBuilder.command("/bin/bash", "-c", "helm repo update");

        try {

            Process process = processBuilder.start();
            InputStream inputStream = process.getInputStream();


            File targetFile = new File("/Users/andy.moncsek/Documents/development/experiments/CRDtoUML/crdTOuml/files/targetFile.tmp");
            if(targetFile.exists()) {
                targetFile.delete();
                targetFile.createNewFile();
            } else {
                targetFile.createNewFile();
            }

         //   copyInputStreamToFile(inputStream,targetFile);

     /**   BufferedReader reader =
                    new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line.replace("\"\"","\"123\""));
            }**/
            String result = new BufferedReader(new InputStreamReader(inputStream))
                    .lines().collect(Collectors.joining("\n")).replace("\"\"","\"123\"");
            System.out.println(result);

            int exitCode = process.waitFor();
            if(exitCode!=0){
                BufferedReader reader2 =
                        new BufferedReader(new InputStreamReader(  process.getErrorStream()));

                String line2;
                while ((line2 = reader2.readLine()) != null) {
                    System.out.println(line2);
                }
            }

            System.out.println("\nExited with error code : " + exitCode);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
