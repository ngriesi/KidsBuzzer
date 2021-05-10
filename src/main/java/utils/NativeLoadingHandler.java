package utils;

import java.io.*;

/**
 * The class handles the loading of the native files for LWJGL
 */
public class NativeLoadingHandler {

    /**
     * enum to identify the os
     */
    private enum OS {
        WIN64, WIN86
    }

    /**
     * method called to load the natives to a folder and set this folder
     * as the source of the lwjgl library path
     *
     * @throws Exception exceptions thrown when the action of copying the
     *                   natives and setting them as library fails. See the separate methods for
     *                   more detailed descriptions
     */
    public static void loadNatives() throws Exception {
        OS os = identifyOS();

        String mainPath = new File(NativeLoadingHandler.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile().getPath();

        if (checkFolder(os, mainPath)) {
            createFiles(os, mainPath);
        } else {
            if (createFolders(os, mainPath)) {
                createFiles(os, mainPath);
            } else {
                throw new Exception("Failed to create folders");
            }

        }

        setSystemProperty(os, mainPath);
    }

    /**
     * creates the files in the library directory
     *
     * @param os       current operating system
     * @param mainPath path of the executing jar file
     * @throws IOException Exception, thrown if a resource file cant be red
     */
    private static void createFiles(OS os, String mainPath) throws IOException {
        File osDir = new File(mainPath + "/lib/" + getOsDirName(os));

        if (os == OS.WIN64) {
            checkAndCreateResource(osDir, "lwjgl-glfw-natives-windows.jar", os);
            checkAndCreateResource(osDir, "lwjgl-jemalloc-natives-windows.jar", os);
            checkAndCreateResource(osDir, "lwjgl-natives-windows.jar", os);
            checkAndCreateResource(osDir, "lwjgl-opengl-natives-windows.jar", os);
            checkAndCreateResource(osDir, "lwjgl-stb-natives-windows.jar", os);
        } else {
            checkAndCreateResource(osDir, "lwjgl-glfw-natives-windows-x86.jar", os);
            checkAndCreateResource(osDir, "lwjgl-jemalloc-natives-windows-x86.jar", os);
            checkAndCreateResource(osDir, "lwjgl-natives-windows-x86.jar", os);
            checkAndCreateResource(osDir, "lwjgl-opengl-natives-windows-x86.jar", os);
            checkAndCreateResource(osDir, "lwjgl-stb-natives-windows-x86.jar", os);
        }
    }

    /**
     * checks if the file that is about to be created exists and calls the creation
     * method if it does not find the file.
     *
     * @param osDir        os specific directory of the native files
     * @param resourceFile name of the resource file that gets copied to the natives directory
     * @param os           operating system
     * @throws IOException Exception, thrown if the resource file cant be red
     */
    private static void checkAndCreateResource(File osDir, String resourceFile, OS os) throws IOException {
        if (!new File(osDir.getPath() + "/" + resourceFile).exists())
            copyResourceFile(resourceFile, osDir.getPath(), os);
    }

    /**
     * checks if the file that is about to be created exists and calls the creation
     * method if it does not find the file.
     *
     * @param path         path where the resource files should go
     * @param resourceName name of the resource file that gets copied to the natives directory
     * @param os           operating system
     * @throws IOException Exception, thrown if the resource file cant be red
     */
    private static void copyResourceFile(String resourceName, String path, OS os) throws IOException {

        InputStream initialStream = NativeLoadingHandler.class.getClassLoader().getResourceAsStream("lib/" + getOsDirName(os) + "/" + resourceName);
        if (initialStream != null) {
            byte[] buffer = new byte[initialStream.available()];
            //noinspection ResultOfMethodCallIgnored
            initialStream.read(buffer);

            File targetFile = new File(path + "/" + resourceName);
            OutputStream outStream = new FileOutputStream(targetFile);
            outStream.write(buffer);
        } else {
            throw new FileNotFoundException("Could not create input stream for resource file");
        }
    }

    /**
     * creates the folders for the native files
     *
     * @param os       operating system
     * @param mainPath path of the jar
     * @return returns true if the folder creation succeeded
     */
    private static boolean createFolders(OS os, String mainPath) {
        boolean result = true;
        File libDir = new File(mainPath + "/lib");
        if (!libDir.exists()) {
            result = libDir.mkdir();
        }
        File osDir = new File(mainPath + "/lib/" + getOsDirName(os));
        result = result && osDir.mkdir();
        return result;
    }

    /**
     * sets the systems property for the lwjgl library path
     *
     * @param os       operating system
     * @param mainPath path of the jar
     */
    private static void setSystemProperty(OS os, String mainPath) {
        String path = mainPath + "\\lib\\" + getOsDirName(os);
        System.setProperty("org.lwjgl.librarypath", path);
    }

    /**
     * Method checks if the folders for the natives exist
     *
     * @param os       operating system
     * @param mainPath path of the jar
     * @return returns true if the folders exist
     */
    private static boolean checkFolder(OS os, String mainPath) {
        File libDir = new File(mainPath + "/lib");
        if (libDir.exists() && libDir.isDirectory()) {
            File osDir = new File(mainPath + "/lib/" + getOsDirName(os));
            return osDir.exists() && osDir.isDirectory();
        } else {
            return false;
        }
    }

    /**
     * returns the name of the os specific folder in the natives resource directory
     *
     * @param os operating system
     * @return the name of the folder in /lib in the resources of this application
     */
    private static String getOsDirName(OS os) {
        switch (os) {
            case WIN64:
                return "win-x64";
            case WIN86:
                return "win-x86";
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * Method identifies the operating system and returns the respective enum
     *
     * @return the enum referring to this operating system
     */
    private static OS identifyOS() {

        String osArch = System.getProperty("os.arch");
        String osName = System.getProperty("os.name").toLowerCase();

        if (osName.startsWith("win")) {
            if (osArch.equalsIgnoreCase("x86")) {
                return OS.WIN86;
            } else if (osArch.equalsIgnoreCase("amd64")) {
                return OS.WIN64;
            }
        }

        return OS.WIN64;
    }
}
