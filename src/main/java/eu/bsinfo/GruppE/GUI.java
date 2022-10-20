package eu.bsinfo.GruppE;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
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
        final String[] columnNames = {"KundenID","Hausnummer","WohnungsNr","Zählerart","ZählerID","Ablesedatum","Zählertausch","Kraftstrom","Haushaltsstrom","Kommentar"};
        dataScrollpane.setLayout(new GridBagLayout());
        DefaultTableModel tableModel = new DefaultTableModel(null, columnNames);

        for (MeasurementData i: DataHandler.getData()) {
            Object[] row = {
                    i.customerId,
                    i.houseNumber,
                    i.apartmentNumber, // always null
                    i.counterType,
                    i.counterId,
                    i.measurementReadingDateTime,
                    i.powerCurrent,
                    i.householdCurrent,
                    i.counterChange,
                    i.comment
            };
            tableModel.addRow(row);
        }
        JTable table = new JTable(tableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane scrollPane = new JScrollPane(table);
        dataScrollpane.add(scrollPane);


        constraint.gridx = 0;
        constraint.gridy = 1;
        constraint.fill = GridBagConstraints.BOTH;
        base.add(dataScrollpane, constraint);



        // error messages should be written into this container
        final Container errorLog = new Container();
        errorLog.add(errorMessageLabel);

        final Container actionButtons = new Container();
        // TODO: Buttons muessen umpositioniert werden
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

        setSize(1500, 550);
        setVisible(true);

        addButton.addActionListener(e -> addData());
        saveButton.addActionListener(e -> save());
        exportButton.addActionListener(e -> export());
        exitButton.addActionListener(e -> exit());
    }

    /**
     * Checks if the data in the inputFields is valid. If valid, the data is added to the DataHandler
     * and the inputFields are cleared. If invalid, an error message is displayed and the fields remain filled.
     */
    public void addData() {
        MeasurementData m;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            m = new MeasurementData(
                    Integer.parseInt(customerIdInput.getText()),
                    houseNumberInput.getText(),
                    Integer.parseInt(counterIdInput.getText()),
                    LocalDate.parse(measurementReadingDateTimeInput.getText(),formatter),
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
        DataHandler.addData(m);
    }

    /**
     * Adjusts the errorMessageLabel to display an error.
     * @param error the error message to display
     */
    public void setErrorMessage(String error) {
        errorMessageLabel.setText(error);
    }

    /**
     * Sets the errorMessageLabel to be empty.
     */
    public void clearErrorMessage() {
        errorMessageLabel.setText("");
    }

    /**
     * Clears all the inputFields of the input values
     */
    public void clearInputFields() {
        for(JTextField t : inputList) {
            t.setText("");
        }
    }

    /**
     * Calls on the DataHandler to save the current data
     */
    public void save() {
        DataHandler.exportData(ExportType.JSON, DataHandler.SAVE_FILENAME);
    }

    /**
     * Calls on the DataHandler to export the current data and opens the target directory in Explorer
     */
    public void export() {
        DataHandler.exportData(ExportType.CSV, DataHandler.EXPORT_FILENAME);
        DataHandler.openTargetDirectoryInExplorer();
    }


    /**
     * Saves data and stops the program
     */
    private void exit() {
        save();
        System.exit(0);
    }

}
