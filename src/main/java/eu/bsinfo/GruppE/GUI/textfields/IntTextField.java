package eu.bsinfo.GruppE.GUI.textfields;

import javax.swing.*;
import javax.swing.text.Document;

public class IntTextField extends JTextField {
    protected Document createDefaultModel() {
        System.out.println(this.getText());
        return new IntTextDocument();
    }

}
