package assets.standardAssets.documentFilter;

public class MySizeFilter extends MyFilter {

    private int maxLength;

    public MySizeFilter(int maxLength) {
        this.maxLength = maxLength;
    }

    boolean test(String text) {
        return text.length() <= maxLength;
    }
}
