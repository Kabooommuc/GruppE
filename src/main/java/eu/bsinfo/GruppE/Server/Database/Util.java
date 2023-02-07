package eu.bsinfo.GruppE.Server.Database;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {

    private Util() {
    }

    //singleton
    private static Connection con = null;

    // factory method
    public static Connection getConnection(final String db) {
        // if connection does not exist, instance connection
        if (con == null) {
            try {
                final Properties prop = new Properties();
                prop.load(new FileReader(db+".properties"));
                final String dburl = prop.getProperty("DBURL");
                final String dbuser = prop.getProperty("DBUSER");
                final String dbpw = prop.getProperty("DBPW");

                con = DriverManager.getConnection(dburl,dbuser, dbpw);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        return con;
    }

    // close
    public static void close(final AutoCloseable obj) {
        if (obj != null) {
            try {
                obj.close();
            } catch (final Exception e) {
                // ignore ( code == aus skript )
            }
        }
    }
}
