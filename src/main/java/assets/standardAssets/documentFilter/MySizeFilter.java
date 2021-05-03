package assets.standardAssets.documentFilter;

/**
 * filter that prevents the content of a document form
 * exceeding a maximum number of characters
 */
public class MySizeFilter extends MyFilter {

    /**
     * maximum number of characters allowed in the document
     */
    private int maxLength;

    /**
     * constructor creates a new filter and sets the maximum number of characters
     *
     * @param maxLength maximum allowed length of the content of the document
     */
    public MySizeFilter(int maxLength) {
        this.maxLength = maxLength;
    }

    /**
     * method checks if the text exceeds the maximum number of characters
     *
     * @param text text that gets checked
     * @return returns true of the length of the text is smaller or equal
     * to maxLength
     */
    boolean test(String text) {
        return text.length() <= maxLength;
    }
}
