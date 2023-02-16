package eu.bsinfo.GruppE.GUI;

import static java.lang.System.err;

public class Runtime {

    public static GUI gui;
    public static void main(String[] args) {
        gui = new GUI();
        try {
            DataHandler.loadData();
        }  catch (Exception e){
            err.println(e);
        }
    }

}
