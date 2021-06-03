package utils.save;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

/**
 * Loads a save file form the SaveFiles directory
 */
class SaveFileLoader {

    /**
     * Loads the data from the file with the same name as the saveFile object
     * and writes the values to the HashMaps
     *
     * @param saveFile save file that gets loaded
     */
    static void tryLoadingSave(SaveFile saveFile) {

        List<String> lines = tryLoading(saveFile);

        for (String string : lines) {
            try {
                computeSaveFileLine(saveFile, string);
            } catch (Exception e) {
                System.out.println("failed to compute line: " + string);
            }

        }
    }

    /**
     * computes one line of the save file to put its value into one of the
     * maps of the saveFile object
     * <p>
     * Line Format:
     * {Type} {Field Name} = {Field Value}
     * <p>
     * example:
     * String text = This is a text
     * <p>
     * Possible Types: String, Integer, Boolean
     *
     * @param saveFile save file object that gets the value
     * @param string   line of the loaded file
     */
    private static void computeSaveFileLine(SaveFile saveFile, String string) {
        int valueStartPosition = string.indexOf('=') + 1;
        if(valueStartPosition > 1) {
            String signature = string.substring(0, valueStartPosition - 2);
            String dataType = signature.split(" ")[0];
            putSaveFileValue(dataType, signature.substring(dataType.length() + 1), string.substring(valueStartPosition).trim(), saveFile);
        }
    }

    /**
     * puts a value into a HashMap of the Save file
     *
     * @param type     type of the loaded value
     * @param name     name of the loaded value used as key in the HashMap
     * @param value    value that was loaded
     * @param saveFile save file object the loaded value gets put into
     */
    private static void putSaveFileValue(String type, String name, String value, SaveFile saveFile) {
        switch (type) {
            case "String":
                saveFile.putString(name, value);
                break;
            case "Integer":
                try {
                    saveFile.putInteger(name, Integer.parseInt(value));
                } catch (NumberFormatException ignored) {
                }
                break;
            case "Boolean":
                saveFile.putBoolean(name, Boolean.parseBoolean(value));

        }
    }

    /**
     * Returns the content of the loaded file in a list of strings if
     * it can load something. If it cant load it returns an empty list
     *
     * @param saveFile save file object of which the file is loaded
     * @return a list with the saved values or an empty list if the
     * file cant be loaded
     */
    private static List<String> tryLoading(SaveFile saveFile) {
        List<String> lines;
        try {
            // reads the file
            lines = Files.readAllLines(Paths.get("saveFiles/" + saveFile.getName() + ".sf"), Charset.defaultCharset());
        } catch (IOException e) {
            System.out.println("Save file " + saveFile.getName() + ".sf not found");
            return new LinkedList<>();
        }
        return lines;
    }
}
