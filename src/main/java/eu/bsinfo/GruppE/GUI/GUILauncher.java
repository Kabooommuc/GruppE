package eu.bsinfo.GruppE.GUI;

public class GUILauncher {

    public static GUI gui;
    public static void main(String[] args) {
        gui = new GUI();
        try {
            DataHandler.loadData();
        }  catch (Exception e){
            e.printStackTrace();
        }
    }

}
