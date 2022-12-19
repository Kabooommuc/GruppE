package eu.bsinfo.GruppE.GUI;

import javax.swing.*;

public class InputChecker {

    public static String checkInputFields(GUI gui) {

        JTextField[] fieldsToCheck = gui.getInputList();
        String[] inputFieldNames = gui.getINPUT_FIELD_NAMES();


        int emptyFieldIndex = checkForEmpty(fieldsToCheck);
        if(emptyFieldIndex!=-1) {
            System.out.println(fieldsToCheck[emptyFieldIndex].getName());
            fieldsToCheck[emptyFieldIndex].setText("FEHLER");
            return GUI.INFO_TAG + "Das Feld " + inputFieldNames[emptyFieldIndex].trim() + " muss ausgefüllt sein.";
        }

        String formatProblems = checkFormat(fieldsToCheck,inputFieldNames);
        if(!formatProblems.equals("")) {
            return GUI.ERROR_TAG + formatProblems;
        }









        return "";



    }

    private static String checkFormat(JTextField[] jTextFields, String[] inputFieldNames) {

        for (int i = 0; i < jTextFields.length;i++) {

            switch(i) {
                //customerId and counterId
                case 0, 6 -> {
                    try {
                        Integer.parseInt(jTextFields[i].getText());
                    } catch (NumberFormatException n) {
                        return inputFieldNames[i].trim() + " muss eine ganze Zahl sein.";
                    }
                }

                //powerCurrentInput and householdCurrentInput
                case 3,4 -> {
                    try {
                        Double.parseDouble(jTextFields[i].getText());
                    } catch (NumberFormatException n) {
                        return inputFieldNames[i].trim() + " muss eine Kommazahl sein (z.B. 1.5)";
                    }
                }
            }









        }

        try {
            Integer.parseInt(jTextFields[0].getText());
        } catch(NumberFormatException n) {
            return "KundeNr muss eine ganze Zahl sein.";
        }







        for (int i = 0; i<jTextFields.length-1; i++) {
            switch(i) {

            }
        }
        return " ";
    }

    private static int checkForEmpty(JTextField[] jTextFields) {
        for (int i = 0; i<jTextFields.length-1; i++) {
            if(jTextFields[i].getText().equals("")) {
                return i;
            }
        }
        return -1;
    }

}
