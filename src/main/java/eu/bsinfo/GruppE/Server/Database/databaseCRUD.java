package eu.bsinfo.GruppE.Server.Database;

import eu.bsinfo.GruppE.Server.models.Ablesung;
import eu.bsinfo.GruppE.Server.models.Kunde;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class databaseCRUD {
    static Connection con = Util.getConnection("gm3");

    final static int KUNDEUUID = 1;
    final static int KUNDENAME = 2;
    final static int KUNDEVORNAME = 3;

    public static ArrayList<Kunde> readAllKunden() throws SQLException {
        ArrayList<Kunde> allKunden = new ArrayList<>();
        System.out.println("readKunde");
        PreparedStatement pst = con.prepareStatement("SELECT * from Kunde");

        ResultSet rs = pst.executeQuery();
        rs.beforeFirst();
        Util.printRs(rs);

        rs.beforeFirst();
        while (rs.next()) {
            Kunde kunde = new Kunde(rs.getString(KUNDEUUID),rs.getString(KUNDENAME),rs.getString(KUNDEVORNAME));
            System.out.println(kunde);
            allKunden.add(kunde);
        }
        return allKunden;
    }
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
        if (rsUUID.first()) {
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

    public static Kunde readKunde(String id) throws SQLException {
        System.out.println("readKunde");
        PreparedStatement pst = con.prepareStatement("SELECT * from Kunde WHERE uuid=?;");
        pst.setString(1, id);
        ResultSet rs = pst.executeQuery();

        // Check if UUID does not exist in database
        if (rs.first() == false) {
            throw new Error("Kunde does not exist");
        }
        rs.beforeFirst();
        Util.printRs(rs);

        rs.beforeFirst();
        if (rs.next()) {
            Kunde kunde = new Kunde(rs.getString(KUNDEUUID),rs.getString(KUNDENAME),rs.getString(KUNDEVORNAME));
            System.out.println(kunde);
            return kunde;
        }

        System.err.println("404 - Kunde not found");
        return null;
    }

    /**
     * @param kunde
     * @throws SQLException
     *
     * Funktionalitaet fuer Lehrer notwendig, jedoch nicht als Feature in der GUI implementiert
     * und daher nicht verwendet.
     */
    public static void updateKunde(Kunde kunde) throws SQLException {
        System.out.println("databaseCRUD.updateKunde");
        System.out.println(kunde);

        String id = kunde.getId();

        PreparedStatement pst = con.prepareStatement("SELECT * from Kunde WHERE uuid=?;");
        pst.setString(1, String.valueOf(id));
        ResultSet rs = pst.executeQuery();

        // Check if UUID does not exist in database
        if (!rs.first()) {
            throw new Error("Kunde does not exist");
        }
        rs.beforeFirst();
        Util.printRs(rs);

        rs.beforeFirst();
        if (rs.next()) {
            PreparedStatement update = con.prepareStatement("UPDATE Kunde SET name = ?, vorname = ? WHERE uuid=?;");
            update.setString(1,kunde.getName());
            update.setString(2,kunde.getVorname());
            update.setString(3, String.valueOf(id));
            ResultSet updateRs = update.executeQuery();

            ResultSet rs2 = pst.executeQuery();
            Util.printRs(rs2);
            return;
        }

        System.err.println("404 - Kunde not found");
    }

    /**
     * @param id
     * @throws SQLException
     *
     * Funktionalitaet fuer Lehrer notwendig, jedoch nicht als Feature in der GUI implementiert
     * und daher nicht verwendet.
     */
    public static void deleteKunde(String id) throws SQLException {
        System.out.println("databaseCRUD.deleteKunde");

        PreparedStatement pst = con.prepareStatement("SELECT uuid from Kunde WHERE uuid=?;");
        pst.setString(1, id);
        ResultSet rs = pst.executeQuery();

        // Check if UUID does not exist in database
        if (!rs.first()) {
            throw new Error("Kunde does not exist");

        }
        rs.beforeFirst();
        Util.printRs(rs);

        rs.beforeFirst();
        if (rs.next()) {
            PreparedStatement replaceKundenUUIDinAblesung = con.prepareStatement("UPDATE Ablesung SET kunde = null WHERE kunde=?;");
            replaceKundenUUIDinAblesung.setString(1, id);
            replaceKundenUUIDinAblesung.executeQuery();
            PreparedStatement delete = con.prepareStatement("DELETE FROM Kunde WHERE uuid=?;");
            delete.setString(1, id);
            ResultSet deleteRs = delete.executeQuery();
            Util.printRs(deleteRs);
            System.out.println("deleted Kunde " + id);

        }
    }

    public static void createAblesung(Ablesung ablesung) throws SQLException {
        System.out.println("databaseCRUD.createAblesung");
        String uuid = String.valueOf(ablesung.getId());

        PreparedStatement checkUUID = con.prepareStatement("SELECT * From Ablesung WHERE uuid=?;");
        checkUUID.setString(1, uuid);
        ResultSet rsUUID = checkUUID.executeQuery();
        Util.printRs(rsUUID);

        // Check if UUID already exists in database
        if (rsUUID.first())
            throw new Error("Ablesung already exists");

        Util.close(checkUUID);

        int boolNum = 0;
        if (ablesung.isNeuEingebaut())
            boolNum = 1;

        /**
         * Kunde is saved with its uuid, nothing else
         */
        PreparedStatement createAblesung = con.prepareStatement("INSERT INTO Ablesung (uuid, zaehlerNummer, ableseDatum, kommentar, neuEingebaut, zaehlerstand, kunde) VALUES (?,?,?,?,?,?,?);");
        createAblesung.setString(1,uuid);
        createAblesung.setString(2,ablesung.getZaehlerNummer());
        createAblesung.setString(3,String.valueOf(ablesung.getDatum()));
        createAblesung.setString(4,ablesung.getKommentar());
        createAblesung.setString(5, String.valueOf(boolNum));
        createAblesung.setString(6, String.valueOf(ablesung.getZaehlerstand()));
        createAblesung.setString(7, String.valueOf(ablesung.getKunde().getId()));

        ResultSet rsInsert = createAblesung.executeQuery();
        Util.close(rsInsert);
    }

    public static Ablesung readAblesung(UUID uuid) throws SQLException {
        System.out.println("databaseCRUD.readAblesung");

        PreparedStatement pst = con.prepareStatement("SELECT * from Ablesung WHERE uuid=?;");
        pst.setString(1, String.valueOf(uuid));
        ResultSet rs = pst.executeQuery();

        // Check if UUID does not exist in database
        if (!rs.first()) {
            throw new Error("Ablesung does not exist");
        }
        rs.beforeFirst();
        Util.printRs(rs);

        rs.beforeFirst();
        if (rs.next()) {
            Kunde kunde = readKunde(rs.getString(7));
            Ablesung ablesung = new Ablesung(UUID.fromString(rs.getString(1)),rs.getString(2),rs.getDate(3).toLocalDate(),kunde, rs.getString(4),rs.getBoolean(5), rs.getDouble(6));
            System.out.println(ablesung);
            return ablesung;
        }

        System.err.println("404 - Ablesung not found");
        return null;
    }

    public static void updateAblesung(Ablesung ablesung) throws SQLException {
        System.out.println("databaseCRUD.updateAblesung");
        System.out.println(ablesung);

        UUID uuid = ablesung.getId();

        PreparedStatement pst = con.prepareStatement("SELECT * from Ablesung WHERE uuid=?;");
        pst.setString(1, String.valueOf(uuid));
        ResultSet rs = pst.executeQuery();

        // Check if UUID does not exist in database
        if (!rs.first()) {
            throw new Error("Ablesung does not exist");
        }
        rs.beforeFirst();
        Util.printRs(rs);

        rs.beforeFirst();

        int boolNum = 0;
        if (ablesung.isNeuEingebaut())
            boolNum = 1;

        if (rs.next()) {
            PreparedStatement update = con.prepareStatement("UPDATE Ablesung SET zaehlerNummer = ?, ableseDatum = ?, kommentar = ?, neuEingebaut = ?, zaehlerstand = ?, kunde = ? WHERE uuid=?;");
            update.setString(1,ablesung.getZaehlerNummer());
            update.setString(2,String.valueOf(ablesung.getDatum()));
            update.setString(3,ablesung.getKommentar());
            update.setString(4, String.valueOf(boolNum));
            update.setString(5, String.valueOf(ablesung.getZaehlerstand()));
            update.setString(6, String.valueOf(ablesung.getKunde()));

            update.setString(7, String.valueOf(uuid));
            update.executeQuery();

            ResultSet rs2 = pst.executeQuery();
            Util.printRs(rs2);
            return;
        }

        System.err.println("404 - Kunde not found");
    }

    public static void deleteAblesung(UUID uuid) throws SQLException {
        System.out.println("databaseCRUD.deleteAblesung");

        PreparedStatement pst = con.prepareStatement("SELECT uuid from Ablesung WHERE uuid=?;");
        pst.setString(1, String.valueOf(uuid));
        ResultSet rs = pst.executeQuery();

        // Check if UUID does not exist in database
        if (!rs.first()) {
            throw new Error("Ablesung does not exist");

        }
        rs.beforeFirst();
        Util.printRs(rs);

        rs.beforeFirst();
        if (rs.next()) {
            PreparedStatement delete = con.prepareStatement("DELETE FROM Ablesung WHERE uuid=?;");
            delete.setString(1, String.valueOf(uuid));
            ResultSet deleteRs = delete.executeQuery();
            Util.printRs(deleteRs);
            System.out.println("deleted Ablesung " + uuid);
        }
    }


}
