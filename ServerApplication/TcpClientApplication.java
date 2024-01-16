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
 * содержит графическую обетрку приложения "Клиент"
 * выполняет общение с сервером
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
     * метод TcpClientApplication()
     * инициализирует компоненты с параметрами по умолчанию
     */

    public void TcpClientApplicationInitialization() {

        //icons
        Icon iconClear = new ImageIcon("D:\\Game Dev\\MyProjects\\Learn\\Learning\\QuickSolution\\Jp12\\src\\images\\clear.png");
        Icon iconExit = new ImageIcon("D:\\Game Dev\\MyProjects\\Learn\\Learning\\QuickSolution\\Jp12\\src\\images\\exit.png");

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
     * метод actionExit()
     * @param e
     * осуществляет выход с приложения, при клике кнопке "Exit"
     */

    public void actionExit(ActionEvent e) {
        System.exit(0);
    }

    /**
     * метод actionClearFields(ActionEvent e)
     * осуществляет очистку всех полей, при клике кнопке "Clear"
     */

    public void actionClearFields(ActionEvent e) {
        textField.setText("");
        textClientArea.setText("");
    }

    /**
     * метод actionAppendText()
     * осуществляет добавление текста к существующиму
     */

    public void actionAppendText() {
        textClientArea.setText("============================\n");
        textClientArea.append("=\n");
    }

    /**
     * метод actionSend()
     * @param e
     * осуществляет "общение" между пользователем и сервером,
     * используя метод clientSend(Socket socket) из класса ClientFunctional
     * для активации при клике по кнопке "Send"  - запускаем процесс
     */
    public void actionSend(ActionEvent e) {

        if (e.getSource() == btnSend) {
            ClientFunctional clientFunctional = new ClientFunctional();
        }
    }
}

/**
 * class ClientFunctional
 * выполняет основной функционал работы клиента
 * содержит конструктор ClientFunctional() и метод
 *  public void clientSend()
 */

 class ClientFunctional{

    /** конструктор ClientFunctional()
     *  осуществляет подключение к серверу,
     *  вывод информации в текстовое поле,
     *  алгоритм приема и отправки данных, с сервера и на сервер
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
     * метод public void clientSend()
     * @param socket
     * осуществляет "общение" между пользователем и сервером
     * используя текстовое поле textField, вводим запрашиваемую информацию серверу(количество строк и столбцов
     * матрицы, NxM)
     * для активации при клике по кнопке "Send"  - запускаем процесс
     */

    public void clientSend(Socket socket) {
        TcpClientApplication clientApplication = new TcpClientApplication();

            try {
        InputStream input = socket.getInputStream();
        OutputStream output = socket.getOutputStream();

        //конвертируем потоки в другой тип, чтобы легче было обрабатывать сообщения
        DataInputStream dataInput = new DataInputStream(input);
        DataOutputStream dataOutput = new DataOutputStream(output);

        //clientApplication.textField.setText("");
        String line = null, line2 = null, message = null;


                //get message from server "Size for matrix"
                message = dataInput.readUTF();
                clientApplication.textClientArea.append(message);

                //get size of matrix from user
                line = clientApplication.textField.getText();
                clientApplication.textClientArea.append(clientApplication.textField.getText() + "\n");
                clientApplication.textClientArea.append("Sending to the server...");
                dataOutput.writeUTF(line);

                //get message
                message = dataInput.readUTF();
                clientApplication.textClientArea.append(message);

                //get size of matrix from user
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
 * класс RunClientApplication - хранит в себе main, для запуска приложения
 * main - точка входа в класс и приложение
 */
class RunClientApplication {

    public static void main(String[] args) throws Exception {
        lb3_tcp_application.TcpClientApplication tcpApplication = new lb3_tcp_application.TcpClientApplication();
        tcpApplication.TcpClientApplicationInitialization();
        tcpApplication.actionAppendText();

    }
}
