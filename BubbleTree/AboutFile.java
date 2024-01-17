import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;

/**
 * @author Ilya Borisov
 * @version java 8.0
 */


/**
 * class AboutFile - stores the constructor in itself AboutFile() and method actionPerformed(ActionEvent e)
 * it also stores the components of the button(JButton), text field(JTextArea) and the container(Container)
 */
public class AboutFile
        extends JFrame
        implements ActionListener {

    private Container container;
    private JButton btnOpenPathToFile;
    private JTextArea textOutputArea;

    /**
     * constructor AboutFile()
     * initializes components with default parameters
     */

    // constructor, to initialize the components
    // with default values.
    AboutFile()
    {
        //mainframe
        JFrame frame = new JFrame();
        frame.setTitle("File details");
        frame.setBounds(300, 90, 600, 570);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setResizable(true);

        container = getContentPane();
        container.setLayout(null);

        //button
        btnOpenPathToFile = new JButton("Get info about file");
        btnOpenPathToFile.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        btnOpenPathToFile.setSize(600, 50);
        btnOpenPathToFile.setLocation(0, 500);
        btnOpenPathToFile.addActionListener(this);

        container.add(btnOpenPathToFile);

        //TextArea
        textOutputArea = new JTextArea(10, getWidth());
        textOutputArea.setEditable(false);
        textOutputArea.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        textOutputArea.setSize(600, 500);
        textOutputArea.setLocation(0, 0);
        textOutputArea.setLineWrap(true);
        textOutputArea.setWrapStyleWord(true);

        container.add(textOutputArea);

        frame.add(container);
       // frame.pack ();
        frame.setLocationRelativeTo ( null );
        frame.setVisible ( true );
    }

    /**
     * method actionPerformed(ActionEvent e)
     * implements the main functionality of the program AboutDetails
     * using JFileChooser for file dialog and e.getSource() == btnOpenPathToFile,
     * to activate by clicking on the button - we start the process during which, by the user
     * the file is selected, all the details of the file are written out in the text field(textOutputArea)
     */

    @Override
    public void actionPerformed(ActionEvent e) {

        JFileChooser fileChooser = new JFileChooser();
        int temp = fileChooser.showDialog(null, "open file");
        if (e.getSource() == btnOpenPathToFile && temp == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            File dir = fileChooser.getCurrentDirectory();
            if (file.exists()) {
                textOutputArea.setText("============================\n");
                textOutputArea.append(file.getName() + "file exists!\n");
                textOutputArea.append("============================\n");
            } if (file.isFile()) {
                textOutputArea.setText("============================\n");
                textOutputArea.append("Path to file:\t" + file.getPath() + "\n");
                textOutputArea.append("Absolute path to file:\t" + file.getAbsolutePath()+ "\n");
                textOutputArea.append("Size of file:\t" + file.length()+ "\n");
                textOutputArea.append("Last modification:\t" + new Date(file.lastModified())+ "\n");
                textOutputArea.append("File available for read:\t" + file.canRead()+ "\n");
                textOutputArea.append("File available for write:\t" + file.canWrite()+ "\n");
                textOutputArea.append("File deleted:\t" + file.delete()+ "\n");
                textOutputArea.append("==============================\n");

            } else {
                textOutputArea.setText("============================\n");
                textOutputArea.append("file" + file.getName() + " not exists!");
                textOutputArea.append("============================\n");
            }

        }
    }
}

/**
 * class RunApplication - stores the main in itself to run the application
 * main - the entry point to the classroom and the application
 */
class RunApplication{

    public static void main(String[] args) throws Exception
    {

        AboutFile aboutFile = new AboutFile();

    }
}