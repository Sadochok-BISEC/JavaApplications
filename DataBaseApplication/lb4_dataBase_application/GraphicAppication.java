package lb4_dataBase_application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/** DataBase Application
 * @author Ilya Borisov
 * @version java 8.0
 */


/**
 * class GraphicAppication
 * performs the main functionality of the application
 * contains a constructor GraphicAppication() and methods
 *  public void actionPerformed() и
 *  public void actionAaa()
 */

public class GraphicAppication
extends JFrame
        implements ActionListener {

        private Container container;
        private JButton btnLoad;
        private JButton btnAdd;
        private JButton btnDelete;
        private JButton btnRefresh;
        private JButton btnExit;

        private JTable table;

        private JTextField textField_Id;
        private JTextField textField_Name;
        private JTextField textField_ManufDetails;
        private JTextField textField_Date;
        private JTextField textField_Cost;

        private JLabel label_Id;
        private JLabel label_Name;
        private JLabel label_ManufDetails;
        private JLabel label_Date;
        private JLabel label_Cost;


        DataBaseManager dataBaseManager = new DataBaseManager();
        /**
         * constructor GraphicAppication()
         * initializes components with default parameters
         */

        GraphicAppication() {

            //mainframe
            JFrame frame = new JFrame();
            frame.setTitle("DataBaseApplication");
            frame.setBounds(300, 90, 497, 533);
            frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
            frame.setResizable(true);

            container = getContentPane();
            container.setLayout(null);

            //region BUTTONS

            //button load
            btnLoad = new JButton("Load");
            btnLoad.setFont(new Font("Times New Roman", Font.PLAIN, 12));
            btnLoad.setSize(80, 20);
            btnLoad.setLocation(390, 350);
            btnLoad.setToolTipText("Load button, to load data from data base");
            btnLoad.addActionListener(this);

            container.add(btnLoad);

            //button add
            btnAdd = new JButton("Add");
            btnAdd.setFont(new Font("Times New Roman", Font.PLAIN, 12));
            btnAdd.setSize(80, 20);
            btnAdd.setLocation(390, 380);
            btnAdd.setToolTipText("Add button, for add to data base from text fields");
            btnAdd.addActionListener(this);

            container.add(btnAdd);


            //button delete
            btnDelete = new JButton("Delete");
            btnDelete.setFont(new Font("Times New Roman", Font.PLAIN, 12));
            btnDelete.setSize(80, 20);
            btnDelete.setLocation(390, 410);
            btnDelete.setToolTipText("Delete button, for delete info from data base");
            btnDelete.addActionListener(this);

            container.add(btnDelete);


            //button refresh
            btnRefresh = new JButton("Refresh");
            btnRefresh.setFont(new Font("Times New Roman", Font.PLAIN, 12));
            btnRefresh.setSize(80, 20);
            btnRefresh.setLocation(390, 440);
            btnRefresh.setToolTipText("Update button, for the refresh info in grid");
            btnRefresh.addActionListener(this);

            container.add(btnRefresh);

            //button exit
            btnExit = new JButton("Exit");
            btnExit.setFont(new Font("Times New Roman", Font.PLAIN, 12));
            btnExit.setSize(80, 20);
            btnExit.setLocation(390, 470);
            btnExit.setToolTipText("Exit button, for exit from application");
            btnExit.addActionListener(this);

            container.add(btnExit);

            //endregion

            //region TEXTFIELDS
            //textField id
            textField_Id = new JTextField();
            textField_Id.setEditable(true);
            textField_Id.setFont(new Font("Times New Roman", Font.PLAIN, 12));
            textField_Id.setSize(300, 20);
            textField_Id.setLocation(50, 350);

            container.add(textField_Id);

            //textField name
            textField_Name = new JTextField();
            textField_Name.setEditable(true);
            textField_Name.setFont(new Font("Times New Roman", Font.PLAIN, 12));
            textField_Name.setSize(300, 20);
            textField_Name.setLocation(50, 380);

            container.add(textField_Name);

            //textField ManufDetails
            textField_ManufDetails = new JTextField();
            textField_ManufDetails.setEditable(true);
            textField_ManufDetails.setFont(new Font("Times New Roman", Font.PLAIN, 12));
            textField_ManufDetails.setSize(300, 20);
            textField_ManufDetails.setLocation(50, 410);

            container.add(textField_ManufDetails);

            //textField date
            textField_Date = new JTextField();
            textField_Date.setEditable(true);
            textField_Date.setFont(new Font("Times New Roman", Font.PLAIN, 12));
            textField_Date.setSize(300, 20);
            textField_Date.setLocation(50, 440);

            container.add(textField_Date);

            //textField cost
            textField_Cost = new JTextField();
            textField_Cost.setEditable(true);
            textField_Cost.setFont(new Font("Times New Roman", Font.PLAIN, 12));
            textField_Cost.setSize(300, 20);
            textField_Cost.setLocation(50, 470);

            container.add(textField_Cost);

            //endregion

            //region LABELS
            //label id
            label_Id = new JLabel("Id");
            label_Id.setFont(new Font("Times New Roman", Font.PLAIN, 12));
            label_Id.setSize(100, 20);
            label_Id.setLocation(1, 350);

            container.add(label_Id);

            //label name
            label_Name = new JLabel("Name");
            label_Name.setFont(new Font("Times New Roman", Font.PLAIN, 12));
            label_Name.setSize(100, 20);
            label_Name.setLocation(1, 380);

            container.add(label_Name);

            //label ManufDetails
            label_ManufDetails = new JLabel("Details");
            label_ManufDetails.setFont(new Font("Times New Roman", Font.PLAIN, 12));
            label_ManufDetails.setSize(100, 20);
            label_ManufDetails.setLocation(1, 410);

            container.add(label_ManufDetails);

            //label date
            label_Date = new JLabel("Date");
            label_Date.setFont(new Font("Times New Roman", Font.PLAIN, 12));
            label_Date.setSize(100, 20);
            label_Date.setLocation(1, 440);

            container.add(label_Date);

            //label cost
            label_Cost = new JLabel("Cost");
            label_Cost.setFont(new Font("Times New Roman", Font.PLAIN, 12));
            label_Cost.setSize(100, 20);
            label_Cost.setLocation(1, 470);

            container.add(label_Cost);

            //endregion

            //table
            table = new JTable(getHeight(),getWidth());
            table.setFont(new Font("Times New Roman", Font.PLAIN, 12));
            table.setSize(475, 345);
            table.setLocation(0, 0);

            container.add(table);

            frame.add(container);
            //frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }

    /**
     * method public void actionPerformed()
     * executes events that occur when buttons are pressed
     * uses ActionEvent with
     * e.getSource() == btnName
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnLoad)
        {
            dataBaseManager.printDataFromDataBase(table);
        }
        else if(e.getSource() == btnAdd) {
            actionAdd();
        }
        else if(e.getSource() == btnRefresh)
        {
            table.repaint();
        }
        else if(e.getSource() == btnDelete)
        {
            dataBaseManager.deleteFromDataBase();
        }
        else if(e.getSource() == btnExit)
            System.exit(0);
    }

    public void actionAdd()
    {
        int trueId = Integer.parseInt(textField_Id.getText());
        int trueCost = Integer.parseInt(textField_Cost.getText());
        try {
            dataBaseManager.addToDataBase(trueId,textField_Name.getText(),
                    textField_ManufDetails.getText(), textField_Date.getText(), trueCost);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}


class runfdnf{
    public static void main(String[] args) {
        GraphicAppication graphicAppication = new GraphicAppication();
    }
}