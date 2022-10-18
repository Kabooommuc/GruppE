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

public class DataExporter {

    static final File resourceDirectoryFile = Paths.get("src", "main", "resources").toFile();

    public static void exportCSV(ArrayList<MeasurementData> dataArray) throws IOException {
        File csvFile = new File(resourceDirectoryFile + "/export.csv");

        CsvMapper csvMapper = (CsvMapper) new CsvMapper()
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .registerModule(new JavaTimeModule());
        CsvSchema csvSchema = csvMapper.schemaFor(MeasurementData.class).withHeader();
        csvMapper.writer(csvSchema).writeValue(csvFile, dataArray);
    }

    public static void exportJSON(ArrayList<MeasurementData> dataArray) throws IOException {
        File jsonFile = new File(resourceDirectoryFile + "/export.json");

        ObjectMapper objMapper = new ObjectMapper()
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .registerModule(new JavaTimeModule());
        objMapper.writeValue(jsonFile, dataArray);
    }

}
