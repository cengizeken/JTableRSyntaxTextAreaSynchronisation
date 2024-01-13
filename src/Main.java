import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.intellijthemes.FlatArcDarkOrangeIJTheme;
import com.formdev.flatlaf.intellijthemes.*;
import com.formdev.flatlaf.FlatLightLaf;
import com.nitido.utils.toaster.Toaster;

import javax.swing.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                FlatDarkLaf.setup();
                new TableTextAreaExample();
                Toaster toaster = new Toaster();

            }
        });
    }
}