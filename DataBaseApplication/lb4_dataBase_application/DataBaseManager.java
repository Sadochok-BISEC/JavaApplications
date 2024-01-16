package lb4_dataBase_application;

import javax.swing.*;
import java.sql.*;

/**
 * class DataBaseManager
 * выполняет "связь" между Бд и приложением
 */
//add, delete, refresh, save(edit)
public class DataBaseManager {

    public static final String USER_NAME = "root";
    public static final String PASSWORD = "Sadochok";
    public static final String URL = "jdbc:mysql://localhost:3306/lb4_ncn";
    public static Statement statement;
    public static Connection connection;
    public static ResultSet resultSet;

    static {
        try{
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (SQLException throwables){
            throwables.printStackTrace();
            throw new RuntimeException();
        }
    }
    static {
        try {
            statement = connection.createStatement();
        }catch (SQLException throwables){
            throwables.printStackTrace();
            throw  new RuntimeException();
        }
    }

    /**
     * метод public void addToDataBase()
     * добавляет данные в БД из текстовых полей приложения
     */
    public void addToDataBase(int _id, String _name, String _manufDetails,
    String _date, int _cost) throws SQLException {

        String query = "INSERT INTO lb3_ncn.souvenir (id, name, manufacturer_details, date, cost) \n" +
                " VALUES("+ _id +"," + _name +"," + _manufDetails +"," + _date +"," + _cost + ");";
       // statement.executeUpdate(query);

        try {
            // opening database connection to MySQL server
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);

            // getting Statement object to execute query
            statement = connection.createStatement();

            // executing SELECT query
            resultSet = statement.executeQuery(query);

           /* while (resultSet.next()) {
                int count = resultSet.getInt(1);
                System.out.println("Total number of books in the table : " + count);
            }*/

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close connection ,stmt and resultset here
            try {  connection.close(); } catch(SQLException se) { /*can't do anything */ }
            try { statement.close(); } catch(SQLException se) { /*can't do anything */ }
            try { resultSet.close(); } catch(SQLException se) { /*can't do anything */ }
        }

        //https://java-master.com/%D1%80%D0%B0%D0%B1%D0%BE%D1%82%D0%B0-%D1%81-%D0%B1%D0%B0%D0%B7%D0%BE%D0%B9-%D0%B4%D0%B0%D0%BD%D0%BD%D1%8B%D1%85-mysql-%D0%B2-java/
        //https://tproger.ru/translations/java-jdbc-example/
        //https://habr.com/ru/sandbox/146588/

    }

    public void updateDataBase() throws SQLException {
        String query = "UPDATE lb3_ncn.souvenir SET (id, name, manufacturer_details, date, cost) \n" +
                ");";
        //statement.executeUpdate(query);

        try {
            // opening database connection to MySQL server
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);

            // getting Statement object to execute query
            statement = connection.createStatement();

            // executing UPDATE query
            statement.executeUpdate(query);


        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close connection ,stmt and resultset here
            try {  connection.close(); } catch(SQLException se) { /*can't do anything */ }
            try { statement.close(); } catch(SQLException se) { /*can't do anything */ }
            try { resultSet.close(); } catch(SQLException se) { /*can't do anything */ }
        }
    }

   /* public void editInDataBase(){}*/

    /**
     * метод public void deleteFromDataBase()
     * удааляет данные в БД
     */
    public void deleteFromDataBase()
    {
        String query = "DELETE  FROM souvenir WHERE id='id';";
        try {
            // opening database connection to MySQL server
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            statement = connection.createStatement();
            statement.executeUpdate(query);

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close connection ,stmt and resultset here
            try {  connection.close(); } catch(SQLException se) { /*can't do anything */ }
            try { statement.close(); } catch(SQLException se) { /*can't do anything */ }
            try { resultSet.close(); } catch(SQLException se) { /*can't do anything */ }
        }
    }

    /**
     * метод public void printDataFromDataBase()
     * выводит данные из БД
     */
    public void printDataFromDataBase(JTable table)
    {
        String query = "SELECT * FROM souvenir";
        try {
            // opening database connection to MySQL server
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);

            statement = connection.createStatement();

            resultSet = statement.executeQuery(query);



            int rowCount = 0;
            while(resultSet.next()) {
                table.setValueAt(resultSet.getString(2),rowCount,0);
                table.setValueAt(resultSet.getString(3),rowCount,1);
                table.setValueAt(resultSet.getDate(4),rowCount,2);
                table.setValueAt(resultSet.getInt(5),rowCount,3);
                rowCount++;
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close connection ,stmt and resultset here
            try {  connection.close(); } catch(SQLException se) { /*can't do anything */ }
            try { statement.close(); } catch(SQLException se) { /*can't do anything */ }
            try { resultSet.close(); } catch(SQLException se) { /*can't do anything */ }
        }
    }

    /*public static void main(String[] args) throws ClassCastException,SQLException, IOException {
        //Class.forName('')

        //connection = DriverManager.getConnection(URL,USER_NAME,PASSWORD);
        //statement = connection.createStatement();

        resultSet = statement.executeQuery("SELECT * FROM souvenir");
        while (resultSet.next())
        {
            System.out.println(resultSet.getString(2)+" "+
                    resultSet.getString(3)+" "+
                    resultSet.getDate(4)+" "+
                    resultSet.getInt(5));
        }
        statement.close();
    }*/
}


