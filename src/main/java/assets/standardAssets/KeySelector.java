package assets.standardAssets;

import assets.combobox.MyComboBox;
import savedataHandler.languages.Text;

/**
 * Special combo box used to select a key
 */
public class KeySelector extends MyComboBox<String> {

    /**
     * creates a custom combo box from an array of items
     */
    public KeySelector() {
        super(new String[0]);
        for (String s : createKeyStringArray()) {
            super.addItem(s);
        }
    }

    /**
     * creates a string array with all the possible keys
     *
     * @return returns the built array
     */
    private String[] createKeyStringArray() {
        return new String[]{
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                Text.RIGHT_DIRECTION, Text.LEFT_DIRECTION, Text.UP_DIRECTION, Text.DOWN_DIRECTION,
                Text.SPACE, Text.RETURN, Text.BACKSPACE, Text.TABULATOR
        };
    }

    /**
     * @return returns the key of this selector
     */
    public int getKey() {
        return fromIndexToKey(getSelectedIndex());
    }

    /**
     * converts a combo box item index into a key code
     *
     * @param index index of the combo box
     * @return the key code for the selected index
     */
    @SuppressWarnings("DuplicateBranchesInSwitch")
    private int fromIndexToKey(int index) {
        switch (index) {
            case 0:
                return 65;
            case 1:
                return 66;
            case 2:
                return 67;
            case 3:
                return 68;
            case 4:
                return 69;
            case 5:
                return 70;
            case 6:
                return 71;
            case 7:
                return 72;
            case 8:
                return 73;
            case 9:
                return 74;
            case 10:
                return 75;
            case 11:
                return 76;
            case 12:
                return 77;
            case 13:
                return 78;
            case 14:
                return 79;
            case 15:
                return 80;
            case 16:
                return 81;
            case 17:
                return 82;
            case 18:
                return 83;
            case 19:
                return 84;
            case 20:
                return 85;
            case 21:
                return 86;
            case 22:
                return 87;
            case 23:
                return 88;
            case 24:
                return 89;
            case 25:
                return 90;

            case 26:
                return 48;
            case 27:
                return 49;
            case 28:
                return 50;
            case 29:
                return 51;
            case 30:
                return 52;
            case 31:
                return 53;
            case 32:
                return 54;
            case 33:
                return 55;
            case 34:
                return 56;
            case 35:
                return 57;

            case 36:
                return 227;
            case 37:
                return 226;
            case 38:
                return 224;
            case 39:
                return 225;
            case 40:
                return 32;
            case 41:
                return 10;
            case 42:
                return 8;
            case 43:
                return 9;
            default:
                return 65;
        }
    }

    /**
     * sets the selection of this key selector with a key code
     *
     * @param key key code of the key that is selected
     */
    public void setKey(int key) {
        setSelectedIndex(fromKeyToIndex(key));
    }

    /**
     * converts a key code into a combo box index
     *
     * @param key key code
     * @return respective combo box index
     */
    @SuppressWarnings("DuplicateBranchesInSwitch")
    private int fromKeyToIndex(int key) {
        switch (key) {
            case 65:
                return 0;
            case 66:
                return 1;
            case 67:
                return 2;
            case 68:
                return 3;
            case 69:
                return 4;
            case 70:
                return 5;
            case 71:
                return 6;
            case 72:
                return 7;
            case 73:
                return 8;
            case 74:
                return 9;
            case 75:
                return 10;
            case 76:
                return 11;
            case 77:
                return 12;
            case 78:
                return 13;
            case 79:
                return 14;
            case 80:
                return 15;
            case 81:
                return 16;
            case 82:
                return 17;
            case 83:
                return 18;
            case 84:
                return 19;
            case 85:
                return 20;
            case 86:
                return 21;
            case 87:
                return 22;
            case 88:
                return 23;
            case 89:
                return 24;
            case 90:
                return 25;

            case 48:
                return 26;
            case 49:
                return 27;
            case 50:
                return 28;
            case 51:
                return 29;
            case 52:
                return 30;
            case 53:
                return 31;
            case 54:
                return 32;
            case 55:
                return 33;
            case 56:
                return 34;
            case 57:
                return 35;

            case 227:
                return 36;
            case 226:
                return 37;
            case 224:
                return 38;
            case 225:
                return 39;
            case 32:
                return 40;
            case 10:
                return 41;
            case 8:
                return 42;
            case 9:
                return 43;
            default:
                return 0;
        }
    }
}
