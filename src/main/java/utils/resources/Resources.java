package utils.resources;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * this class contains only static methods to read resource files.
 * it can get the resource path, read a resource file to a single string
 * or read it to a list of strings with one string per line.
 */
@SuppressWarnings("unused")
public class Resources {

    /**
     * returns the system, file path of a resource file
     *
     * @param filename name of the resource file
     * @return the system file path of the resource file
     * @exception FileNotFoundException thrown if the file cant be found
     *
     */
    public static String getResourcePath(String filename) throws FileNotFoundException {
        URL url = (Resources.class.getClassLoader()).getResource(filename);
        if(url != null) {
            return new File(url.getFile()).getAbsolutePath();
        } else {
            throw new FileNotFoundException();
        }

    }

    /**
     * Method loads the content of a resource file (from Resources class classpath)
     * as a String
     *
     * @param filename the name of the Resource file
     * @return file content as single String
     * @throws ClassNotFoundException if Resources.class.getName() cant be found
     */

    public static String loadResource(String filename) throws Exception{
        String result;

        InputStream in = Class.forName(Resources.class.getName()).getResourceAsStream(filename);
        Scanner scanner = new Scanner(in,"ISO_8859_1");
        result = scanner.useDelimiter("\\A").next();

        return result;
    }

    /**
     * Method loads the content of a resource file (from Resources class classpath)
     * as a List of Strings
     * one String per line
     *
     * @param filename name of the resource file
     * @return list of file lines as Strings
     * @throws ClassNotFoundException if Resources.class.getName() cant be found
     * @throws IOException if I/O error occurs at br.readLine()
     */

    public static List<String> readAllLines(String filename) throws Exception {
        List<String> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(Class.forName(Resources.class.getName()).getResourceAsStream(filename), StandardCharsets.ISO_8859_1))) {
            String line;
            while((line = br.readLine()) != null) {
                list.add(line);
            }
            return list;
        }
    }
}
