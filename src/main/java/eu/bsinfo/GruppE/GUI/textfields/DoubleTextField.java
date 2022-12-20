package eu.bsinfo.GruppE.GUI.textfields;

import javax.swing.*;
import javax.swing.text.Document;

public class DoubleTextField extends JTextField {
    protected Document createDefaultModel() {
        return new DoubleTextDocument();
    }

    @Override
    public String getText(){
        return(super.getText().replace(",", "."));
    }



}
