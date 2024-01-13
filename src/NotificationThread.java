import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NotificationThread extends Thread {
    private final String message;
    SystemTray tray;
    public NotificationThread(String message) {
        this.message = message;
    }

    @Override
    public void run() {
        SwingUtilities.invokeLater(() -> {
            if (SystemTray.isSupported()) {
                tray = SystemTray.getSystemTray();

                Image image = Toolkit.getDefaultToolkit().getImage("icon.png"); // Replace with your icon file

                TrayIcon trayIcon = new TrayIcon(image, "Notification");
                trayIcon.setImageAutoSize(true);

                try {
                    tray.add(trayIcon);
                    trayIcon.displayMessage("Notification", message, TrayIcon.MessageType.INFO);

                    // Wait for 2 seconds before removing the notification
                    Thread.sleep(1);

                    // Remove the tray icon on the event dispatch thread
                    SwingUtilities.invokeLater(() -> {
                        try {
                            SystemTray.getSystemTray().remove(trayIcon);
                            tray.remove(trayIcon);
                            //tray = null;
                            System.out.println("removed");
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    });
                } catch (AWTException | InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                // System tray is not supported
                JOptionPane.showMessageDialog(null, message, "Notification", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
}

