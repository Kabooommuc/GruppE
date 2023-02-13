package eu.bsinfo.GruppE.Server.Database;

import eu.bsinfo.GruppE.Server.models.Kunde;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.UUID;

public class tryConnection {
    static final UUID id = UUID.fromString("fa241caf-b6d7-46bb-bbd7-43044f9997c2");
    static final Kunde kunde = new Kunde("Testing","Tester");

    static final Connection con = Util.getConnection("gm3");

    public static void main(String[] args) {
       // basicConnection();
        try {
            databaseCRUD.createKunde(kunde);
            Kunde ausgelesenerKunde = databaseCRUD.readKunde(kunde.getId());
            System.out.println(ausgelesenerKunde);

            kunde.setName("schebesta");
            kunde.setVorname("felix");
            databaseCRUD.updateKunde(kunde);

            databaseCRUD.deleteKunde(kunde.getId());
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    private static void basicConnection() {
        try {
            System.out.println("... connected");
            final DatabaseMetaData meta = con.getMetaData();
            System.out.format(
                    "Driver : %s %s.%s\n", meta.getDriverName(), meta.
                            getDriverMajorVersion(),
                    meta.getDriverMinorVersion()
            );
            System.out.format("DB     : %s %s.%s (%s)\n", meta.
                            getDatabaseProductName(),
                    meta.getDatabaseMajorVersion(),
                    meta.getDatabaseMinorVersion(),
                    meta.getDatabaseProductVersion());
            Util.close(con);
        } catch (final SQLException e) {
            System.out.println("Fehler: " + e.getMessage());
        }
    }
}
