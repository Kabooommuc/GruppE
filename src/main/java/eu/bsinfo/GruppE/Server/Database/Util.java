package eu.bsinfo.GruppE.Server.Database;

import org.nocrala.tools.texttablefmt.Table;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
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

    public static void printRs(final ResultSet rs) {

        try {
            final ResultSetMetaData rsmeta = rs.getMetaData();
            final int cols = rsmeta.getColumnCount();
            final Table t = new Table(cols);

            for (int i = 1;  i <= cols; i++) {
                    final String label = rsmeta.getColumnLabel(i);
                    t.addCell(label);
            }

            while (rs.next()) {
                for (int i = 1; i <= cols; i++) {
                    final Object obj = rs.getObject(i);
                    t.addCell(obj == null ? "" : obj.toString());
                }
            }
            System.out.println(t.render());
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
