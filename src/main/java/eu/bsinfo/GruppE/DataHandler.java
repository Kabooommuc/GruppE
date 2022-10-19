package eu.bsinfo.GruppE;

import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;

import static eu.bsinfo.GruppE.Runtime.gui;

/**
 * Stores and manages the already entered data
 */
public class DataHandler {
    private static final String SAVE_FILENAME = "data";
    private static final String ERROR_WHILE_SAVING = "Error: File failed to save";
    @Getter
    public static ArrayList<MeasurementData> data = new ArrayList<>();

    /**
     * Adds a piece of MeasurmentData to the data array
     * @param d the piece of data to be added
     */
    public static void addData(MeasurementData d) {
        data.add(d);
    }

    /**
     * Saves the current data array as a JSON file or displays an error if saving was unsuccessful
     */
    public static void saveData() {
        try {
            DataExporter.exportJSON(data, SAVE_FILENAME);
        }
        catch (IOException e) {
            e.printStackTrace();
            gui.setErrorMessage(ERROR_WHILE_SAVING);
        }
    }

}
