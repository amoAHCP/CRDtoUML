package org.jacpfx.yaml;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.representer.Representer;

import java.io.IOException;
import java.io.InputStream;

public class YAMLParser {

    private static Representer getREPRESENTER() {
        Representer representer = new Representer();
        representer.getPropertyUtils().setSkipMissingProperties(true);
        return representer;
    }

    public static <T> T parse(Class<T> clazz, InputStream stream) {
       try{
           return new Yaml(new Constructor(clazz), getREPRESENTER()).load(stream);
       } finally {
           close(stream);
       }
    }

    private static void close(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
