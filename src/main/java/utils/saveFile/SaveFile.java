package utils.saveFile;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Class contains methods to save the attributes (String, int, boolean) to a file. This requires the class that gets saved to extends this class
 * and to have public getter and setter methods fo all its attributes. To set default values if fields or the whole file cant be found
 * the fields have to be given a value e.g. in the constructor
 */
public abstract class SaveFile {

    /**
     * stores the name of the save file, it is set when the file gets loaded and than reused when it gets saved
     */
    private String name;

    /**
     * creates a save file with a name
     *
     * @param name name of the save file
     */
    public SaveFile(String name) {
        this.name = name;
    }

    /**
     * sets the name of the save file
     *
     * @param name new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return returns the name of the save file
     */
    public String getName() {
        return name;
    }

    /**
     * saves this object to a file with the name that is stored in this class and in the SaveFile directory
     * if the file or the directory do not exist, they are created
     */
    public void saveFile() {
        new Thread(() -> {

            StringBuilder fileContent = createSaveFileString(SaveFile.this.getClass().getMethods());
            handleFileWriting(fileContent.toString());


        }).start();

    }

    /**
     * creates the string that gets saved
     *
     * @param methods methods of the class that extends SaveFile
     * @return returns the full string content of the file
     */
    private StringBuilder createSaveFileString(Method[] methods) {
        StringBuilder fileContent = new StringBuilder();
        for (Method method : methods) {
            if (checkIfMethodIsSpecificGetter(method)) {
                addFileLine(method, fileContent);
            }
        }
        return fileContent;
    }

    /**
     * checks if a given method is a getter in the class of the object that gets saved
     *
     * @param method method that gets checked
     * @return result of the check, true if it is a getter
     */
    private boolean checkIfMethodIsSpecificGetter(Method method) {
        return method.getDeclaringClass().equals(SaveFile.this.getClass()) && (method.getName().startsWith("get") || method.getName().startsWith("is"));
    }

    /**
     * creates a line of the save file with a getter method
     *
     * @param method      method, used to access the value that gets saved
     * @param fileContent string builder the line that was created gets added to
     */
    private void addFileLine(Method method, StringBuilder fileContent) {
        try {
            fileContent.append(SaveFileLineCreator.createLine(method, SaveFile.this)).append("\n");
        } catch (InvocationTargetException e) {
            System.out.println("Exception thrown in called method: " + method.getName());
        } catch (IllegalAccessException e) {
            System.out.println("Cant access getter method " + method.getName() + ". Make sure the method is public");
        }
    }

    /**
     * saves the content to a file
     *
     * @param content content of the save file
     */
    private void handleFileWriting(String content) {
        createFolder();
        try {
            writeToFile(content);
        } catch (IOException e) {
            System.out.println("Cant write to save file " + name + ".sf");
        }
    }

    /**
     * writes a string to a file
     *
     * @param content string that gets written to the file
     * @throws IOException Exception gets thrown when to PrintWriter cant write to the file
     */
    private void writeToFile(String content) throws IOException {
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
    private void createFolder() {
        File dir = new File("saveFiles");
        if (!dir.exists()) {
            //noinspection ResultOfMethodCallIgnored
            dir.mkdir();
        }
    }
}
