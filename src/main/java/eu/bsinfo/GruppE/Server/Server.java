package eu.bsinfo.GruppE.Server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.net.httpserver.HttpServer;
import eu.bsinfo.GruppE.Server.models.Ablesung;
import eu.bsinfo.GruppE.Server.ressources.AblesungRessource;
import eu.bsinfo.GruppE.Server.ressources.KundenRessource;
import eu.bsinfo.GruppE.Server.ressources.UUIDRessource;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class Server {

    private static final File targetDirectoryFile = Paths.get("target").toFile();
    private static final String serverAblesungenFileName = "server_ablesungen";
    private static final String serverKundenFileName = "server_kunden";
    private static final String serverUUIDFileName = "server_uuid";
    private static final String serveridFileName = "server_id";
    final static String pack = "eu.bsinfo.GruppE.Server.ressources";
    static HttpServer server;
    final static ResourceConfig rc = new ResourceConfig().packages(pack);

   // static Connection con = Util.getConnection("gm3");
    public static void main(String[] args) throws IOException {
        String url = "http://localhost:8080/rest";
        startServer(url, true);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                stopServer(true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, "Shutdown-thread"));
    }

    public static void startServer(String url, boolean loadFromFile) throws IOException {
        System.out.println("Start server");
        System.out.println(url);

        if(loadFromFile){
            System.out.println("Loading from files...");

            ObjectMapper objMapper = new ObjectMapper()
                    .registerModule(new JavaTimeModule());
            File jsonFileAblesungen = new File(targetDirectoryFile + "/" + serverAblesungenFileName + ".json");
            if(jsonFileAblesungen.exists()) {
                Ablesung[] importedArrayData = objMapper.readValue(jsonFileAblesungen, Ablesung[].class);
                AblesungRessource.ablesungen = new ArrayList<>(Arrays.asList(importedArrayData));
            }
        }

        server = JdkHttpServerFactory.createHttpServer(URI.create(url), rc);

        //try {
        //    createDB();
        //    System.out.println("Database created....");
        //} catch (SQLException e) {
        //    throw new RuntimeException(e);
        //}
        System.out.println("Ready for Requests....");
    }

    public static void stopServer(boolean saveToFile) throws IOException {
        if(saveToFile) {
            System.out.println("Saving to files...");
            saveData(AblesungRessource.ablesungen, serverAblesungenFileName);
            saveData(KundenRessource.kunden, serverKundenFileName);
            saveData(UUIDRessource.idUUIDpairs, serverUUIDFileName);
            saveData(UUIDRessource.UUIDidpairs, serveridFileName);
        }

        if(server == null || server.getExecutor() == null )
            return;

      //  Util.close(con);
        server.stop(0);
    }

    private static void saveData(Object dataToExport, String fileNameNoSuffix) throws IOException {
        File jsonFile = new File(targetDirectoryFile + "/" + fileNameNoSuffix + ".json");

        ObjectMapper objMapper = new ObjectMapper()
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .configure(SerializationFeature.INDENT_OUTPUT, true)
                .registerModule(new JavaTimeModule());
        objMapper.writeValue(jsonFile, dataToExport);
    }

    public static UUID convertStringToUUID(String strUUID) {
        UUID objUUID;

        try {
            objUUID = UUID.fromString(strUUID);
        } catch (IllegalArgumentException e) {
            return null;
        }

        return objUUID;
    }

}
