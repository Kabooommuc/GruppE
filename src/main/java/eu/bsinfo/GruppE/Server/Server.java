package eu.bsinfo.GruppE.Server;

import com.sun.net.httpserver.HttpServer;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

public class Server {

    final static String pack = "eu.bsinfo.GruppE.Server.ressources";
    static HttpServer server;
    final static ResourceConfig rc = new ResourceConfig().packages(pack);

    public static void main(String[] args) {
        String url = "http://localhost:8080/rest";
        startServer(url, false);
    }

    public static void startServer(String url, boolean loadFromFile){
        System.out.println("Start server");
        System.out.println(url);
        if(server == null || server.getExecutor() == null) {
            server = JdkHttpServerFactory.createHttpServer(URI.create(url), rc);
            System.out.println("Ready for Requests....");
        }
    }

    public static void stopServer(boolean saveToFile) {
        if(server == null || server.getExecutor() == null ) {
            return;
        }
        server.stop(0);
    }

}
