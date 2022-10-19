package eu.bsinfo.GruppE;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The DataExporter contains all methods regarding the export and import functionality of the application.
 * It does not handle Exceptions and will throw them to the calling class.
 */
public class DataExporter {

    // Path to /target as File Object
    static final File resourceDirectoryFile = Paths.get("target").toFile();

    /**
     * Exports the given ArrayList of MeasurementData to a JSON file in ´/target´ with the given fileName.
     * The Jackson ObjectMapper will not write Dates as Timestamps and will pretty-print the JSON file.
     *
     * @param dataToExport     An ArrayList of MeasurementData to export
     * @param fileNameNoSuffix A fileName string without file extension
     * @throws IOException When the ObjectMapper fails to write the JSON file
     */
    public static void exportJSON(ArrayList<MeasurementData> dataToExport, String fileNameNoSuffix) throws IOException {
        File jsonFile = new File(resourceDirectoryFile + "/" + fileNameNoSuffix + ".json");

        ObjectMapper objMapper = new ObjectMapper()
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .configure(SerializationFeature.INDENT_OUTPUT, true)
                .registerModule(new JavaTimeModule());
        objMapper.writeValue(jsonFile, dataToExport);
    }

    /**
     * Exports the given ArrayList of MeasurementData to a CSV file in ´/target´ with the given fileName.
     * The Jackson CSVMapper will not write Dates as Timestamps.
     *
     * @param dataToExport     An ArrayList of MeasurementData to export
     * @param fileNameNoSuffix A fileName string without file extension
     * @throws IOException When the CSVMapper fails to write the CSV file
     */
    public static void exportCSV(ArrayList<MeasurementData> dataToExport, String fileNameNoSuffix) throws IOException {
        File csvFile = new File(resourceDirectoryFile + "/" + fileNameNoSuffix + ".csv");

        CsvMapper csvMapper = (CsvMapper) new CsvMapper()
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .registerModule(new JavaTimeModule());
        CsvSchema csvSchema = csvMapper.schemaFor(MeasurementData.class).withHeader();
        csvMapper.writer(csvSchema).writeValue(csvFile, dataToExport);
    }

    /**
     * Returns an ArrayList of MeasurementData from an JSON file in ´/target´ with the given fileName.
     *
     * @param fileNameNoSuffix A fileName string without file extension
     * @return ArrayList of MeasurementData
     * @throws IOException When the ObjectMapper fails to read the JSON file
     */
    public static ArrayList<MeasurementData> importJson(String fileNameNoSuffix) throws IOException {
        File jsonFile = new File(resourceDirectoryFile + "/" + fileNameNoSuffix + ".json");

        ObjectMapper objMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule());

        MeasurementData[] importedArrayData = objMapper.readValue(jsonFile, MeasurementData[].class);
        return new ArrayList<>(Arrays.asList(importedArrayData));
    }

}
