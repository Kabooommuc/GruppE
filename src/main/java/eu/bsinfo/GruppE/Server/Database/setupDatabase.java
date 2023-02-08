package eu.bsinfo.GruppE.Server.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static eu.bsinfo.GruppE.Server.Database.Util.getConnection;

public class setupDatabase {
    static Connection con = getConnection("gm3");

    public static void createDB() throws SQLException {
        Statement st = con.createStatement();
        String sqlkunde = """
CREATE TABLE IF NOT EXISTS Kunde
(
    uuid    VARCHAR(36) not null primary key,
    name    VARCHAR(50) not null,
    vorname VARCHAR(50) not null
);
""";
        String sqlablesung = """
CREATE TABLE IF NOT EXISTS Ablesung
(
    uuid VARCHAR(36) not null primary key,
    zaehlerNummer VARCHAR(50) NOT NULL,
    ableseDatum DATE not null,
    kommentar TEXT,
    neuEingebaut BOOLEAN,
    zaehlerstand DOUBLE,
    kunde VARCHAR(36),
    foreign key (kunde) references Kunde (uuid)
);
""";
        ResultSet rsKunde = st.executeQuery(sqlkunde);
        ResultSet rsAblesung = st.executeQuery(sqlablesung);
    }
}
