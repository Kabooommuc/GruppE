package eu.bsinfo.GruppE;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUI extends JFrame {
    public GUI() {
        super("Zählerabrechnung - ProgSchnellUndSicher GmbH");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });

        final Container con = getContentPane();
        con.setLayout(new BorderLayout());
        final JPanel pn = new JPanel();


        setSize(530, 200);
        setVisible(true);

    }

    private void exit() {
        System.exit(0);
    }

}
