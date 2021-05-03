package assets.standardAssets.documentFilter;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

/**
 * document filter that checks every change that is made to a document (TextField) if the
 * change is allowed
 * <p>
 * By extending this class and overwriting the abstract test method different conditions can be checked
 * before updating the document
 */
public abstract class MyFilter extends DocumentFilter {

    /**
     * Invoked prior to insertion of text into the
     * specified Document. Subclasses that want to conditionally allow
     * insertion should override this and only call supers implementation as
     * necessary, or call directly into the FilterBypass.
     * <p>
     * checks the inserted text with the text method
     *
     * @param fb     FilterBypass that can be used to mutate Document
     * @param offset the offset into the document to insert the content &gt;= 0.
     *               All positions that track change at or after the given location
     *               will move.
     * @param string the string to insert
     * @param attr   the attributes to associate with the inserted
     *               content.  This may be null if there are no attributes.
     * @throws BadLocationException the given insert position is not a
     *                              valid position within the document
     */
    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {

        Document doc = fb.getDocument();
        StringBuilder sb = new StringBuilder();
        sb.append(doc.getText(0, doc.getLength()));
        sb.insert(offset, string);

        if (test(sb.toString())) {
            super.insertString(fb, offset, string, attr);
        }
    }

    abstract boolean test(String text);

    /**
     * Invoked prior to replacing a region of text in the
     * specified Document. Subclasses that want to conditionally allow
     * replace should override this and only call supers implementation as
     * necessary, or call directly into the FilterBypass.
     * <p>
     * checks the new text with the test method
     *
     * @param fb     FilterBypass that can be used to mutate Document
     * @param offset Location in Document
     * @param length Length of text to delete
     * @param text   Text to insert, null indicates no text to insert
     * @param attrs  AttributeSet indicating attributes of inserted text,
     *               null is legal.
     * @throws BadLocationException the given insert position is not a
     *                              valid position within the document
     */
    @Override
    public void replace(FilterBypass fb, int offset, int length, String text,
                        AttributeSet attrs) throws BadLocationException {

        Document doc = fb.getDocument();
        StringBuilder sb = new StringBuilder();
        sb.append(doc.getText(0, doc.getLength()));
        sb.replace(offset, offset + length, text);

        if (test(sb.toString())) {
            super.replace(fb, offset, length, text, attrs);
        }

    }

    /**
     * Invoked prior to removal of the specified region in the
     * specified Document. Subclasses that want to conditionally allow
     * removal should override this and only call supers implementation as
     * necessary, or call directly into the <code>FilterBypass</code> as
     * necessary.
     * <p>
     * checks if the remove action is allowed with the test method
     *
     * @param fb     FilterBypass that can be used to mutate Document
     * @param offset the offset from the beginning &gt;= 0
     * @param length the number of characters to remove &gt;= 0
     * @throws BadLocationException some portion of the removal range
     *                              was not a valid part of the document.  The location in the exception
     *                              is the first bad position encountered.
     */
    @Override
    public void remove(FilterBypass fb, int offset, int length)
            throws BadLocationException {
        Document doc = fb.getDocument();
        StringBuilder sb = new StringBuilder();
        sb.append(doc.getText(0, doc.getLength()));
        sb.delete(offset, offset + length);

        if (test(sb.toString())) {
            super.remove(fb, offset, length);
        }

    }
}
