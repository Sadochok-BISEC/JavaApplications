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
     * method TcpServerApplication()
     * initializes components with default parameters
     */

    public void TcpServerApplicationInitialization() {

        //icons
        Icon iconClear = new ImageIcon("..\images\clear.png");
        Icon iconExit = new ImageIcon("..\images\exit.png");

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
     * method actionExit()
     * @param with
     * exits the application by clicking the "Exit" button
     */

    public void actionExit(ActionEvent e) {
        System.exit(0);
    }

    /**
     * method actionClearFields()
     * @param with
     * clears all fields by clicking the "Clear" button
     */

    public void actionClearFields(ActionEvent e) {
        textServerArea.setText("");
    }

    /**
     * method actionAppendText()
     * adds text to an existing one
     */

    public void actionAppendText() {
        textServerArea.setText("============================\n");
        textServerArea.append("Application launched):\n");
    }
}

/** class ServerFunctional
 * performs the main functionality of the server
 * contains a constructor ServerFunctional() и method
 * searchMinMaxMatrixElements(),
 * takes the dimensions of the matrix in the integer version
 */

class ServerFunctional {

    // public static void main(String[] args) {
    /** constructor ServerFunctional()
     *  performs the launch and operation of the server,
     *  displaying information in a text field,
     *  the algorithm for receiving and sending data, from the server and to the client
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

    /** method public void searchMinMaxMatrixElements()
     * @param n,m takes the dimensions of the matrix in the integer version
     * finds the minimum and maximum elements of the matrix
     * accepts previously requested information from the client (number of rows and columns
     * matrices, NxM)
     * Filling the matrix with random numbers "matrix[i][j] = random.nextInt(10) + 1", when
     * "i < n and j < m"
     * the output of the filled matrix is carried out on the server, and the output of the found max and min elements from the client
     */
    public void searchMinMaxMatrixElements(int n, int m)
    {
        int[][] matrix = new int[n][m];
        int max, min;
        Random random = new Random();
        TcpServerApplication tcpServerApplication = new TcpServerApplication();
        TcpClientApplication tcpClientApplication = new TcpClientApplication();

        // Filing
        tcpServerApplication.textServerArea.append("fulled matrix: \n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[i][j] = random.nextInt(10) + 1;
                tcpServerApplication.textServerArea.append(matrix[i][j] + "   ");
            }
            tcpServerApplication.textServerArea.append("\n");
        }

        // Searh min, max
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
 * class RunServerApplication - stores the main in itself to run the application
 * main - the entry point to the classroom and the application
 */
class RunServerApplication {

    public static void main(String[] args) throws Exception {
        lb3_tcp_application.TcpServerApplication tcpApplication = new lb3_tcp_application.TcpServerApplication();
        tcpApplication.TcpServerApplicationInitialization();
        tcpApplication.actionAppendText();

        ServerFunctional serverFunctional = new ServerFunctional();
    }
}
