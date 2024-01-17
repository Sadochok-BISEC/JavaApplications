package lb3_tcp_application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


/** Tcp Client Application
 * @author Ilya Borisov
 * @version java 8.0
 */


/**class TcpClientApplication
 * contains the graphical wrapper of the Client application
 * contains a graphical outline of the "Client" application
 */
public class TcpClientApplication
        extends JFrame {

    private Container container;
    public JButton btnSend;
    public JButton btnExit;
    public JButton btnClear;
    public JTextArea textClientArea;
    public JTextField textField;

    /**
     * method TcpClientApplication()
     * initializes components with default parameters
     */

    public void TcpClientApplicationInitialization() {

        //icons
        Icon iconClear = new ImageIcon("..\images\clear.png");
        Icon iconExit = new ImageIcon("..\images\exit.png");

        //mainframe
        JFrame frame = new JFrame();
        frame.setTitle("Client Application");
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
        btnSend.addActionListener(this::actionExit);
        btnSend.addActionListener(this::actionSend);
        container.add(btnSend);


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
        textClientArea = new JTextArea(10, getWidth());
        textClientArea.setEditable(false);
        textClientArea.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        textClientArea.setSize(440, 500);
        textClientArea.setLocation(0, 0);
        textClientArea.setLineWrap(true);
        textClientArea.setWrapStyleWord(true);

        container.add(textClientArea);

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
        textField.setText("");
        textClientArea.setText("");
    }

     /**
     * method actionAppendText()
     * adds text to an existing one
     */

    public void actionAppendText() {
        textClientArea.setText("============================\n");
        textClientArea.append("=\n");
    }

    /**
     * method actionSend()
     * @param with
     * performs "communication" between the user and the server,
     * uses method clientSend(Socket socket) from ClientFunctional class
     * to activate by clicking on the button "Send"  - starting the process
     */
    public void actionSend(ActionEvent e) {

        if (e.getSource() == btnSend) {
            ClientFunctional clientFunctional = new ClientFunctional();
        }
    }
}

/**
 * class ClientFunctional
 * performs the main functionality of the client's work
 * contains a constructor ClientFunctional() and method
 *  public void clientSend()
 */

 class ClientFunctional{

    /** constructor ClientFunctional()
     *  connects to the server,
     *  displaying information in a text field,
     *  the algorithm for receiving and sending data, from the server and to the server
     */

    public void ClientFunctional()
    {
        int serverPort = 8030;
        String address = "CHELL"; //hostName

        try{
            InetAddress ipAddress = InetAddress.getByName(address);

            Socket socket = new Socket(ipAddress, serverPort);
            TcpClientApplication tcpClientApplication = new TcpClientApplication();
            tcpClientApplication.textClientArea.append("Connected");

            while(true)
            {
                clientSend(socket);
                break;
            }
        }
        catch(UnknownHostException ex){

            ex.printStackTrace();
        } catch(IOException exception) {

            exception.printStackTrace();
        }

    }


    /**
     * method public void clientSend()
     * @param socket
     * performs "communication" between the user and the server
     * using the textField textfield, we enter the requested information to the server (the number of rows and columns
     * matrix, NxM)
     * to activate it by clicking on the "Send" button, start the process
     */

    public void clientSend(Socket socket) {
        TcpClientApplication clientApplication = new TcpClientApplication();

            try {
        InputStream input = socket.getInputStream();
        OutputStream output = socket.getOutputStream();

        // We convert streams to another type to make it easier to process messages.
        DataInputStream dataInput = new DataInputStream(input);
        DataOutputStream dataOutput = new DataOutputStream(output);

        //clientApplication.textField.setText("");
        String line = null, line2 = null, message = null;


                // get message from server "Size for matrix"
                message = dataInput.readUTF();
                clientApplication.textClientArea.append(message);

                // get size of matrix from user
                line = clientApplication.textField.getText();
                clientApplication.textClientArea.append(clientApplication.textField.getText() + "\n");
                clientApplication.textClientArea.append("Sending to the server...");
                dataOutput.writeUTF(line);

                // get message
                message = dataInput.readUTF();
                clientApplication.textClientArea.append(message);

                // get size of matrix from user
                line2 = clientApplication.textField.getText();
                clientApplication.textClientArea.append(clientApplication.textField.getText() + "\n");
                clientApplication.textClientArea.append("Sending to the server...");
                dataOutput.writeUTF(line2);

                System.out.println("Good job homie");
                dataOutput.flush();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
}


/**
 * class RunClientApplication - stores the main in itself to run the application
 * main - точкаlog in to the classroom and the application
 */
class RunClientApplication {

    public static void main(String[] args) throws Exception {
        lb3_tcp_application.TcpClientApplication tcpApplication = new lb3_tcp_application.TcpClientApplication();
        tcpApplication.TcpClientApplicationInitialization();
        tcpApplication.actionAppendText();

    }
}
