package assets.standardAssets.documentFilter;

public class MyIntegerFilter extends MyFilter {

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
