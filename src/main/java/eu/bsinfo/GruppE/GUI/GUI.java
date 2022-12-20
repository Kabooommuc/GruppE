package eu.bsinfo.GruppE.GUI;

import com.toedter.calendar.JDateChooser;
import eu.bsinfo.GruppE.GUI.textfields.IntTextField;
import lombok.Getter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class GUI extends JFrame {


    private static final String INFO = "Info";
    public static final String INFO_TAG = "[" + INFO + "]";
    private static final String WARNING = "Warning";
    public static final String WARNING_TAG = "[" + WARNING + "]";
    private static final String ERROR = "Error";
    public static final String ERROR_TAG = "[" + ERROR + "]";

    private final IntTextField customerIdInput = new IntTextField();
    private final JTextField houseNumberInput = new JTextField();
    private final JTextField apartmentNumberInput = new JTextField();
    private final JTextField counterTypeInput = new JTextField();
    private final IntTextField counterIdInput = new IntTextField();
    private final JTextField measurementReadingDateTimeInput = new JTextField();
    private final JDateChooser measurementReadingDateTimeTest = new JDateChooser();
    private final JCheckBox counterChangeInput = new JCheckBox();
    private final JTextField commentInput = new JTextField();
    private final JTextField powerCurrentInput = new JTextField();

    private final JTextField householdCurrentInput = new JTextField();

    @Getter
    private final JTextField[] inputList = {
            customerIdInput,
            houseNumberInput,
            apartmentNumberInput,
            powerCurrentInput,
            householdCurrentInput,

            counterTypeInput,
            counterIdInput,
            //measurementReadingDateTimeInput,
            commentInput,
    };

    @Getter
    private final String[] INPUT_FIELD_NAMES = {"KundeNr ", "HausNr ", "WohnungsNr ","Kraftstrom ","Haushaltsstrom ","Zählerart ","ZählerID ","Ablesedatum ","Kommentar "};

    private final String[] COLUMN_NAMES = {"KundenID", "Hausnummer", "WohnungsNr", "Zählerart", "ZählerID", "Ablesedatum", "Zählertausch", "Kraftstrom", "Haushaltsstrom", "Kommentar"};
    final double[] COLUMN_WIDTHS = {10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 50.0};

    private final DefaultTableModel tableModel = new DefaultTableModel(COLUMN_NAMES, 0);

    JLabel errorMessageLabel = new JLabel("");

    public GUI() {
        super("Zählerabrechnung - ProgSchnellUndSicher GmbH");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });


        final int FRAME_WIDTH = 1800;
        final int FRAME_HEIGHT = 600;

        final Container base = getContentPane();
        base.setLayout(new BorderLayout());

        final Container inputFields = new Container();
        inputFields.setLayout(new GridLayout(2, 10));

        JLabel customerIdLabel = new JLabel(INPUT_FIELD_NAMES[0]);
        JLabel houseNumberLabel = new JLabel(INPUT_FIELD_NAMES[1]);
        JLabel apartmentNumberLabel = new JLabel(INPUT_FIELD_NAMES[2]);
        JLabel powerCurrentLabel = new JLabel(INPUT_FIELD_NAMES[3]);
        JLabel householdCurrentLabel = new JLabel(INPUT_FIELD_NAMES[4]);
        JLabel counterTypeLabel = new JLabel(INPUT_FIELD_NAMES[5]);
        JLabel counterIdLabel = new JLabel(INPUT_FIELD_NAMES[6]);
        JLabel measurementReadingDateLabel = new JLabel(INPUT_FIELD_NAMES[7]);
        JLabel commentLabel = new JLabel("Kommentar:");

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
        inputFields.add(householdCurrentInput);

        inputFields.add(counterTypeLabel);
        inputFields.add(counterTypeInput);
        inputFields.add(counterIdLabel);
        inputFields.add(counterIdInput);
        inputFields.add(measurementReadingDateLabel);
        //inputFields.add(measurementReadingDateTimeInput);
        inputFields.add(measurementReadingDateTimeTest);
        inputFields.add(commentLabel);
        inputFields.add(commentInput);
        inputFields.add(counterChangeLabel);
        inputFields.add(counterChangeInput);

        base.add(inputFields, BorderLayout.NORTH);

        final Container dataTable = new Container();
        dataTable.setLayout(new BorderLayout());

        for (MeasurementData md : DataHandler.getData()) {
            addRow(md);
        }
        JTable table = new JTable(tableModel);
        setJTableColumnsWidth(table, FRAME_WIDTH, COLUMN_WIDTHS);
        table.setAutoCreateRowSorter(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        new TableCellListener(table, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableCellListener tcl = (TableCellListener) e.getSource();
                updateMDFromRow(tcl.getRow(), tcl.getColumn(), tcl.getNewValue());
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        dataTable.add(scrollPane);


        base.add(dataTable, BorderLayout.CENTER);


        // error messages should be written into this container
        final Container errorLog = new Container();
        errorLog.add(errorMessageLabel);

        final Container actionButtons = new Container();
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

        base.add(actionButtons, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addData());
        saveButton.addActionListener(e -> save());
        exportButton.addActionListener(e -> export());
        exitButton.addActionListener(e -> exit());

        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setVisible(true);
    }

    public static void setJTableColumnsWidth(JTable table, int tablePreferredWidth, double[] percentages) {
        double total = Arrays.stream(percentages).sum();

        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth((int)
                    (tablePreferredWidth * (percentages[i] / total)));
        }
    }

    /**
     * Checks if the data in the inputFields is valid. If valid, the data is added to the DataHandler
     * and the inputFields are cleared. If invalid, an error message is displayed and the fields remain filled.
     */
    public void addData() {
        MeasurementData md;
        String fieldCheckResult = InputChecker.checkInputFields(this);
        if (fieldCheckResult==null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            md = new MeasurementData(
                    Integer.parseInt(customerIdInput.getText()),
                    houseNumberInput.getText(),
                    Integer.parseInt(counterIdInput.getText()),
                   // LocalDate.parse(, formatter),
                    measurementReadingDateTimeTest.getDate(),
                    Double.parseDouble(powerCurrentInput.getText()),
                    Double.parseDouble(householdCurrentInput.getText()),
                    counterChangeInput.isSelected(),
                    commentInput.getText()
            );
        }
        else {
            displayMessage(fieldCheckResult);
            return;

        }

        clearInputFields();
        DataHandler.addData(md);
        addRow(md);
    }

    private void addRow(MeasurementData md) {
        Object[] row = {
                md.customerId,
                md.houseNumber,
                md.apartmentNumber, // always null
                md.counterType,
                md.counterId,
                md.measurementReadingDateTime,
                md.powerCurrent,
                md.householdCurrent,
                md.counterChange,
                md.comment
        };
        tableModel.addRow(row);
    }

    /**
     * Updates a value of the MeasurementData object from the given row index with the new value.
     *
     * @param row      row index of the edited cell
     * @param column   column index of the edited cell
     * @param newValue new value of the edited cell
     */
    private void updateMDFromRow(int row, int column, Object newValue) {
        MeasurementData mdUpdate = DataHandler.data.get(row);
        mdUpdate.setValueBasedOnColumn(column, newValue);
    }

    /**
     * Displays an error message as a popup
     *
     * @param error the error message to display
     */
    public void displayMessage(String error) {

        String errorType = "";
        String errorMessage = error;

        if (error.contains("[") && error.contains("]") && error.indexOf("[") < error.indexOf("]")) {
            errorMessage = error.substring(error.indexOf("]") + 1).trim();
            errorType = error.substring(error.indexOf("[") + 1, error.indexOf("]"));
        }

        int windowType = JOptionPane.PLAIN_MESSAGE;

        switch (errorType) {
            case INFO -> windowType = JOptionPane.INFORMATION_MESSAGE;
            case WARNING -> windowType = JOptionPane.WARNING_MESSAGE;
            case ERROR -> windowType = JOptionPane.ERROR_MESSAGE;
        }
        JOptionPane.showMessageDialog(new JFrame(), errorMessage, errorType, windowType);
    }

    /**
     * Clears all the inputFields of the input values
     */
    public void clearInputFields() {
        for (JTextField t : inputList) {
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
