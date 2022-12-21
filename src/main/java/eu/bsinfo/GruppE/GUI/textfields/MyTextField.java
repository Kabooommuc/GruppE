package eu.bsinfo.GruppE.GUI.textfields;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MyTextField extends JTextField {
    public MyTextField() {
        super();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER) {
                    transferFocus();
                }
            }
        });
    }
}
