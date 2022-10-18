package eu.bsinfo.GruppE;

import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;

import static eu.bsinfo.GruppE.Runtime.gui;

public class DataHandler {
    private static final String SAVE_FILENAME = "data";
    private static final String ERROR_WHILE_SAVING = "Error: File failed to save";
    @Getter
    public static ArrayList<MeasurementData> data = new ArrayList<>();

    public static void addData(MeasurementData d) {
        data.add(d);
    }

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
