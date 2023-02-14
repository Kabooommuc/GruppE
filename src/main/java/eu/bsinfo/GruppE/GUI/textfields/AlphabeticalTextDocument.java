package eu.bsinfo.GruppE.GUI.textfields;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class AlphabeticalTextDocument extends PlainDocument {

    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {

        str = str.replaceAll("[^a-zA-Z\\s-üÜäÄöÖ]", "");

        super.insertString(offs, str, a);
    }

}

