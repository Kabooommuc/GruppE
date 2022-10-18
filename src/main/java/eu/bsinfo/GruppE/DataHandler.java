package eu.bsinfo.GruppE;

import lombok.Getter;

import java.util.ArrayList;

public class DataHandler {
    @Getter
    public static ArrayList<MeasurementData> data;

    public static void addData(MeasurementData d) {
        data.add(d);
    }

}
