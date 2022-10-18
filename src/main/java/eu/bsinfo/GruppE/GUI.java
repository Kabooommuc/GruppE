package eu.bsinfo.GruppE;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class GUI extends JFrame {

    private final String ERROR_INVALID_INPUT = "Error: Input data is not valid";

    private final JTextField customerIdInput = new JTextField();
    private final JTextField houseNumberInput = new JTextField();
    private final JTextField apartmentNumberInput = new JTextField();
    private final JTextField counterTypeInput = new JTextField();
    private final JTextField counterIdInput = new JTextField();
    private final JTextField measurementReadingDateTimeInput = new JTextField();
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
        GridBagConstraints constraint = new GridBagConstraints();
        base.setLayout(new GridBagLayout());
        constraint.fill = GridBagConstraints.HORIZONTAL;

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

        constraint.gridx = 0;
        constraint.gridy = 0;
        base.add(inputFields, constraint);

        final Container dataScrollpane = new Container();
        dataScrollpane.setLayout(new ScrollPaneLayout());

        constraint.gridx = 0;
        constraint.gridy = 1;

        // error messages should be written into this container
        final Container errorLog = new Container();
        errorLog.add(errorMessageLabel);

        final Container actionButtons = new Container();
        // TODO: Buttons muessen
        GridBagLayout actionButtonsGridBagLayout = new GridBagLayout();              // actionButtonsGridBagLayout
        GridBagConstraints actionButtonsGridBagConstraints = new GridBagConstraints();    // actionButtonsGridBagConstraints
        actionButtons.setLayout(actionButtonsGridBagLayout);
        actionButtonsGridBagLayout.setConstraints(actionButtons, actionButtonsGridBagConstraints);

        JButton addButton = new JButton("Add");
        JButton saveButton = new JButton("Save");
        JButton exportButton = new JButton("Export");
        JButton exitButton = new JButton("Exit");

        actionButtonsGridBagConstraints.gridx = 0;
        actionButtons.add(addButton);
        actionButtonsGridBagConstraints.gridx = 1;
        actionButtons.add(saveButton);
        actionButtonsGridBagConstraints.gridx = 3;
        actionButtons.add(exportButton);
        actionButtonsGridBagConstraints.gridx = 4;
        actionButtonsGridBagConstraints.anchor = GridBagConstraints.EAST;
        actionButtons.add(exitButton);

        constraint.gridx = 0;
        constraint.gridy = 3;
        base.add(actionButtons, constraint);

        setSize(1500, 150);
        setVisible(true);

        addButton.addActionListener(e -> addData());
        saveButton.addActionListener(e -> save());
        exportButton.addActionListener(e -> {});     //TODO
        exitButton.addActionListener(e -> exit());
    }

    public void addData() {
        MeasurementData m;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try {
            m = new MeasurementData(
                    Integer.parseInt(customerIdInput.getText()),
                    houseNumberInput.getText(),
                    Integer.parseInt(counterIdInput.getText()),
                    LocalDate.parse(measurementReadingDateTimeInput.getText(),formatter), //TODO: use localDateTime here
                    Double.parseDouble(powerCurrentInput.getText()),
                    Double.parseDouble(householdCurrentInput.getText()),
                    counterChangeInput.isSelected(),
                    commentInput.getText()
            );
        }
        catch (NumberFormatException | DateTimeParseException e) {
            setErrorMessage(ERROR_INVALID_INPUT);
            return;
        }

        clearErrorMessage();
        clearInputFields();
        System.out.println(m);
        DataHandler.addData(m);
    }

    public void setErrorMessage(String error) {
        errorMessageLabel.setText(error);
    }

    public void clearErrorMessage() {
        errorMessageLabel.setText("");
    }

    public void clearInputFields() {
        for(JTextField t : inputList) {
            t.setText("");
        }

    }

    public void save() {
        DataHandler.saveData();
    }

    private void exit() {
        System.exit(0);
    }

}
