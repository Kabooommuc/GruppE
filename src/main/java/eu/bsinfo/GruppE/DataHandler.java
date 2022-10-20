package eu.bsinfo.GruppE;

import lombok.Getter;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import static eu.bsinfo.GruppE.Runtime.gui;

/**
 * Stores and manages the already entered data
 */
public class DataHandler {
    public static final String SAVE_FILENAME = "data";
    public static final String EXPORT_FILENAME = "export";

    private static final String ERROR_WHILE_SAVING = "Error: File failed to save";
    private static final String ERROR_COULD_NOT_OPEN = "Error: Could not open target directory";

    @Getter
    public static ArrayList<MeasurementData> data = new ArrayList<>();

    /**
     * Adds an MeasurementData object to the application data ArrayList
     *
     * @param md The MeasurementData Object to add
     */
    public static void addData(MeasurementData md) {
        data.add(md);
    }

    /**
     * Saves the current application data ArrayList as an JSON or CSV file
     * or displays an error if saving was unsuccessful.
     */
    public static void exportData(ExportType exportType, String fileName) {
        try {
            if (exportType == ExportType.JSON)
                DataExporter.exportJSON(data, fileName);
            else if (exportType == ExportType.CSV)
                DataExporter.exportCSV(data, fileName);
        } catch (IOException e) {
            e.printStackTrace();
            gui.setErrorMessage(ERROR_WHILE_SAVING);
        }
    }

    /**
     * Opens the folder where the data gets saves to in Windows Explorer.
     */
    public static void openTargetDirectoryInExplorer() {
        try {
            Desktop.getDesktop().open(DataExporter.targetDirectoryFile);
        } catch (IOException e) {
            e.printStackTrace();
            gui.setErrorMessage(ERROR_COULD_NOT_OPEN);
        }
    }

}

