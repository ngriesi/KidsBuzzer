package utils.save;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * class used to save a file to the file system containing the values of the HashMaps
 * of the saveFile object
 */
public class SaveFileSaver {

    /**
     * saves the contents of the HashMaps of the save file object to a file
     * with the name of the field name of the saveFile object
     *
     * @param saveFile save file object which maps get saved
     */
    public static void saveFile(SaveFile saveFile) {
        String fileContent = createStringsString(saveFile);
        fileContent = fileContent + createIntegersString(saveFile);
        fileContent = fileContent + createBooleansString(saveFile);
        saveString(saveFile.getName(), fileContent);
    }

    /**
     * Saves the <code>String fileContent</code> to a file called <code>name.sf</code>
     * in the directory SaveFiles. If the directory does not exist it gets created.
     *
     * @param name        name of the save file
     * @param fileContent content that gets saved
     */
    private static void saveString(String name, String fileContent) {
        createFolder();
        try {
            writeToFile(name, fileContent);
        } catch (IOException e) {
            System.out.println("Cant write to save file " + name + ".sf");
        }
    }

    /**
     * writes a string to a file
     *
     * @param name    name of the save file
     * @param content string that gets written to the file
     * @throws IOException Exception gets thrown when to PrintWriter cant write to the file
     */
    private static void writeToFile(String name, String content) throws IOException {
        try (PrintWriter out = new PrintWriter("saveFiles/" + name + ".sf")) {

            File file = new File("saveFiles/" + name + ".sf");
            //noinspection ResultOfMethodCallIgnored
            file.createNewFile();
            out.println(content);
        }
    }

    /**
     * creates the save file folder if it does not exist yet
     */
    private static void createFolder() {
        File dir = new File("saveFiles");
        if (!dir.exists()) {
            //noinspection ResultOfMethodCallIgnored
            dir.mkdir();
        }
    }

    /**
     * Method turns the content of the <code>booleans HashMap</code> of the
     * <code>saveFile</code> into a formatted string for the save file.
     * The lines of the <code>String</code> have the following form:
     * Boolean {name/key} = {value}
     *
     * @param saveFile save file where the <code>HashMap</code> is retrieved from
     * @return a <code>String</code> containing all the booleans that should be saved
     * from the passed <code>SaveFile</code> as lines
     */
    private static String createBooleansString(SaveFile saveFile) {
        StringBuilder sb = new StringBuilder();
        for (String key : saveFile.getBooleans().keySet()) {
            sb.append("Boolean ").append(key).append(" = ").append(saveFile.getBoolean(key)).append("\n");
        }
        return sb.toString();
    }

    /**
     * Method turns the content of the <code>integers HashMap</code> of the
     * <code>saveFile</code> into a formatted string for the save file.
     * The lines of the <code>String</code> have the following form:
     * Integer {name/key} = {value}
     *
     * @param saveFile save file where the <code>HashMap</code> is retrieved from
     * @return a <code>String</code> containing all the integers that should be saved
     * from the passed <code>SaveFile</code> as lines
     */
    private static String createIntegersString(SaveFile saveFile) {
        StringBuilder sb = new StringBuilder();
        for (String key : saveFile.getIntegers().keySet()) {
            sb.append("Integer ").append(key).append(" = ").append(saveFile.getInteger(key)).append("\n");
        }
        return sb.toString();
    }

    /**
     * Method turns the content of the <code>strings HashMap</code> of the
     * <code>saveFile</code> into a formatted string for the save file.
     * The lines of the <code>String</code> have the following form:
     * String {name/key} = {value}
     *
     * @param saveFile save file where the <code>HashMap</code> is retrieved from
     * @return a <code>String</code> containing all the strings that should be saved
     * from the passed <code>SaveFile</code> as lines
     */
    private static String createStringsString(SaveFile saveFile) {
        StringBuilder sb = new StringBuilder();
        for (String key : saveFile.getStrings().keySet()) {
            sb.append("String ").append(key).append(" = ").append(saveFile.getString(key)).append("\n");
        }
        return sb.toString();
    }
}
