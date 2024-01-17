import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

/** BubbleTree Application
 * @author Ilya Borisov
 * @version java 8.0
 */

public class BubbleTree
        extends JFrame{

    private Container container;
    private JButton btnSend;
    private JButton btnSave;
    private JButton btnLoad;
    private JButton btnExit;
    private JButton btnClear;
    private JTextArea textOutputArea;
    private JTextField textField;

    /**
     * constructor BubbleTree()
     * initializes components with default parameters
     */

    BubbleTree() {

        //icons
        Icon iconClear = new ImageIcon("..\images\clear.png");
        Icon iconSave = new ImageIcon("..\images\save.png");
        Icon iconLoad = new ImageIcon("..\images\load.png");
        Icon iconExit = new ImageIcon("..\images\exit.png");

        //mainframe
        JFrame frame = new JFrame();
        frame.setTitle("BubbleTree");
        frame.setBounds(300, 90, 450, 560);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setResizable(true);

        container = getContentPane();
        container.setLayout(null);


        //button send
        btnSend = new JButton("Send");
        btnSend.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        btnSend.setSize(80, 20);
        btnSend.setLocation(220, 500);
        btnSend.setToolTipText("Send button, to send messages");
        btnSend.addActionListener(this::actionSend);

        container.add(btnSend);

        //button saveFile
        btnSave = new JButton(iconSave);
        //btnSave.setFont(new Font("Times New Roman", Font.PLAIN, 8));
        btnSave.setSize(20, 20);
        btnSave.setLocation(320, 500);
        btnSave.setToolTipText("Save button, for save to file from text console");
        btnSave.addActionListener(this::actionSaveFile);

        container.add(btnSave);

        //button loadFile
        btnLoad = new JButton(iconLoad);
        //btnLoad.setFont(new Font("Times New Roman", Font.PLAIN, 8));
        btnLoad.setSize(20, 20);
        btnLoad.setLocation(350, 500);
        btnLoad.setToolTipText("Load button, for load information from file to text console");
        btnLoad.addActionListener(this::actionLoadFile);

        container.add(btnLoad);

        //button exit
        btnExit = new JButton(iconExit);
        //btnExit.setFont(new Font("Times New Roman", Font.PLAIN, 8));
        btnExit.setSize(20, 20);
        btnExit.setLocation(410, 500);
        btnExit.setToolTipText("Exit button, for exit from application");
        btnExit.addActionListener(this::actionExit);

        container.add(btnExit);


        //button clear
        btnClear = new JButton(iconClear);
        //btnClear.setFont(new Font("Times New Roman", Font.PLAIN, 8));
        btnClear.setSize(20, 20);
        btnClear.setLocation(380, 500);
        btnClear.setToolTipText("Clear button, for the cleaning text fields");
        btnClear.addActionListener(this::actionClearFields);

        container.add(btnClear);

        //textField
        textField = new JTextField();
        textField.setEditable(true);
        textField.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        textField.setSize(200, 20);
        textField.setLocation(1, 500);

        container.add(textField);

        //TextArea
        textOutputArea = new JTextArea(10, getWidth());
        textOutputArea.setEditable(false);
        textOutputArea.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        textOutputArea.setSize(440, 500);
        textOutputArea.setLocation(0, 0);
        textOutputArea.setLineWrap(true);
        textOutputArea.setWrapStyleWord(true);

        container.add(textOutputArea);

        frame.add(container);
        //frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    FileNameExtensionFilter filter = new FileNameExtensionFilter("*.bubtree", "*.*");

    /**
     * method actionSend(ActionEvent e)
     * performs "communication" between the user and the computer (conditionally)
     * using the textField textfield, enter the requested information to the computer (array size, desired element)
     * to activate when clicking on the "Send" button, start the process
     */

    public void actionSend(ActionEvent e) {
        int size,element;

        textOutputArea.append(textField.getText() + "\n");
        size = Integer.parseInt(textField.getText());

        textOutputArea.append("Input element for binary search: \n");
        textOutputArea.append(textField.getText() + "\n");
        element = Integer.parseInt(textField.getText());

        fillingArrayRandomy(size,element);

        textField.setText("");
    }

    /**
     * method actionSaveFile(ActionEvent e)
     * performs streaming output to a file BufferedWriter outFile =  new BufferedWriter(new FileWriter(fileName)),
     * format ".bubtree"
     * using JFileChooser to open a file dialog fileChooser.showDialog(),
     * to activate it by clicking on the "Save" button, we start the process
     */

    public void actionSaveFile(ActionEvent e) {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(filter);
        int temp = fileChooser.showDialog(null, "Save file");
        if (e.getSource() == btnSave && temp == JFileChooser.APPROVE_OPTION)
        {
            File fileName = new File(fileChooser.getSelectedFile() + ".bubtree");
            BufferedWriter outFile = null;

            try {
                outFile = new BufferedWriter(new FileWriter(fileName));
                textOutputArea.write(outFile);
            }
            catch (IOException exceptions)
            {
                System.err.println("Error: " + exceptions);
                exceptions.printStackTrace();
            }
            finally {
                if (outFile != null)
                {
                    try {
                        outFile.close();
                    }
                    catch (IOException ioException)
                    {
                        System.err.println("Error: " + ioException);
                        ioException.printStackTrace();
                    }
                }
            }
        }

    }

    /**
     * method actionLoadFile(ActionEvent e)
     * performs streaming output from a file BufferedReader reader =  new BufferedReader(new FileWriter(fileName)),
     * format ".bubtree"
     * using JFileChooser to open a file dialog fileChooser.showDialog(),
     * to activate when clicking on the "Load" button, we start the process, the information will be displayed in the text field(textOutputArea)
     */

    public void actionLoadFile(ActionEvent e) {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(filter);
        int temp = fileChooser.showDialog(null, "Load file");
        if (e.getSource() == btnLoad && temp == JFileChooser.APPROVE_OPTION) {
            File fileName = fileChooser.getSelectedFile();
            BufferedReader reader;

            try {
                reader = new BufferedReader(new FileReader(fileName));
                String line;
                textOutputArea.setText("");
                while ((line = reader.readLine()) != null)
                {
                    textOutputArea.append(line + "\n");
                }
            } catch (IOException exceptions) {
                System.err.println("Error: " + exceptions);
                exceptions.printStackTrace();
                }
        }
    }

    /**
     * method actionExit(ActionEvent e)
     * exits the application by clicking the "Exit" button
     */

    public void actionExit(ActionEvent e)
    {
        System.exit(0);
    }

    /**
     * method actionClearFields(ActionEvent e)
     * clears all fields by clicking the "Clear" button
     */

    public void actionClearFields(ActionEvent e)
    {
        textField.setText("");
        textOutputArea.setText("");
    }

    /**
     * method actionAppendText()
     * adds text to an existing one
     */

    public void actionAppendText()
    {
        textOutputArea.setText("============================\n");
        textOutputArea.append("Input size of array(random generation):\n");
    }

    /**
     * method  fillingArrayRandomy(int size, int element)
     * generates the filling of the array with numbers, takes the size (size) и
     * an element for binary search (element)
     * Outputs the result of the generation in a text field textOutputArea
     */

    public void fillingArrayRandomy(int size, int element)
    {
        // We are waiting for a number from the user about the dimension of the array
        // we fill it with random numbers

        Random random = new Random();
        int[] bArray = new int[size];

        // Filling
        for (int i = 0; i < size; i++)
        {
            bArray[i] = random.nextInt(10) + 1;
            textOutputArea.append(bArray[i] + "  ");
        }
        bubbleSortProcess(bArray, element);
    }

    /**
     * method  bubbleSortProcess(int[] array, int element)
     * performs sorting of the bubble's method array, accepts an array ([] array) and
     * an element for binary search (element)
     * Displays the sorting result in a text field textOutputArea
     */

    public void bubbleSortProcess(int[] array, int element)
    {
        boolean isSorted = false;
        int buffer;

        // Starting the bubble sorting process
        while (!isSorted)
        {
            isSorted = true;
            for(int i = 0; i < array.length - 1; i++)
            {
                if(array[i] > array[i+1])
                {
                    isSorted = false;
                    buffer = array[i];
                    array[i] = array[i+1];
                    array[i+1] = buffer;
                }
            }
        }
        textOutputArea.append(Arrays.toString(array) + "\n");

        binarySearch(element, array);

        textField.setText("");
    }

    /**
     * method  binarySearch(int element, int[] array)
     * performs a binary search for a number in an array, accepts an array([] array) and
     * an element for binary search (element). In case the element is not found
     * displays a message about this:
     * "Element not found. Method binary's search end job after "
     *                     + comparisonCount + " comparisons
     *
     */

    public void binarySearch(int element, int[] array)
    {
        // We are waiting for the user to provide an element for binary search
        // We are performing a search

        int firstElement, lastElement;

        firstElement = 0;
        lastElement = array.length - 1;

        int position;
        int comparisonCount = 1;   // To count the number of comparisons

        // First, let's find the index of the middle element of the array
        position = (firstElement + lastElement) / 2;

        while ((array[position] != element) && (firstElement <= lastElement)) {
            comparisonCount++;
            if (array[position] > element) {  // If the number is set for the search
                lastElement = position - 1; // Reduce the position by 1.
            } else {
                firstElement = position + 1;    // Otherwise, we increase it by 1
            }
            position = (firstElement + lastElement) / 2;
        }
        if (firstElement <= lastElement) {
            textOutputArea.append(element + " is " + ++position + " element in array\n");
            textOutputArea.append("Method binary's search found after " + comparisonCount +
                    " comparisons\n");
        } else {
            textOutputArea.append("Element not found. Method binary's search end job after "
                    + comparisonCount + " comparisons\n");
        }
    }
}

/**
 * class RunApplication - stores the main in itself to run the application
 * main - the entry point to the classroom and the application
 */
class RunApplication {

    public static void main(String[] args) throws Exception {
        BubbleTree bubbleTree = new BubbleTree();
        bubbleTree.actionAppendText();

    }
}
