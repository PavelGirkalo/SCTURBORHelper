
package net.sourceforge.tess4j.util;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Utils {
/*
    //private static final Logger LOGGER = LoggerFactory.getLogger(new LoggHelper().toString());


    public static void writeFile(byte[] data, File outFile) throws IOException {
        // create parent dirs when necessary
        if (outFile.getParentFile() != null) {
            outFile.getParentFile().mkdirs();
        }
        try (FileOutputStream fos = new FileOutputStream(outFile)) {
            fos.write(data);
        }
    }


    public static String getConstantName(Object value, Class c) {
        for (Field f : c.getDeclaredFields()) {
            int mod = f.getModifiers();
            if (Modifier.isStatic(mod) && Modifier.isPublic(mod) && Modifier.isFinal(mod)) {
                try {
                    if (f.get(null).equals(value)) {
                        return f.getName();
                    }
                } catch (IllegalAccessException e) {
                    return String.valueOf(value);
                }
            }
        }
        return String.valueOf(value);
    }
    */
}
