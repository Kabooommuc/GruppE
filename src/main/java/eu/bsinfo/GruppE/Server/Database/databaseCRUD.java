package eu.bsinfo.GruppE.Server.Database;

import eu.bsinfo.GruppE.Server.models.Ablesung;
import eu.bsinfo.GruppE.Server.models.Kunde;

import java.sql.*;
import java.util.UUID;

public class databaseCRUD {
    static Connection con = Util.getConnection("gm3");

    final static int KUNDEUUID = 1;
    final static int KUNDENAME = 2;
    final static int KUNDEVORNAME = 3;

    public static void createKunde(Kunde kunde) throws SQLException {
        System.out.println("createKunde");
        String uuid = String.valueOf(kunde.getId());
        String name = kunde.getName();
        String vorname = kunde.getVorname();

        PreparedStatement checkUUID = con.prepareStatement("SELECT uuid From Kunde WHERE uuid=?;");
        checkUUID.setString(1, uuid);
        ResultSet rsUUID = checkUUID.executeQuery();
        Util.printRs(rsUUID);

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
    public static Kunde readKunde(UUID uuid) throws SQLException {
        System.out.println("readKunde");
        PreparedStatement pst = con.prepareStatement("SELECT * from Kunde WHERE uuid=?;");
        pst.setString(1, String.valueOf(uuid));
        ResultSet rs = pst.executeQuery();

        // Check if UUID does not exist in database
        if (rs.first() == false) {
            throw new Error("Kunde does not exist");
        }
        rs.beforeFirst();
        Util.printRs(rs);

        rs.beforeFirst();
        if (rs.next()) {
            Kunde kunde = new Kunde(UUID.fromString(rs.getString(KUNDEUUID)),rs.getString(KUNDENAME),rs.getString(KUNDEVORNAME));
            System.out.println(kunde);
            return kunde;
        }

        System.err.println("404 - Kunde not found");
        return null;
    }

    public static void updateKunde(Kunde kunde) throws SQLException {
        System.out.println("databaseCRUD.updateKunde");
        System.out.println(kunde);

        UUID uuid = kunde.getId();

        PreparedStatement pst = con.prepareStatement("SELECT * from Kunde WHERE uuid=?;");
        pst.setString(1, String.valueOf(uuid));
        ResultSet rs = pst.executeQuery();

        // Check if UUID does not exist in database
        if (rs.first() == false) {
            throw new Error("Kunde does not exist");
        }
        rs.beforeFirst();
        Util.printRs(rs);

        rs.beforeFirst();
        if (rs.next()) {
            PreparedStatement update = con.prepareStatement("UPDATE Kunde SET name = ?, vorname = ? WHERE uuid=?;");
            update.setString(1,kunde.getName());
            update.setString(2,kunde.getVorname());
            update.setString(3, String.valueOf(uuid));
            ResultSet updateRs = update.executeQuery();

            ResultSet rs2 = pst.executeQuery();
            Util.printRs(rs2);
            return;
        }

        System.err.println("404 - Kunde not found");
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
