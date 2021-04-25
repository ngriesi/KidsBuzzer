package utils.saveFile;

import java.lang.reflect.Method;

/**
 * contains methods to create fields from Strings and a method to get the right creator methods
 * for a specific type
 */
@SuppressWarnings("unused")
public class FieldCreatorClass {

    /**
     * used to get the method to create a field of the given type
     *
     * @param type the type of the field that gets created
     * @return the method that creates the field
     */
    static Method getCreatorMethod(Class<?> type) {
        for (Method method : FieldCreatorClass.class.getMethods()) {
            if (method.getReturnType().equals(type) && method.getDeclaringClass().equals(FieldCreatorClass.class)) {
                return method;
            }
        }

        return null;
    }

    /**
     * creates a String field form a string
     *
     * @param fieldContent content of the String field
     * @return returns the String
     */
    public static String createString(String fieldContent) {
        return fieldContent;
    }

    /**
     * creates a boolean field from a string
     *
     * @param fieldContent content of the boolean field
     * @return returns a new field of type boolean
     */
    public static boolean createBoolean(String fieldContent) {
        return Boolean.parseBoolean(fieldContent);
    }

    /**
     * creates an Integer field from a string
     *
     * @param fieldContent content of the boolean field
     * @return returns a new field of type Integer
     */
    public static int createInteger(String fieldContent) {
        return Integer.parseInt(fieldContent);
    }

    /**
     * creates an array of booleans from a string
     *
     * @param fieldContent content of the boolean array
     * @return returns a new array of type boolean
     */
    public static boolean[] createBooleanArray(String fieldContent) {
        fieldContent = fieldContent.substring(1, fieldContent.length() - 1);
        String[] values = fieldContent.split(", ");
        boolean[] result = new boolean[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = Boolean.parseBoolean(values[i]);
        }
        return result;
    }

    /**
     * creates an array of integers from a string
     *
     * @param fieldContent content of the integer array
     * @return returns a new array of type integer
     */
    public static int[] createIntArray(String fieldContent) {
        fieldContent = fieldContent.substring(1, fieldContent.length() - 1);
        String[] values = fieldContent.split(", ");
        int[] result = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = Integer.parseInt(values[i]);
        }
        return result;
    }

    /**
     * creates an array of strings from a string
     *
     * @param fieldContent content of the string array
     * @return returns a new array of type string
     */
    public static String[] createStringArray(String fieldContent) {
        fieldContent = fieldContent.substring(1, fieldContent.length() - 1);
        return fieldContent.split(", ");
    }
}
