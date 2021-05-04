package utils.saveFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * class to load a save file
 */
public class SaveFileLoader {

    /**
     * Static method to load a save file from the drive
     *
     * @param name name of the save file
     * @param type type of the file (a class extending this class)
     * @param <T>  generic type of the save file, extends this class
     * @return an object of type T, if possible, its values were set from the file
     */
    public static <T extends SaveFile> T loadFile(String name, final Class<T> type) {


        T object = createObjectWithName(type, name);

        if (object == null) {
            return null;
        }

        List<String> lines;
        try {
            // reads the file
            lines = Files.readAllLines(Paths.get("saveFiles/" + name + ".sf"), Charset.defaultCharset());
        } catch (IOException e) {
            return type.cast(object);
        }

        writeLoadedStringToObject(lines, type, object);

        return type.cast(object);
    }

    /**
     * loops through the loaded lines and searches for matching fields
     * in the class of the object to be set
     *
     * @param lines  lines of the save file
     * @param type   type of the loaded save file
     * @param object object of which the field gets set
     * @param <T>    generic type fo the SaveFile
     */
    private static <T extends SaveFile> void writeLoadedStringToObject(List<String> lines, Class<T> type, T object) {
        for (String s : lines) {

            // skips empty lines
            if (s.isEmpty()) continue;

            String fieldName = s.split("=")[0].trim();
            String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            String fieldContent = s.substring(s.indexOf('=') + 1);
            catchMethodInvocationExceptions(type, methodName, fieldName, object, fieldContent);

        }
    }

    /**
     * catches the exceptions of the method invocation
     *
     * @param type         type of the loaded save file
     * @param methodName   name of the setter method
     * @param fieldName    name of the field that gets set
     * @param object       object of which the field gets set
     * @param fieldContent content of the field, loaded form the file
     * @param <S>          generic type of the field
     * @param <T>          generic type fo the SaveFile
     */
    private static <S, T extends SaveFile> void catchMethodInvocationExceptions(Class<T> type, String methodName, String fieldName, T object, String fieldContent) {
        try {
            invokeMethod(type, methodName, type.getDeclaredField(fieldName).getType(), object, fieldContent);
        } catch (NoSuchFieldException e) {
            System.out.println("Mistake in the name of the field " + fieldName + " in the save file, ignored, sets value to default");
        } catch (InvocationTargetException e) {
            System.out.println("Method " + methodName + " threw an exception: " + e.getCause().getMessage());
        } catch (NoSuchMethodException e) {
            System.out.println("missing setter Method for field " + fieldName);
        } catch (IllegalAccessException e) {
            System.out.println("Cant access method " + methodName + " - use public access modifier for the method");
        }
    }

    /**
     * creates a new object to write the loaded values to
     *
     * @param type type of the file that gets loaded
     * @param name name of the save file
     * @param <T>  generic type of the save file
     * @return returns a new instance of the SaveFile object
     */
    private static <T extends SaveFile> T createObjectWithName(Class<T> type, String name) {

        try {
            // creates an instance of the save file object to return
            T object = type.newInstance();
            // sets the name attribute of the save file
            type.cast(object).setName(new File(name).getName());

            return object;
        } catch (InstantiationException e) {
            System.out.println("Cant create instance of " + type.getName() + ". SaveFile classes must not be abstract and must have a default constructor");
            return null;
        } catch (IllegalAccessException e) {
            System.out.println("Cant access class " + type.getName() + ". Make sure the class and its constructor are public");
            return null;
        }
    }

    /**
     * invokes the method to write the value
     *
     * @param objectType   type of the SaveFile
     * @param methodName   name of the setter method
     * @param fieldType    type of the field that gets set
     * @param object       object of which the field gets set
     * @param fieldContent content of the field, loaded form the file
     * @param <S>          generic type of the field
     * @param <T>          generic type fo the SaveFile
     * @throws NoSuchMethodException     thrown if the method does not exist
     * @throws InvocationTargetException thrown if the methods invoked throw an exception
     * @throws IllegalAccessException    thrown if the methods cant be accessed
     */
    private static <S, T extends SaveFile> void invokeMethod(Class<T> objectType, String methodName, Class<S> fieldType, T object, String fieldContent) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //noinspection ConstantConditions
        objectType.getMethod(methodName, fieldType).invoke(object, FieldCreatorClass.getCreatorMethod(fieldType).invoke(null, fieldContent));
    }


}
