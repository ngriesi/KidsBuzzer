package assets.standardAssets.documentFilter;

/**
 * filter that checks if the new text of the document is either an int or nothing
 * and blocks the change otherwise
 */
public class MyIntegerFilter extends MyFilter {

    /**
     * method that checks if the text is either an int or nothing
     * and blocks the change otherwise
     *
     * @param text text that gets checked
     * @return returns true if the text is a string or empty
     */
    protected boolean test(String text) {
        if (text.equals("")) {
            return true;
        }
        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
