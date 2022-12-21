package eu.bsinfo.GruppE.GUI.textfields;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class IntTextDocument extends PlainDocument {

    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {

        try {
            Integer.parseInt(getText(0, getLength()) + str);
        } catch (NumberFormatException n) {
            return;
        }

        super.insertString(offs, str, a);
    }



}