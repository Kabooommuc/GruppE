package eu.bsinfo.GruppE.Server.Database;

import eu.bsinfo.GruppE.Server.models.Ablesung;
import eu.bsinfo.GruppE.Server.models.Kunde;

import java.sql.*;

public class databaseCRUD {
    static Connection con = Util.getConnection("gm3");

    public static void createKunde(Kunde kunde) throws SQLException {
        System.out.println("createKunde");
        String uuid = String.valueOf(kunde.getId());
        String name = kunde.getName();
        String vorname = kunde.getVorname();

        PreparedStatement checkUUID = con.prepareStatement("SELECT uuid From Kunde WHERE uuid=?;");
        checkUUID.setString(1, uuid);
        ResultSet rsUUID = checkUUID.executeQuery();

        // Check if UUID already exists in database
        if (rsUUID.first() == true) {
            throw new Error("Kunde already exists");
        }
        Util.close(checkUUID);

        PreparedStatement insertKunde = con.prepareStatement("INSERT INTO Kunde (uuid, name, vorname) VALUES (?,?,?);");
        insertKunde.setString(1,uuid);
        insertKunde.setString(2,name);
        insertKunde.setString(3,vorname);

        ResultSet rsInsert = insertKunde.executeQuery();
        Util.close(rsInsert);
    }

    // TODO: DOES NOT WORK YET COMPLETELY
    public static void readKunde(Kunde kunde) throws SQLException {
        System.out.println("readKunde");
        final String uuid = String.valueOf(kunde.getId());
        PreparedStatement pst = con.prepareStatement("SELECT uuid from Kunde WHERE uuid=?");
        pst.setString(1, uuid);
        ResultSet rs = pst.executeQuery();

        // Check if UUID does not exist in database
        if (rs.first() == false) {
            throw new Error("Kunde does not exist");
        }
        Util.printRs(rs);
    }

    public static void updateKunde(Kunde kunde) {

    }

    public void deleteKunde(Kunde kunde) {

    }

    public void createAblesung(Ablesung ablesung) {

    }

    public void readAblesung(Ablesung ablesung) {

    }

    public void updateAblesung(Ablesung ablesung) {

    }

    public void deleteAblesung(Ablesung ablesung) {

    }


}
