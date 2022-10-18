package eu.bsinfo.GruppE;

import lombok.Getter;

import java.util.ArrayList;

public class DataHandler {
    @Getter
    public static ArrayList<MeasurementData> data = new ArrayList<>();

    public static void addData(MeasurementData d) {
        data.add(d);
        System.out.println(d);
    }

}
