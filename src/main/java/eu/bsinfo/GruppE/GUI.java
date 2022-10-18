package eu.bsinfo.GruppE;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUI extends JFrame {

    private final String ERROR_INVALID_INPUT = "Error: Input data is not valid";

    private final JTextField customerIdInput = new JTextField();
    private final JTextField houseNumberInput = new JTextField();
    private final JTextField apartmentNumberInput = new JTextField();
    private final JTextField counterTypeInput = new JTextField();
    private final JTextField counterIdInput = new JTextField();
    private final JTextField measurementReadingDateTimeInput = new JTextField();
     // TODO: measurementReadingDate is no String input; it should be a date picker
    private final JCheckBox counterChangeInput = new JCheckBox();
    private final JTextField commentInput = new JTextField();
    private final JTextField powerCurrentInput = new JTextField();

    private final JTextField householdCurrentInput = new JTextField();

    private final JTextField[] inputList = {
            customerIdInput,
            houseNumberInput,
            apartmentNumberInput,
            counterTypeInput,
            counterIdInput,
            measurementReadingDateTimeInput,

            commentInput,
            powerCurrentInput,
            householdCurrentInput
    };

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
        JLabel householdCurrentLabel = new JLabel("Haushaltsstrom ");
        JLabel counterTypeLabel = new JLabel("Zählerart ");
        JLabel counterIdLabel = new JLabel("ZählerID ");
        JLabel measurementReadingDateLabel = new JLabel("Ablesedatum ");
        JLabel commentLabel = new JLabel("Kommentar ");
        JLabel counterChangeLabel = new JLabel("Zählertausch ");

        // this array exists for the loop, which sets the padding and the right alignment
        JLabel[] list = {
                customerIdLabel, houseNumberLabel, apartmentNumberLabel,
                powerCurrentLabel, householdCurrentLabel, counterTypeLabel,
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
        inputFields.add(householdCurrentLabel);
        JTextField householdCurrentInput = new JTextField();
        inputFields.add(householdCurrentInput);

        inputFields.add(counterTypeLabel);
        inputFields.add(counterTypeInput);
        inputFields.add(counterIdLabel);
        inputFields.add(counterIdInput);
        inputFields.add(measurementReadingDateLabel);
        inputFields.add(measurementReadingDateTimeInput);
        inputFields.add(commentLabel);
        inputFields.add(commentInput);
        inputFields.add(counterChangeLabel);
        inputFields.add(counterChangeInput);

        base.add(inputFields);

        final Container dataScrollpane = new Container();
        dataScrollpane.setLayout(new ScrollPaneLayout());

        final Container dataGrid = new Container();
        dataGrid.setLayout(new GridBagLayout());

        // error messages should be written into this container
        final Container errorLog = new Container();
        JLabel logging = new JLabel();

        final Container actionButtons = new Container();
        actionButtons.setLayout(new GridBagLayout());

        setSize(1450, 100);
        setVisible(true);
    }

    public void addData() {
        MeasurementData m;
        try {
            m = new MeasurementData(
                    Integer.parseInt(customerIdInput.getText()),
                    houseNumberInput.getText(),
                    null,
                    counterTypeInput.getText(),
                    Integer.parseInt(counterIdInput.getText()),
                    null, //TODO: use localDateTime here
                    Double.parseDouble(powerCurrentInput.getText()),
                    Double.parseDouble(householdCurrentInput.getText()),
                    counterChangeInput.isSelected(),
                    commentInput.getText()
            );
        }
        catch (NumberFormatException e) {
            errorMessageLabel.setText(ERROR_INVALID_INPUT);
            return;
        }

        clearErrorMessage();
        clearInputFields();
        DataHandler.addData(m);
    }

    public void clearErrorMessage() {
        errorMessageLabel.setText("");
    }

    public void clearInputFields() {
        for(JTextField j : inputList) {
            j.setText("");
        }

    }

    private void exit() {
        System.exit(0);
    }

}
