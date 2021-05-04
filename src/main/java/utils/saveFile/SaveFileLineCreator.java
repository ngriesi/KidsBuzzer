package utils.saveFile;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * class contains methods to create lines of the save file from methods of the SaveFile object
 */
class SaveFileLineCreator {

    /**
     * creates one line of the save file
     *
     * @param method to access the value that gets saved
     * @param object object that gets saved
     * @param <T>    type of the SaveFile object that gets saved
     * @return returns a line of the saved file
     * @throws InvocationTargetException thrown if the getter method throws an Exception
     * @throws IllegalAccessException    thrown if the getter method cant be accessed
     */
    static <T extends SaveFile> String createLine(Method method, T object) throws InvocationTargetException, IllegalAccessException {
        String getterName = method.getName();
        int i = getterName.startsWith("is") ? 0 : 1;
        String fieldName = getterName.substring(2 + i, 3 + i).toLowerCase() + getterName.substring(3 + i);

        String temp = createValueString(method, object);

        return fieldName + "=" + temp;
    }

    /**
     * creates the string containing the value of the field
     *
     * @param method to access the value that gets saved
     * @param object object that gets saved
     * @param <T>    type of the SaveFile object that gets saved
     * @return returns a string containing the value of the field
     * @throws InvocationTargetException thrown if the getter method throws an Exception
     * @throws IllegalAccessException    thrown if the getter method cant be accessed
     */
    private static <T extends SaveFile> String createValueString(Method method, T object) throws InvocationTargetException, IllegalAccessException {
        if (method.getReturnType().isArray()) {
            return createValueStringFromArray(method, object);
        } else {
            return method.invoke(object).toString();
        }
    }

    /**
     * creates the string containing the value of the field for arrays
     *
     * @param method to access the value that gets saved
     * @param object object that gets saved
     * @param <T>    type of the SaveFile object that gets saved
     * @return returns a string containing the value of the array-field
     * @throws InvocationTargetException thrown if the getter method throws an Exception
     * @throws IllegalAccessException    thrown if the getter method cant be accessed
     */
    private static <T extends SaveFile> String createValueStringFromArray(Method method, T object) throws InvocationTargetException, IllegalAccessException {

        switch (method.getReturnType().getName()) {
            case "[Z":
                return Arrays.toString((boolean[]) method.invoke(object));

            case "[I":
                return Arrays.toString((int[]) method.invoke(object));

            default:
                return Arrays.toString((Object[]) method.invoke(object));
        }
    }
}
