package eu.bsinfo.GruppE.GUI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.bsinfo.GruppE.Client.GuiToRestClient;
import eu.bsinfo.GruppE.Server.models.Ablesung;
import eu.bsinfo.GruppE.Server.models.Kunde;
import lombok.Getter;

import java.awt.*;
import java.io.IOException;
import java.time.ZoneId;
import java.util.ArrayList;

/**
 * Stores and manages the already entered data
 */
public class DataHandler {
    public static final String SAVE_FILENAME = "data";
    public static final String EXPORT_FILENAME = "export";

    private static final String ERROR_WHILE_LOADING = GUI.ERROR_TAG + "Failed to load file!";
    private static final String ERROR_WHILE_SAVING = GUI.ERROR_TAG + "Failed to save file!";
    private static final String ERROR_COULD_NOT_OPEN = GUI.ERROR_TAG + "Could not open target directory";

    @Getter
    public static ArrayList<MeasurementData> data = new ArrayList<>();
    @Getter
    public static ArrayList<String> kundenIDs = new ArrayList<>();

    /**
     * Loads data from the cached save file and sets the data ArrayList to the data returned from the import.
     */
    public static void loadData() throws JsonProcessingException {
        // get all Kunden
        String allKundenString = GuiToRestClient.getFromRest("kunden");
        System.out.println(allKundenString);

        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<Kunde> kundenList = objectMapper.readValue(allKundenString, new TypeReference<>() {
        });
        for (Kunde k: kundenList) {
            System.out.println(k);
            kundenIDs.add(k.getId());
            GUI.customerIdInput.addItem(k.getId());
        }

        // add ablesungen to table
    }

    /**
     * Adds an MeasurementData object to the application data ArrayList
     *
     * @param md The MeasurementData Object to add
     */
    public static void addData(MeasurementData md) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

         //hier muss aus measurementData eine Ablesung gemacht werden. also alle values raus und statt kundennr das kundenobjekt in ablesungen rein

        String kundenNr = md.customerId;
        String kundeJsonString = GuiToRestClient.getFromRest("kunden/"+kundenNr);
        Kunde kunde = objectMapper.readValue(kundeJsonString, Kunde.class);
        if (kundeJsonString.equals("404 - not found")) {
            GUI.displayMessage(GUI.ERROR_TAG + "Kunde " + md.customerId + " wurde nicht gefunden");
        }
        Ablesung newAblesung = new Ablesung(
                String.valueOf(md.counterId),
                md.measurementReadingDateTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                kunde, md.comment, md.counterChange, md.powerCurrent);

        System.out.println(newAblesung);
        GuiToRestClient.postAblesung(newAblesung);
    }

    /**
     * Saves the current application data ArrayList as an JSON or CSV file
     * or displays an error if saving was unsuccessful.
     */
    public static void exportData(ExportType exportType, String fileName) {
        try {
            if (exportType == ExportType.JSON) DataExporter.exportJSON(data, fileName);
            else if (exportType == ExportType.CSV) DataExporter.exportCSV(data, fileName);
        } catch (IOException e) {
            e.printStackTrace();
            GUI.displayMessage(ERROR_WHILE_SAVING);
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
            GUI.displayMessage(ERROR_COULD_NOT_OPEN);
        }
    }

}

