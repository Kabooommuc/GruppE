package eu.bsinfo.GruppE.Server.ressources;

import eu.bsinfo.GruppE.Server.models.Ablesung;
import eu.bsinfo.GruppE.Server.models.Kunde;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

@Path("ablesungenVorZweiJahren")
public class AblesungVonVor2JahrenRessource {
    public static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd.MM.yy");

    // create an empty Ablesungen list, where we can put our Ablesungen entities to.
    public static ArrayList<Ablesung> ablesungenResult = new ArrayList<>();
    private static final String DATA_NOT_FOUND = "No Data found!";
    private static final String DATA_COMPILED = "Data Successfully Compiled";
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTwoYearOldAblesung() {
        final String resourceName = "testdata.csv";     //dataHandler csv datei
        InputStream inStream = null;
        try {
            inStream = getClass().getClassLoader().getResourceAsStream(resourceName);
            assert inStream != null;
            Scanner sc = new Scanner(inStream);     //scannt den speicher
            sc.useDelimiter(";");

            LocalDate two_year_fn = LocalDate.of(LocalDate.now().getYear() - 2, 1, 1);

            // read through file, line by line, ignore first line (headers)
            int cnt = 0;

            if(sc.hasNextLine()){
                // First time will skip header, all other times it will simply advance to the next line
                cnt++;
                sc.nextLine();
                cnt++;
            }

            while (sc.hasNextLine()&&cnt<=1079) {
                if(cnt>2){
                    sc.nextLine();
                }

                // convert individual column data to the right data types
                int kundeNummer = sc.nextInt();
                String datumAsString = sc.next();
                LocalDate datum = LocalDate.parse(datumAsString, DTF);
                String zaehlerart = sc.next();
                String zaehlerNummer = sc.next();
                int neuEingebautAsInt = sc.nextInt(); // needs to be either 0 or 1, we don't check it though
                Boolean neuEingebaut = neuEingebautAsInt == 1;
                int zaehlerstand = sc.nextInt();
                String kommentar = "";

                if(datum.isAfter(two_year_fn)) {        //if date is after 01.01.2019, program continues.
                    // create an Ablesungen object with the above values
                    Ablesung a = new Ablesung(

                            zaehlerNummer,
                            datum,
                            new Kunde("fake", "name"),
                            kommentar,
                            neuEingebaut,
                            zaehlerstand
                    );
                    // We ignore the last column (Kommentar), since it is empty and scanner doesn't really get that

                    // add the newly created object to the List
                    ablesungenResult.add(a);
            //        System.out.println("added " + cnt);
            //    }else{
            //        System.out.println("ignored " + cnt);
                }
                cnt++;
            }
            System.out.println("after while");      //can be removed after testing

            if(ablesungenResult.size() == 0){       //if array size is 0, prints "Data not found"
                return Response.status(Response.Status.NOT_FOUND).entity(DATA_NOT_FOUND).build();
            }else{
                System.out.println(DATA_COMPILED);      //otherwise, prints message and builds
                return Response.status(Response.Status.OK).entity(ablesungenResult).build();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                    System.out.println("Could not find data");
                }
            }
        }
        return null;
    }
}
