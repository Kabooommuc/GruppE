package eu.bsinfo.GruppE;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUI extends JFrame {

    JTextField customerIdInput = new JTextField();
    JTextField houseNumberInput = new JTextField();
    JTextField apartmentNumberInput = new JTextField();
    JTextField counterTypeInput = new JTextField();
    JTextField counterIdInput = new JTextField();
    JTextField measurementReadingDateInput = new JTextField();
    JCheckBox counterChangeInput = new JCheckBox();
    JTextField commentInput = new JTextField();
    public GUI() {
        super("Zählerabrechnung - ProgSchnellUndSicher GmbH");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });

        final Container base = getContentPane();
        base.setLayout(new GridBagLayout());

        final Container inputFields = new Container();
        inputFields.setLayout(new GridLayout(2, 8));

        JLabel customerIdLabel = new JLabel("KundenNr");
        JLabel houseNumberLabel = new JLabel("HausNr");
        JLabel apartmentNumberLabel = new JLabel("WohnungsNr");
        JLabel counterTypeLabel = new JLabel("Zählerart");
        JLabel counterIdLabel = new JLabel("ZählerID");
        JLabel measurementReadingDateLabel = new JLabel("Ablesedatum");
        JLabel counterChangeLabel = new JLabel("Zählertausch");
        JLabel commentLabel = new JLabel("Kommentar");

        inputFields.add(customerIdLabel);
        inputFields.add(customerIdInput);
        inputFields.add(houseNumberLabel);
        inputFields.add(houseNumberInput);
        inputFields.add(apartmentNumberLabel);
        inputFields.add(apartmentNumberInput);
        inputFields.add(counterChangeLabel);
        inputFields.add(counterChangeInput);

        inputFields.add(counterTypeLabel);
        inputFields.add(counterTypeInput);
        inputFields.add(counterIdLabel);
        inputFields.add(counterIdInput);
        inputFields.add(measurementReadingDateLabel);
        inputFields.add(measurementReadingDateInput);
        inputFields.add(commentLabel);
        inputFields.add(commentInput);

        base.add(inputFields);

        final Container dataScrollpane = new Container();
        dataScrollpane.setLayout(new ScrollPaneLayout());

        final Container dataGrid = new Container();
        dataGrid.setLayout(new GridBagLayout());

        final Container actionButtons = new Container();
        actionButtons.setLayout(new GridBagLayout());

        setSize(800, 100);
        setVisible(true);

    }

    private void exit() {
        System.exit(0);
    }

}
