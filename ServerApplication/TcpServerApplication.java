package lb3_tcp_application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;


/** Tcp Server Application
 * @author Ilya Borisov
 * @version java 8.0
 */

public class TcpServerApplication
        extends JFrame {

    private Container container;
    private JButton btnExit;
    private JButton btnClear;
    public JTextArea textServerArea;

    /**
     * метод TcpServerApplication()
     * инициализирует компоненты с параметрами по умолчанию
     */

    public void TcpServerApplicationInitialization() {

        //icons
        Icon iconClear = new ImageIcon("D:\\Game Dev\\MyProjects\\Learn\\Learning\\QuickSolution\\Jp12\\src\\images\\clear.png");
        Icon iconExit = new ImageIcon("D:\\Game Dev\\MyProjects\\Learn\\Learning\\QuickSolution\\Jp12\\src\\images\\exit.png");

        //mainframe
        JFrame frame = new JFrame();
        frame.setTitle("Sever Application");
        frame.setBounds(300, 90, 450, 560);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setResizable(true);

        container = getContentPane();
        container.setLayout(null);


        //button exit
        btnExit = new JButton(iconExit);
        //btnExit.setFont(new Font("Times New Roman", Font.PLAIN, 8));
        btnExit.setSize(20, 20);
        btnExit.setLocation(450, 560);
        btnExit.setToolTipText("Exit button, for exit from application");
        btnExit.addActionListener(this::actionExit);

        container.add(btnExit);


        //button clear
        btnClear = new JButton(iconClear);
        //btnClear.setFont(new Font("Times New Roman", Font.PLAIN, 8));
        btnClear.setSize(20, 20);
        btnClear.setLocation(450, 540);
        btnClear.setToolTipText("Clear button, for the cleaning text fields");
        btnClear.addActionListener(this::actionClearFields);

        container.add(btnClear);


        //TextArea
        textServerArea = new JTextArea(10, getWidth());
        textServerArea.setEditable(false);
        textServerArea.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        textServerArea.setSize(430, 560);
        textServerArea.setLocation(0, 0);
        textServerArea.setLineWrap(true);
        textServerArea.setWrapStyleWord(true);

        container.add(textServerArea);

        frame.add(container);
        //frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    /**
     * метод actionExit()
     * @param e
     * осуществляет выход с приложения, при клике кнопке "Exit"
     */

    public void actionExit(ActionEvent e) {
        System.exit(0);
    }

    /**
     * метод actionClearFields()
     * @param e
     * осуществляет очистку всех полей, при клике кнопке "Clear"
     */

    public void actionClearFields(ActionEvent e) {
        textServerArea.setText("");
    }

    /**
     * метод actionAppendText()
     * осуществляет добавление текста к существующиму
     */

    public void actionAppendText() {
        textServerArea.setText("============================\n");
        textServerArea.append("Application launched):\n");
    }
}

/** класс ServerFunctional
 * выполняет основной функционал работы сервера
 * содержит конструктор ServerFunctional() и метод
 * searchMinMaxMatrixElements(),
 * принимает размеры матрицы в целочисленном варианте
 */

class ServerFunctional {

    // public static void main(String[] args) {
    /** конструктор ServerFunctional()
     *  осуществляет запуск и работу сервера,
     *  вывод информации в текстовое поле,
     *  алгоритм приема и отправки данных, с сервера и к клиенту
     */
    public void ServerFunctional() {

        int port = 8030; //1025 - 65535

        try {
            TcpServerApplication tcpServerApplication = new TcpServerApplication();
            tcpServerApplication.textServerArea.append("Server running...");
            ServerSocket serverSocket = new ServerSocket(port);
            tcpServerApplication.textServerArea.append("Waiting client...");

            Socket socket = serverSocket.accept();
            tcpServerApplication.textServerArea.append("Connection accepted.");
            System.out.println();

            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();

            //convert streams to dataStreams
            DataInputStream dataInput = new DataInputStream(input);
            DataOutputStream dataOutput = new DataOutputStream(output);

            String lineN = null, lineM = null;
            int n = 0, m = 0;

            while (true) {

                dataOutput.writeUTF("Size for matrix N: ");
                lineN = dataInput.readUTF();
                dataOutput.writeUTF("Size for matrix M: ");
                lineM = dataInput.readUTF();

                n = Integer.parseInt(lineN);
                m = Integer.parseInt(lineM);

                searchMinMaxMatrixElements(n, m);

                dataOutput.flush();

                break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Error string format, only numbers!");
        }
        catch (NullPointerException exception)
        {
            exception.printStackTrace();
        }
    }

    /** метод public void searchMinMaxMatrixElements()
     * @param n,m принимает размеры матрицы в целочисленном варианте
     * находит минимальные и максимальные элементы матрицы
     * принимает ранее запрашиваемую информацию от клиента(количество строк и столбцов
     * матрицы, NxM)
     * Заполнение матрицы случайными числами "matrix[i][j] = random.nextInt(10) + 1", где
     * "i меньше n и j меньше m"
     * вывод заполненной матрицы осуществляется на сервере, а вывод найденного max и min элементов у клиента
     */
    public void searchMinMaxMatrixElements(int n, int m)
    {
        int[][] matrix = new int[n][m];
        int max, min;
        Random random = new Random();
        TcpServerApplication tcpServerApplication = new TcpServerApplication();
        TcpClientApplication tcpClientApplication = new TcpClientApplication();

        //Full
        tcpServerApplication.textServerArea.append("fulled matrix: \n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[i][j] = random.nextInt(10) + 1;
                tcpServerApplication.textServerArea.append(matrix[i][j] + "   ");
            }
            tcpServerApplication.textServerArea.append("\n");
        }

        //Searh min, max
        max = matrix[0][0];
        min = matrix[0][0];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (max < matrix[i][j]) max = matrix[i][j];
                if (min > matrix[i][j]) min = matrix[i][j];

            }
            tcpServerApplication.textServerArea.append("\n");
        }
        tcpClientApplication.textClientArea.append("Max'es element of matrix: " + max);
        tcpClientApplication.textClientArea.append("Minimal'es element of matrix: " + min);
    }
}


/**
 * класс RunServerApplication - хранит в себе main, для запуска приложения
 * main - точка входа в класс и приложение
 */
class RunServerApplication {

    public static void main(String[] args) throws Exception {
        lb3_tcp_application.TcpServerApplication tcpApplication = new lb3_tcp_application.TcpServerApplication();
        tcpApplication.TcpServerApplicationInitialization();
        tcpApplication.actionAppendText();

        ServerFunctional serverFunctional = new ServerFunctional();

    }
}




/*int port = 8030; //1025 - 65535

        TcpServerApplication tcpServerApplication = new TcpServerApplication();

        tcpApplication.textServerArea.append("Server running...\n");
        tcpApplication.textServerArea.append("Waiting client...\n");
        tcpApplication.textServerArea.append("Connection accepted.\n");

        try {

            System.out.println("Server running...");
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Waiting client...");

            Socket socket = serverSocket.accept();
            System.out.println("Connection accepted.");
            System.out.println();

            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();

            //convert streams to dataStreams
            DataInputStream dataInput = new DataInputStream(input);
            DataOutputStream dataOutput = new DataOutputStream(output);

            String lineN = null, lineM = null;
            int n = 0, m = 0;

            while (true) {
                //dataOutput.writeUTF("Size for matrix N: ");
                //lineN = dataInput.readUTF();
                //dataOutput.writeUTF("Size for matrix M: ");
                //lineM = dataInput.readUTF();

                dataOutput.writeUTF("Size for matrix N: ");
                lineN = dataInput.readUTF();
                dataOutput.writeUTF("Size for matrix M: ");
                lineM = dataInput.readUTF();

                n = Integer.parseInt(lineN);
                m = Integer.parseInt(lineM);

                ServerFunctional serverFunctional = new ServerFunctional();

                serverFunctional.searchMinMaxMatrixElements(n, m);

                dataOutput.flush();

                break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Error string format, only numbers!");
        }
        catch (NullPointerException exception)
        {
            exception.printStackTrace();
        }
    */
