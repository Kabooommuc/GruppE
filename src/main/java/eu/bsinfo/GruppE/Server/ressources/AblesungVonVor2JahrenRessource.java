package eu.bsinfo.GruppE.Server.ressources;

import eu.bsinfo.GruppE.Server.models.Ablesung;
import eu.bsinfo.GruppE.Server.models.Kunde;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Path("ablesungenVorZweiJahrenHeute")
public class AblesungVonVor2JahrenRessource {
    public static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTwoYearOldAblesung() {
        final String resourceName = "testdata.csv";
        InputStream inStream = null;
        try {
            inStream = getClass().getClassLoader().getResourceAsStream(resourceName);
            Scanner sc = new Scanner(inStream);
            sc.useDelimiter(";");

            // create an empty Ablesungen list, where we can put our Ablesungen entities to.
            ArrayList<Ablesung> ablesungenCsv = new ArrayList<>();

            // read through file, line by line, ignore first line (headers)
            int cnt = 0;

            LocalDate current_date = LocalDate.now();       //find out current year
            LocalDate two_years_ago = current_date.minusYears(2);            //subtract two years
            int get_year = two_years_ago.getYear();         //variable get_year = two years ago
            LocalDate begin_of_two_year = LocalDate.of(get_year, 1, 1);       //create variable begin_of_2_years
            String SaveAsString = begin_of_two_year + " I fucking hate you"; //convert localDate to string because I cant format localDate with localDate
            LocalDate two_year_fm = LocalDate.parse(SaveAsString, DTF);     // above statement = true if this works

            while (sc.hasNextLine()) {
                cnt++;
                // First time will skip header, all other times it will simply advance to the next line
                sc.nextLine();

                // convert individual column data to the right data types
                int kundeNummer = sc.nextInt();
                String datumAsString = sc.next();
                LocalDate datum = LocalDate.parse(datumAsString, DTF);
                String zaehlerart = sc.next();
                String zaehlerNummer = sc.next();
                int neuEingebautAsInt = sc.nextInt(); // needs to be either 0 or 1, we don't check it though
                Boolean neuEingebaut = neuEingebautAsInt == 1 ? true : false;
                int zaehlerstand = sc.nextInt();
                String kommentar = "";

                if(datum.isAfter(two_year_fm)) {
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
                    ablesungenCsv.add(a);
                    System.out.println("added " + cnt);
                }else{
                    System.out.println("ignored" + cnt);
                }
            }
            System.out.println("after while");

            /* filter the list for the dates (last 2 years)
            ArrayList<Ablesung> ablesungenCsvFiltered = ablesungenCsv.stream().filter((ablesung) -> {
                //
                // here must be code that returns a boolean.
                // everything that is true, remains in the collection, everything else will be discarded
            }).collect;
             */

            // error handling -> return 500 on real server side error

            // return empty list, if no data could be found

            return Response.status(Response.Status.OK).entity(ablesungenCsv).build();

        } catch (Exception e) {
            System.err.println(e);
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException ioe) {
                    // todo. i.e. log, ignore, whatever
                }
            }
        }
        return null;
    }
}
