package eu.bsinfo.GruppE.GUI.textfields;

import javax.swing.text.Document;

public class IntTextField extends MyTextField {

    protected Document createDefaultModel() {
        return new IntTextDocument();
    }

}
