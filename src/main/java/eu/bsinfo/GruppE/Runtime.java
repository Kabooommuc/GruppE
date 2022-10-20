package eu.bsinfo.GruppE;

public class Runtime {

    public static GUI gui;
    public static void main(String[] args) {
        DataHandler.loadData();
        gui = new GUI();
    }

}
