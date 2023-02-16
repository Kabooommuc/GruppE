package eu.bsinfo.GruppE.Server;

import com.sun.net.httpserver.HttpServer;
import eu.bsinfo.GruppE.Server.Database.Util;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import static eu.bsinfo.GruppE.Server.Database.setupDatabase.createDB;

public class Server {

    final static String pack = "eu.bsinfo.GruppE.Server.ressources";
    static HttpServer server;
    final static ResourceConfig rc = new ResourceConfig().packages(pack);
    static Connection con = Util.getConnection("gm3");

    public static void main(String[] args) {
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

    public static void startServer(String url, boolean loadFromFile) {
        System.out.println("Start server");
        System.out.println(url);

        server = JdkHttpServerFactory.createHttpServer(URI.create(url), rc);

        try {
            createDB();
            System.out.println("Database created....");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Ready for Requests....");
    }

    public static void stopServer(boolean saveToFile) throws IOException {
        if(saveToFile) {
            System.out.println("Server shutdown...");
        }

        if(server == null || server.getExecutor() == null )
            return;

        Util.close(con);
        server.stop(0);
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
