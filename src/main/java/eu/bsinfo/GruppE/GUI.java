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
        final boolean shouldFill = true;
        GridBagConstraints c = new GridBagConstraints();
        base.setLayout(new GridBagLayout());
        if (shouldFill) {
            c.fill = GridBagConstraints.HORIZONTAL;
        }

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
        for (JLabel j : list) {
            j.setHorizontalAlignment(4);
            j.setBorder(new EmptyBorder(0, 15, 0, 10));
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

        c.gridx = 0;
        c.gridy = 0;
        base.add(inputFields, c);

        final Container dataScrollpane = new Container();
        dataScrollpane.setLayout(new ScrollPaneLayout());

        // error messages should be written into this container
        final Container errorLog = new Container();
        
//        base.add(errorMessageLabel);



        final Container actionButtons = new Container();
        GridBagLayout aBgbl = new GridBagLayout();              // actionButtonsGridBagLayout
        GridBagConstraints aBgbc = new GridBagConstraints();    // actionButtonsGridBagConstraints
        actionButtons.setLayout(aBgbl);
        aBgbl.setConstraints(actionButtons, aBgbc);

        JButton addButton = new JButton("Add");
        JButton saveButton = new JButton("Save");
        JButton exportButton = new JButton("Export");
        JButton exitButton = new JButton("Exit");

        aBgbc.gridx = 0;
        actionButtons.add(addButton);
        aBgbc.gridx = 1;
        actionButtons.add(saveButton);
        aBgbc.gridx = 3;
        actionButtons.add(exportButton);
        aBgbc.gridx = 4;
        aBgbc.anchor = GridBagConstraints.EAST;
        actionButtons.add(exitButton);

        c.gridx = 0;
        c.gridy = 1;
        base.add(actionButtons, c);

        setSize(1500, 150);
        setVisible(true);

        addButton.addActionListener(e -> addData());
//        saveButton.addActionListener(e -> save());
//        exportButton.addActionListener(e -> export());
        exitButton.addActionListener(e -> exit());
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
