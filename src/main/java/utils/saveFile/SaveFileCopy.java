package utils.saveFile;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * class used to copy a save file object
 */
class SaveFileCopy {

    /**
     * copy method for any child classes of SaveFile
     *
     * @param saveFile saveFile to be copied
     * @return returns a new instance of the save file with the same values
     */
    static <T extends SaveFile> T copySaveFile(T saveFile) {

        //noinspection unchecked
        T result = (T) createObject(saveFile.getClass());


        Method[] methods = new Method[0];
        if (result != null) {
            methods = result.getClass().getMethods();
        }

        copyFields(result, methods, saveFile);

        return result;
    }

    /**
     * methods copies the fields of one save file to another one
     *
     * @param result   the save file that gets copied to
     * @param methods  methods of the class of the object that gets copied
     * @param saveFile save file that gets copied from
     * @param <T>      type of the save file
     */
    private static <T extends SaveFile> void copyFields(T result, Method[] methods, T saveFile) {
        for (Method method : methods) {
            if (checkMethod(method)) {
                String setterName = "set" + method.getName().substring(method.getName().startsWith("get") ? 3 : 2);

                copyField(result, setterName, method, saveFile);
            }
        }
    }

    /**
     * copies one field from one Save file to another
     *
     * @param result     object that gets copied to
     * @param setterName name of the setter method to write the value
     * @param method     getter method to access the value of the field
     * @param saveFile   object that gets copied from
     * @param <T>        type of the save file
     */
    private static <T extends SaveFile> void copyField(T result, String setterName, Method method, T saveFile) {
        try {
            result.getClass().getMethod(setterName, method.getReturnType()).invoke(result, method.invoke(saveFile));
        } catch (InvocationTargetException e) {
            System.out.println("Method " + setterName + " or " + method.getName() + " threw an exception: " + e.getCause().getMessage());
        } catch (NoSuchMethodException e) {
            System.out.println("missing Method " + setterName + " or " + method.getName());
        } catch (IllegalAccessException e) {
            System.out.println("Cant access method " + setterName + " or " + method.getName() + " - use public access modifier for the method");
        }
    }

    /**
     * method checks if the passed method is a getter that has to be used for copying
     *
     * @param method method that gets checked
     * @return returns true if method gets used
     */
    private static boolean checkMethod(Method method) {
        if (!method.getName().equals("getClass") && !method.getName().equals("getName")) {
            return method.getName().startsWith("get") || method.getName().startsWith("is");
        }
        return false;
    }

    /**
     * creates an object of the passed type extending SaveFile
     *
     * @param type type of the object
     * @param <T>  generic type of the object
     * @return new instance of the save file type
     */
    private static <T extends SaveFile> T createObject(Class<T> type) {
        try {
            return type.newInstance();
        } catch (InstantiationException e) {
            System.out.println("Cant create instance of " + type.getName() + ". SaveFile classes must not be abstract and must have a default constructor");
            return null;
        } catch (IllegalAccessException e) {
            System.out.println("Cant access class " + type.getName() + ". Make sure the class and its constructor are public");
            return null;
        }
    }

}
