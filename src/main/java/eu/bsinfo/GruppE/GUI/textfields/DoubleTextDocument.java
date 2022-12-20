package eu.bsinfo.GruppE.GUI.textfields;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class DoubleTextDocument extends PlainDocument {

    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {

        str = str.replace(".", ",");
        str = str.replace("d", "");
        str = str.replace("f", "");
        str = str.trim();

        try {
            Double.parseDouble((getText(0, getLength()) + str).replace(",","."));
        } catch (NumberFormatException n) {
            return;
        }


        super.insertString(offs, str, a);


    }




}
