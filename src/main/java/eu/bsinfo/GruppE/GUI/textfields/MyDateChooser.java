package eu.bsinfo.GruppE.GUI.textfields;

import com.toedter.calendar.JDateChooser;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Calendar;

public class MyDateChooser extends JDateChooser {
    public MyDateChooser() {
        super();
        setDateFormatString("dd.MM.yyyy");
        getDateEditor().getUiComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    pressTab();
                    ensureDoubleDigitYear();
                }
            }
        });
    }

    public void ensureDoubleDigitYear() {
        Calendar c = getCalendar();
        if (c.get(Calendar.YEAR) >= 100)
            return;

        c.add(Calendar.YEAR, 2000);
        setCalendar(c);
    }

    public void pressTab() {
        Robot robot;
        try {
            robot = new Robot();
        } catch (AWTException ex) {
            throw new RuntimeException(ex);
        }
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_TAB);
    }

}
