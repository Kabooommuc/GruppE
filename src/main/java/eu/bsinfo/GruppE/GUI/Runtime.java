package eu.bsinfo.GruppE.GUI;

public class Runtime {

    public static GUI gui;
    public static void main(String[] args) {
        gui = new GUI();
        DataHandler.loadData();
    }

}
