package eu.bsinfo.GruppE.Server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.net.httpserver.HttpServer;
import eu.bsinfo.GruppE.Server.models.Kunde;
import eu.bsinfo.GruppE.Server.ressources.KundenRessource;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class Server {

    static final File targetDirectoryFile = Paths.get("target").toFile();
    final static String pack = "eu.bsinfo.GruppE.Server.ressources";
    static HttpServer server;
    final static ResourceConfig rc = new ResourceConfig().packages(pack);

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
            File jsonFileKunden = new File(targetDirectoryFile + "/" + "server_kunden" + ".json");
            if(jsonFileKunden.exists()) {
                Kunde[] importedArrayData = objMapper.readValue(jsonFileKunden, Kunde[].class);
                KundenRessource.kunden = new ArrayList<>(Arrays.asList(importedArrayData));
            }

        }

        server = JdkHttpServerFactory.createHttpServer(URI.create(url), rc);
        System.out.println("Ready for Requests....");
    }

    public static void stopServer(boolean saveToFile) throws IOException {
        if(saveToFile) {
            System.out.println("Saving to files...");
            saveData(KundenRessource.kunden, "server_kunden");
        }
        if(server == null || server.getExecutor() == null ) {
            return;
        }
        server.stop(0);
    }

    private static void saveData(ArrayList<?> dataToExport, String fileNameNoSuffix) throws IOException {
        File jsonFile = new File(targetDirectoryFile + "/" + fileNameNoSuffix + ".json");

        ObjectMapper objMapper = new ObjectMapper()
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .configure(SerializationFeature.INDENT_OUTPUT, true)
                .registerModule(new JavaTimeModule());
        objMapper.writeValue(jsonFile, dataToExport);
    }

}
