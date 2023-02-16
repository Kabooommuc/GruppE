package eu.bsinfo.GruppE.GUI;

import com.fasterxml.jackson.core.JsonProcessingException;
import eu.bsinfo.GruppE.GUI.textfields.*;
import eu.bsinfo.GruppE.Server.models.Kunde;
import lombok.Getter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.ZoneId;
import java.util.Arrays;

import static eu.bsinfo.GruppE.Client.GuiToRestClient.getKundeFromUUID;
import static eu.bsinfo.GruppE.Client.GuiToRestClient.postKunde;

public class GUI extends JFrame {


    private static final String INFO = "Info";
    public static final String INFO_TAG = "[" + INFO + "]";
    private static final String WARNING = "Warnung";
    public static final String WARNING_TAG = "[" + WARNING + "]";
    private static final String ERROR = "Fehler";
    public static final String ERROR_TAG = "[" + ERROR + "]";

    //Dropdown Kundenliste
    public static final JComboBox<String> customerIdInput = new JComboBox<>();

    private final MyTextField houseNumberInput = new MyTextField();
    private final MyTextField apartmentNumberInput = new MyTextField();
    private final MyTextField counterTypeInput = new MyTextField();
    private final IntTextField counterIdInput = new IntTextField();
    private final MyDateChooser readingDate = new MyDateChooser();
    private final JCheckBox counterChangeInput = new JCheckBox();
    private final MyTextField commentInput = new MyTextField();
    private final DoubleTextField powerCurrentInput = new DoubleTextField();
    private final DoubleTextField householdCurrentInput = new DoubleTextField();


    private final JButton addButton = new JButton("Datensatz Hinzufügen");
    private final JButton saveButton = new JButton("Save");
    private final JButton exportButton = new JButton("Exportieren");

    @Getter
    private final JTextField[] inputList = {
            //customerIdInput,
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
    private final String[] INPUT_FIELD_NAMES = {"KundenNr ", "HausNr ", "WohnungsNr ", "Kraftstrom (kWh) ", "Haushaltsstrom (kWh) ", "Zählerart ", "ZählerID ", "Ablesedatum ", "Kommentar ", "Zählertausch "};

    private final String[] COLUMN_NAMES = {"KundenID", "Hausnummer", "WohnungsNr", "Zählerart", "ZählerID", "Ablesedatum", "Kraftstrom", "Haushaltsstrom", "Zählertausch", "Kommentar"};
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

        //TODO: something like this once customers can be loaded
        //if(DataHandler.getKundenIDs().size()==0) {
        //    saveButton.setEnabled(false);
        //    addButton.setEnabled(false);
        //    exportButton.setEnabled(false);
        //}

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
        JLabel commentLabel = new JLabel(INPUT_FIELD_NAMES[8]);
        JLabel counterChangeLabel = new JLabel(INPUT_FIELD_NAMES[9]);

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
        inputFields.add(readingDate);
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

        JScrollPane scrollPane = new JScrollPane(table);
        dataTable.add(scrollPane);


        base.add(dataTable, BorderLayout.CENTER);


        // error messages should be written into this container
        final Container errorLog = new Container();
        errorLog.add(errorMessageLabel);

        final Container actionButtons = new Container();
        actionButtons.setLayout(new GridLayout(1,10,5,5));

        JButton createKunde = new JButton("Kunde Erstellen");
        JButton exitButton = new JButton("Schließen");

        actionButtons.add(createKunde);
        actionButtons.add(addButton);
        for(int i = 0; i < 5;i++)
            actionButtons.add(new JLabel(""));
        actionButtons.add(exportButton);
        actionButtons.add(exitButton);

        base.add(actionButtons, BorderLayout.SOUTH);

        createKunde.addActionListener(e -> openCustomerDialogue());
        addButton.addActionListener(e -> {
            try {
                addData();
            } catch (JsonProcessingException ex) {
                throw new RuntimeException(ex);
            }
        });

        saveButton.addActionListener(e -> save());
        exportButton.addActionListener(e -> export());
        exitButton.addActionListener(e -> exit());

        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLocationRelativeTo(null);
        setVisible(true);
    }


    public void openCustomerDialogue() {
        JFrame f = new JFrame();
        f.setAlwaysOnTop(true);

        JLabel firstName = new JLabel("Vorname: ");
        JLabel lastName = new JLabel("Nachname: ");
        AlphabeticalTextField firstNameInput= new AlphabeticalTextField();
        AlphabeticalTextField lastNameInput= new AlphabeticalTextField();
        JButton createCustomer = new JButton("Kunde Erstellen");
        JButton cancel = new JButton("Abbrechen");

        lastNameInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER) {
                    createCustomer.doClick();
                }
            }
        });

        cancel.addActionListener(e -> f.dispose());
        createCustomer.addActionListener(e -> {
            if(firstNameInput.getText().equals("") || lastNameInput.getText().equals("")) {
                displayMessage(WARNING_TAG + "Feld darf nicht leer sein.");
            }
            else {
                addKunde(firstNameInput.getText(), lastNameInput.getText());
                f.dispose();
            }
        });

        JPanel  j = new JPanel();
        j.setBorder(new EmptyBorder(10,10,10,10));
        j.setLayout(new GridLayout(3,2,10,30));
        j.add(firstName);
        j.add(firstNameInput);
        j.add(lastName);
        j.add(lastNameInput);
        j.add(createCustomer);
        j.add(cancel);

        f.getContentPane().add(j);

        f.setSize(400,230);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
    public void addKunde(String vorname, String nachname) {

        Kunde newKunde = new Kunde(nachname, vorname);
        postKunde(newKunde);
        displayMessage(INFO_TAG + "Kunde erstellt");

        String newID = newKunde.getId();
        DataHandler.getKundenIDs().add(newID);
        customerIdInput.addItem(newID);

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
     * and the inputFields are cleared. If invalid, an error message is displayed and the data is not added.
     */
    public void addData() throws JsonProcessingException {
        MeasurementData md;
        String fieldCheckResult = InputChecker.checkInputFields(this);

        if (fieldCheckResult != null) {
            displayMessage(fieldCheckResult);
            return;
        }
        if (readingDate.getDate() == null) {
            displayMessage(ERROR_TAG + "Invalides Datum");
            return;
        }

        Kunde kunde = (Kunde) getKundeFromUUID(String.valueOf(customerIdInput.getSelectedItem()));

        md = new MeasurementData(
                kunde.getId(),
                houseNumberInput.getText(),
                apartmentNumberInput.getText(),
                Integer.parseInt(counterIdInput.getText()),
                readingDate.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                Double.parseDouble(powerCurrentInput.getText()),
                Double.parseDouble(householdCurrentInput.getText()),
                counterChangeInput.isSelected(),
                commentInput.getText()
        );

        System.out.println(md);

        clearInputFields();

        // This is handling the MeassurementData to become a Ablesung and be sent to the server
        DataHandler.addData(md);
        addRow(md);
    }

    public void addRow(MeasurementData md) {
        Object[] row = {
                md.customerId,
                md.houseNumber,
                md.apartmentNumber,
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
     * Displays an error message as a popup
     *
     * @param error the error message to display
     */
    public static void displayMessage(String error) {

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
        JFrame f = new JFrame();
        f.setAlwaysOnTop(true);
        JOptionPane.showMessageDialog(f, errorMessage, errorType, windowType);
    }

    /**
     * Clears all the inputFields of the input values
     */
    public void clearInputFields() {
        for (JTextField t : inputList) {
            t.setText("");
        }
        readingDate.setCalendar(null);

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
