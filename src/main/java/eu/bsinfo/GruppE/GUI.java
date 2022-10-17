package eu.bsinfo.GruppE;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
    JTextField powerCurrentInput = new JTextField();
    JTextField householdElecticityInput = new JTextField();
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
        inputFields.setLayout(new GridLayout(2, 10));


        JLabel customerIdLabel = new JLabel("KundenNr ");
        customerIdLabel.setHorizontalAlignment(JLabel.RIGHT);
        customerIdLabel.setBorder(new EmptyBorder(0,15,0,10));
        JLabel houseNumberLabel = new JLabel("HausNr ");
        houseNumberLabel.setHorizontalAlignment(4); // JLabel.Right = 4
        houseNumberLabel.setBorder(new EmptyBorder(0,15,0,10));
        JLabel apartmentNumberLabel = new JLabel("WohnungsNr ");
        apartmentNumberLabel.setHorizontalAlignment(4);
        apartmentNumberLabel.setBorder(new EmptyBorder(0,15,0,10));
        JLabel counterTypeLabel = new JLabel("Zählerart ");
        counterTypeLabel.setHorizontalAlignment(4);
        counterTypeLabel.setBorder(new EmptyBorder(0,15,0,10));
        JLabel counterIdLabel = new JLabel("ZählerID ");
        counterIdLabel.setHorizontalAlignment(4);
        counterIdLabel.setBorder(new EmptyBorder(0,15,0,10));
        JLabel measurementReadingDateLabel = new JLabel("Ablesedatum ");
        measurementReadingDateLabel.setHorizontalAlignment(4);
        measurementReadingDateLabel.setBorder(new EmptyBorder(0, 15,0,10));
        JLabel counterChangeLabel = new JLabel("Zählertausch ");
        counterChangeLabel.setHorizontalAlignment(4);
        counterChangeLabel.setBorder(new EmptyBorder(0, 15,0, 10));
        JLabel commentLabel = new JLabel("Kommentar ");
        commentLabel.setHorizontalAlignment(4);
        commentLabel.setBorder(new EmptyBorder(0, 15, 0, 10));
        JLabel powerCurrentLabel = new JLabel("Kraftstrom ");
        powerCurrentLabel.setHorizontalAlignment(4);
        powerCurrentLabel.setBorder(new EmptyBorder(0, 15, 0, 10));
        JLabel householdElectricityLabel = new JLabel("Haushaltsstrom ");
        householdElectricityLabel.setHorizontalAlignment(4);
        householdElectricityLabel.setBorder(new EmptyBorder(0,15,0,10));

        inputFields.add(customerIdLabel);
        inputFields.add(customerIdInput);
        inputFields.add(houseNumberLabel);
        inputFields.add(houseNumberInput);
        inputFields.add(apartmentNumberLabel);
        inputFields.add(apartmentNumberInput);
        inputFields.add(powerCurrentLabel);
        inputFields.add(powerCurrentInput);
        inputFields.add(householdElectricityLabel);
        inputFields.add(householdElecticityInput);

        inputFields.add(counterTypeLabel);
        inputFields.add(counterTypeInput);
        inputFields.add(counterIdLabel);
        inputFields.add(counterIdInput);
        inputFields.add(measurementReadingDateLabel);
        inputFields.add(measurementReadingDateInput);
        inputFields.add(commentLabel);
        inputFields.add(commentInput);
        inputFields.add(counterChangeLabel);
        inputFields.add(counterChangeInput);

        base.add(inputFields);

        final Container dataScrollpane = new Container();
        dataScrollpane.setLayout(new ScrollPaneLayout());

        final Container dataGrid = new Container();
        dataGrid.setLayout(new GridBagLayout());

        final Container actionButtons = new Container();
        actionButtons.setLayout(new GridBagLayout());

        setSize(1450, 100);
        setVisible(true);


    }

    private void exit() {
        System.exit(0);
    }

}
