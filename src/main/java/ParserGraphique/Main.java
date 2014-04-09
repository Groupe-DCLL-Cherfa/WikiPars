package ParserGraphique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import dcll.Cherfa.WikiPars.ErreurSyntax;
import dcll.Cherfa.WikiPars.PasDEntrer;
import dcll.Cherfa.WikiPars.WikiReader;





/**
 * @author Cherfa
 */
public final class Main {
    /**
     * the default file test.
     */
    private static final String FILE_TEST = "File_Test/test.txt";
    /**
     * Default constructor.
     */
    private Main() { }
    /**
     * @param args the args.
     */
    public static void main(final String[] args) {
    	try {
            for (UIManager.LookAndFeelInfo info : javax.swing.UIManager
                    .getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        }
        final WikiReader parser = new WikiReader();
        final JFrame form = new JFrame();
        form.setUndecorated(true);
        final JButton bCharger, bOuvrir;
        bOuvrir = new JButton();
        bCharger = new JButton();
        bCharger.setPreferredSize(new Dimension(200,200));
        bOuvrir.setPreferredSize(new Dimension(200,200));
        bOuvrir.setBackground(Color.GREEN);
        bCharger.setBackground(Color.GREEN);
        bOuvrir.setFont(new java.awt.Font("Comic Sans MS", 0, 11));
        bCharger.setFont(new java.awt.Font("Comic Sans MS", 0, 11));
        bOuvrir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	bOuvrir.setBackground(Color.red);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	bOuvrir.setBackground(Color.GREEN);
            }
        });
        
        bCharger.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	bCharger.setBackground(Color.red);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	bCharger.setBackground(Color.GREEN);
            }
        });
        form.setLocationRelativeTo(null);
        form.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        form.setTitle("*****Wikiversity Quiz******");
       
        
        bOuvrir.setText("<html><h2><strong>Charger un fichier</strong></h2></html>");
        bOuvrir.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent evt) {
                parser.setInput(getFileContent(getFilePath()));
                form.setVisible(false);
                showQuiz(parser);
            }
        });
        bCharger.setText("<html><h2><strong>Fichier par défaut</strong></h2></html>");
        bCharger.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent evt) {
                parser.setInput(getFileContent(FILE_TEST));
                form.setVisible(false);
                showQuiz(parser);
            }
        });
        BorderLayout layout = new BorderLayout();
        form.getContentPane().setLayout(layout);
        form.add(bOuvrir, BorderLayout.EAST);
        form.add(bCharger, BorderLayout.WEST);
        form.setResizable(false);
        form.setSize(form.getPreferredSize());
        form.pack();
        form.setVisible(true);
    }
    /**
     * @param parser the wikimedia parser.
     */
    public static void showQuiz(final WikiReader parser) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Window(parser.parse()).setVisible(true);
                } catch (PasDEntrer e) {
                    showError("Parser", e.getMessage());
                } catch (ErreurSyntax e) {
                    showError("Parser", e.getMessage());
                }
            }
        });
    }
    /**
     * @param title the dialog title
     * @param message the message to show
     */
    public static void showError(final String title, final String message) {
        JOptionPane.showMessageDialog(null, message, title,
                JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }

    /**
     * Method to set the file to use for the test, a default is used if it is
     * not specified.
     * @return String, the file content
     */
    private static String getFilePath() {
        JFileChooser chooser = new JFileChooser();

        int res = chooser.showOpenDialog(null);
        if (res == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            return file.getPath();
        } else {
            return FILE_TEST;
        }
    }
    /**
     * @param fileName the file name.
     * @return String
     */
    private static String getFileContent(final String fileName) {
        BufferedReader br = null;
        String line, output = "";
        try {
            try {
                br = new BufferedReader(new InputStreamReader(
                        new FileInputStream(
                        new File(fileName)),
                        "UTF-8"));
            } catch (UnsupportedEncodingException e1) {
                showError("Lecture Fichier test", e1.getMessage());
            }
            try {
                while ((line = br.readLine()) != null) {
                	line=line.concat("\n");
                	output = output.concat(line);
                }
            } catch (IOException e) {
                showError("Lecture Fichier test", e.getMessage());
            }
        } catch (FileNotFoundException e1) {
            showError("Chargement Fichier test", e1.getMessage());
        }
        try {
            br.close();
        } catch (IOException e) {
            showError("Fermeture Fichier test", e.getMessage());
        }
        return output;
    }
}
