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
     * конструктор BubbleTree()
     * инициализирует компоненты с параметрами по умолчанию
     */

    BubbleTree() {

        //icons
        Icon iconClear = new ImageIcon("D:\\Game Dev\\MyProjects\\Learn\\Learning\\QuickSolution\\Jp12\\src\\images\\clear.png");
        Icon iconSave = new ImageIcon("D:\\Game Dev\\MyProjects\\Learn\\Learning\\QuickSolution\\Jp12\\src\\images\\save.png");
        Icon iconLoad = new ImageIcon("D:\\Game Dev\\MyProjects\\Learn\\Learning\\QuickSolution\\Jp12\\src\\images\\load.png");
        Icon iconExit = new ImageIcon("D:\\Game Dev\\MyProjects\\Learn\\Learning\\QuickSolution\\Jp12\\src\\images\\exit.png");

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
     * метод actionSend(ActionEvent e)
     * осуществляет "общение" между пользователем и компьютером(условно)
     * используя текстовое поле textField, вводим запрашиваемую информацию компьютеру(размер массива, искомый элемент)
     * для активации при клике по кнопке "Send"  - запускаем процесс
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
     * метод actionSaveFile(ActionEvent e)
     * осуществляет потоковый вывод в файл BufferedWriter outFile =  new BufferedWriter(new FileWriter(fileName)),
     * формат ".bubtree"
     * используя JFileChooser для открытия файлового диалога fileChooser.showDialog(),
     * для активации при клике по кнопке "Save"  - запускаем процесс
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
     * метод actionLoadFile(ActionEvent e)
     * осуществляет потоковый вывод из файла BufferedReader reader =  new BufferedReader(new FileWriter(fileName)),
     * формат ".bubtree"
     * используя JFileChooser для открытия файлового диалога fileChooser.showDialog(),
     * для активации при клике по кнопке "Load"  - запускаем процесс, информация отобразится в текстовом поле(textOutputArea)
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
     * метод actionExit(ActionEvent e)
     * осуществляет выход с приложения, при клике кнопке "Exit"
     */

    public void actionExit(ActionEvent e)
    {
        System.exit(0);
    }

    /**
     * метод actionClearFields(ActionEvent e)
     * осуществляет очистку всех полей, при клике кнопке "Clear"
     */

    public void actionClearFields(ActionEvent e)
    {
        textField.setText("");
        textOutputArea.setText("");
    }

    /**
     * метод actionAppendText()
     * осуществляет добавление текста к существующиму
     */

    public void actionAppendText()
    {
        textOutputArea.setText("============================\n");
        textOutputArea.append("Input size of array(random generation):\n");
    }

    /**
     * метод  fillingArrayRandomy(int size, int element)
     * генерирует заполнение массива числами, принимает размер (size) и
     * элемент для бинарного поиска (element)
     * * Выводит результат генерации в текстовое поле textOutputArea
     */

    public void fillingArrayRandomy(int size, int element)
    {
        //Ждем от пользователя числа о размерности массива
        //заполняем его случайными числами

        Random random = new Random();
        int[] bArray = new int[size];

        //заполнение
        for (int i = 0; i < size; i++)
        {
            bArray[i] = random.nextInt(10) + 1;
            textOutputArea.append(bArray[i] + "  ");
        }
        bubbleSortProcess(bArray, element);
    }

    /**
     * метод  bubbleSortProcess(int[] array, int element)
     * выаолняет сортировку массива методом пузырька, принимает массив ([] array) и
     * элемент для бинарного поиска (element)
     * Выводит результат сортировки в текстовое поле textOutputArea
     */

    public void bubbleSortProcess(int[] array, int element)
    {
        boolean isSorted = false;
        int buffer;

        //запускаем процесс пузырьковой сортировки
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
     * метод  binarySearch(int element, int[] array)
     * осуществляет бинарный поиск числа в массиве,  принимает массив ([] array) и
     * элемент для бинарного поиска (element). В случае, если элемент не найден
     * выведет об этом сообщение:
     * "Element not found. Method binary's search end job after "
     *                     + comparisonCount + " comparisons
     *
     */

    public void binarySearch(int element, int[] array)
    {
        //Ждем от пользователя элемент для бинарного поиска
        //выполняем поиск

        int firstElement, lastElement;

        firstElement = 0;
        lastElement = array.length - 1;

        int position;
        int comparisonCount = 1;   // для подсчета количества сравнений

        // для начала найдем индекс среднего элемента массива
        position = (firstElement + lastElement) / 2;

        while ((array[position] != element) && (firstElement <= lastElement)) {
            comparisonCount++;
            if (array[position] > element) {  // если число заданного для поиска
                lastElement = position - 1; // уменьшаем позицию на 1.
            } else {
                firstElement = position + 1;    // иначе увеличиваем на 1
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
 * класс RunApplication - хранит в себе main, для запуска приложения
 * main - точка входа в класс и приложение
 */
class RunApplication {

    public static void main(String[] args) throws Exception {
        BubbleTree bubbleTree = new BubbleTree();
        bubbleTree.actionAppendText();

    }
}
