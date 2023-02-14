package eu.bsinfo.GruppE.GUI.textfields;

import javax.swing.text.Document;

public class AlphabeticalTextField extends MyTextField {
    protected Document createDefaultModel() {
        return new AlphabeticalTextDocument();
    }

}

