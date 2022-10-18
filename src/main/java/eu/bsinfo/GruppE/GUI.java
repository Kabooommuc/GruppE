package eu.bsinfo.GruppE;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.InputMismatchException;

public class GUI extends JFrame {

    JTextField customerIdInput = new JTextField();
    JTextField houseNumberInput = new JTextField();
    JTextField apartmentNumberInput = new JTextField();
    JTextField counterTypeInput = new JTextField();
    JTextField counterIdInput = new JTextField();
    JTextField measurementReadingDateInput = new JTextField();
    // TODO: measurementReadingDate is no String; it should be a date picker
    JCheckBox counterChangeInput = new JCheckBox();
    JTextField commentInput = new JTextField();
    JTextField powerCurrentInput = new JTextField();
    JTextField householdElecticityInput = new JTextField();

    JLabel errorMessageLabel = new JLabel("");


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
        JLabel houseNumberLabel = new JLabel("HausNr ");
        JLabel apartmentNumberLabel = new JLabel("WohnungsNr ");
        JLabel powerCurrentLabel = new JLabel("Kraftstrom ");
        JLabel householdElectricityLabel = new JLabel("Haushaltsstrom ");

        JLabel counterTypeLabel = new JLabel("Zählerart ");
        JLabel counterIdLabel = new JLabel("ZählerID ");
        JLabel measurementReadingDateLabel = new JLabel("Ablesedatum ");
        JLabel commentLabel = new JLabel("Kommentar ");
        JLabel counterChangeLabel = new JLabel("Zählertausch ");

        JLabel[] list = {
                houseNumberLabel, apartmentNumberLabel,
                powerCurrentLabel, householdElectricityLabel, counterTypeLabel,
                counterIdLabel, commentLabel, counterChangeLabel, measurementReadingDateLabel
        };
        for ( JLabel j : list ) {
            j.setHorizontalAlignment(4);
            j.setBorder(new EmptyBorder(0,15,0,10));
        }

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

    public void addData() {
        MeasurementData m = new MeasurementData();
        try {
            clearErrorMessage();
            DataHandler.addData(m);
        }
        catch (InputMismatchException e) {
            errorMessageLabel.setText("");
        }
    }

    public void clearErrorMessage() {
        errorMessageLabel.setText("");
    }

    private void exit() {
        System.exit(0);
    }

}
