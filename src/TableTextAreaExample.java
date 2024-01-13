import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import org.apache.batik.anim.dom.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.TranscodingHints;
import org.apache.batik.transcoder.image.ImageTranscoder;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.nitido.utils.toaster.Toaster;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import dorkbox.notify.Notify;
import dorkbox.notify.Position;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TableTextAreaExample extends JFrame {

    private JTable table;
    private RSyntaxTextArea textArea;
    private boolean functionalityEnabled = true;
    public TableTextAreaExample() {
        setTitle("Table and TextArea Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    BufferedImage bufferedImage;
    private void initComponents() {


        // Sample data for the table
        Object[][] data = {
                {"Row 1 Col 1", "Row 1 Col 2", "Row 1 Col 3"},
                {"Row 2 Col 1", "Row 2 Col 2", "Row 2 Col 3"},
                {"Row 3 Col 1", "Row 3 Col 2", "Row 3 Col 3"},
                {"Row 4 Col 1", "Row 4 Col 2", "Row 4 Col 3"},
                {"Row 5 Col 1", "Row 5 Col 2", "Row 5 Col 3"}
        };

        // Column names for the table
        Object[] columnNames = {"Column 1", "Column 2", "Column 3"};

        // Create a table model
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);

        // Create JTable with the model
        table = new JTable(tableModel);

        // Create RSyntaxTextArea
        textArea = new RSyntaxTextArea();
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); // Set your preferred syntax style

        // Add KeyListener to detect double shift key press
        textArea.addKeyListener(new KeyListener() {
            private boolean shiftPressed = false;
            private long lastKeyPressTime = 0;

            @Override
            public void keyTyped(KeyEvent e) {
                // Not needed for this example
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                    if (System.currentTimeMillis() - lastKeyPressTime < 400) {
                        // If the second shift key press occurs within 200 milliseconds, toggle functionality
                        functionalityEnabled = !functionalityEnabled;
                        try {
                            //showNotificationSystem("Functionality " + (functionalityEnabled ? "enabled" : "disabled"));
                            showNotification("Functionality " + (functionalityEnabled ? "enabled" : "disabled"));
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }

                        System.out.println("Functionality " + (functionalityEnabled ? "enabled" : "disabled"));
                    }
                    lastKeyPressTime = System.currentTimeMillis();
                    shiftPressed = true;
                }
                if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_D){
                    System.out.println("CTRL + D is pressed");
                    e.consume();
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                    shiftPressed = false;
                }
            }
        });
        // Create a split pane to hold the table and text area
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(table), new JScrollPane(textArea));
        splitPane.setDividerLocation(400); // Set initial divider location

        // Add ListSelectionListener to the table (conditionally based on the flag)
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (functionalityEnabled) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        // Set the content of RSyntaxTextArea based on the selected row and column 3
                        textArea.setText(table.getValueAt(selectedRow, 2).toString());
                    }
                }
            }
        });

        // Add DocumentListener to RSyntaxTextArea (conditionally based on the flag)
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (functionalityEnabled) {
                    updateTableCell();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (functionalityEnabled) {
                    updateTableCell();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if (functionalityEnabled) {
                    updateTableCell();
                }
            }
        });

        // Set layout and add components
        getContentPane().setLayout(new BorderLayout());
        JButton button = new JButton("deneme");
        button.setForeground(Color.decode("#11A2E5"));
        //button.setBackground(Color.decode("#9eb856"));
        //button.setBorder(new LineBorder(Color.decode("#37A9D0")));
        button.setBorder(new LineBorder(Color.decode("#1A9ED5")));

        getContentPane().add(button, BorderLayout.SOUTH);
        getContentPane().add(splitPane, BorderLayout.CENTER);

    }
    private void showNotificationSystem(String message) {
        NotificationThread notificationThread = new NotificationThread(message);
        notificationThread.start();
    }
    ImageIcon image;
    BufferedImage image1;
    private void showNotification(String message) {
        Toaster toaster = new Toaster();

        toaster.setBorderColor(Color.decode("#1A120B"));
        try {
            /*MyTranscoder transcoder = new MyTranscoder();
            TranscodingHints hints = new TranscodingHints();
            hints.put(ImageTranscoder.KEY_WIDTH, 96);
            hints.put(ImageTranscoder.KEY_HEIGHT, 96);
            transcoder.setTranscodingHints(hints);
            transcoder.transcode(new TranscoderInput("C:\\projeler\\Iavi\\TableRSyntaxAreaSynchronisation/envelope-regular.svg"), null);
            image1 = transcoder.getImage();*/
            bufferedImage = ImageIO.read(new File("C:\\projeler\\Iavi\\TableRSyntaxAreaSynchronisation\\envelope-50.png"));
            image = new ImageIcon(bufferedImage);
            toaster.setMessageColor(Color.decode("#ADC767"));
            toaster.setToasterColor(Color.decode("#1B1B1B"));

        } catch (Exception ex) {
            ex.printStackTrace();
        }


        toaster.setToasterHeight(100);
        toaster.setToasterWidth(300);
        JLabel label = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                super.paintComponent(g);
            }
        };
        label.setIcon(new ImageIcon("C:\\projeler\\Iavi\\TableRSyntaxAreaSynchronisation\\envelope-50.png"));
        toaster.showToaster(label, message);
        //toaster.showToaster(new ImageIcon("C:\\projeler\\Iavi\\TableRSyntaxAreaSynchronisation\\envelope-50.png"), message);
        toaster.setDisplayTime(2000);
    }

    private static ImageIcon createImageIcon(String imagePath, int width, int height) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_REPLICATE);
        return new ImageIcon(image);
    }
    private void showNotificationJDialog(String message) {
        JOptionPane optionPane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = optionPane.createDialog("Notification");

        // Set a timer to close the dialog after 2 seconds
        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        timer.setRepeats(false);
        timer.start();

        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
    }
    private void updateTableCell() {
        int selectedRow = table.getSelectedRow();
        int columnToUpdate = 2; // Column 3 (zero-based index)
        if (selectedRow != -1) {
            // Update the value in the table model based on the content of RSyntaxTextArea
            String s = textArea.getText();
            //boolean b = s.contains("yukselis");
            //System.out.println(b);
            table.setValueAt(textArea.getText(), selectedRow, columnToUpdate);
        }
    }
}
