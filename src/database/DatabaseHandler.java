/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javax.swing.JOptionPane;
//import library.assistant.ui.listbook.BookListController.Book;
//import library.assistant.ui.memberlist.MemberListController.Member;

public class DatabaseHandler {

    String DATABASE = "busmanager"; //to be changed to LIBRARYSYS

    private static DatabaseHandler handler = null;

    String db_url = "jdbc:mysql://localhost:3306/";
    String userPass = "?user=root&password="; //mysql?zeroDateTimeBehavior=convertToNull";
    String username = "root";
    String password = "";

    private static Connection conn = null;
    private static Statement stmt = null;

    public DatabaseHandler() {
        createDatabase();
        createConnection();
        setupLoginTable();
        setupMemberTable();
        //   logindefault();

    }

    public static DatabaseHandler getInstance() {
        if (handler == null) {
            handler = new DatabaseHandler();
        }
        return handler;
    }

    public static Connection getconn() {
        return conn;
    }

    public void createConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(db_url + DATABASE, username, password);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Cant Load Database", "Database error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    void createDatabase() {
        try {
            //System.out.println("ATTEMPTING CREATING DATABASE " + DATABASE);
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(db_url + userPass);
            stmt = conn.createStatement();
            stmt.execute("CREATE DATABASE IF NOT EXISTS " + DATABASE);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("UNABLE TO CREATE DATABASE");
            e.printStackTrace();
        }
    }

    void setupLoginTable() {
        String TABLE_NAME = "LOGINDETAILS"; //to be changed to BOOK
        try {
            System.out.println("Checking if table " + TABLE_NAME + " exists");

            stmt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            System.out.println("Initializing . . .");
            if (tables.next()) {
                System.out.println("Table " + TABLE_NAME + " already exists!");
            } else {
                stmt.execute("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                        + " id  int(10) unsigned auto_increment not null primary key ,\n"
                        + " username varchar(200),\n"
                        + " password varchar(200) "
                        + " )");
                System.out.println("TABLE " + TABLE_NAME + " CREATED SUCCESSFULLY");

                // add default login details.
                logindefault();
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage() + " - - - - >> SetupDatabase");
        } finally {

        }
    }

    void logindefault() {
        String username1 = "admin";
        String password1 = "admin";

        String username2 = "ceo";
        String password2 = "ceo";

        username1 = getMd5(username1);
        password1 = getMd5(password1);

        username2 = getMd5(username2);
        password2 = getMd5(password2);

//       String DATABASE = "busmanager"; //to be changed to LIBRARYSYS
//    
//    private static DatabaseHandler handler = null;
//   
//    String db_url = "jdbc:mysql://localhost:3306/";
//    String userPass = "?user=root&password="; //mysql?zeroDateTimeBehavior=convertToNull";
//    String username = "root";
//    String password = "";
//   
//    private static Connection conn = null;
//    private static Statement stmt = null;
//    
        // JDBC driver name and database URL
        String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        String db_url1 = "jdbc:mysql://localhost/busmanager";

        //  Database credentials
        // static final String USER = "username";
        //static final String PASS = "password";
//   Connection conn = null;
//   Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(db_url1, username, password);
            System.out.println("Connected database successfully...");

            //STEP 4: Execute a query
            System.out.println("Inserting records into logindetails table...");
            stmt = conn.createStatement();

            String sql = "INSERT INTO logindetails (username,password)" + " VALUES('" + username1 + "','" + password1 + "')";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO logindetails (username,password)" + " VALUES('" + username2 + "','" + password2 + "')";
            stmt.executeUpdate(sql);

            System.out.println("Inserted records into logindetails table...");

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }

//    try{
//     String str = "INSERT INTO logindetails (username,password)" + " VALUES('"+username1+"','"+password1+"')";
//     
//     
//        Connection conn = DatabaseHandler.getconn();
//
//         stmt = conn.createStatement();
//     
//           stmt.executeUpdate(str);
//     
//      if(handler.execAtion(str)){
//            
//            System.out.println("default login added successfuly");
//            
//        } else{
//            
//           System.out.println("default login not added");
//        }
//    } catch(Exception e){
//        System.out.println("unable to add default values");
//    }
    }

    void setupMemberTable() {
        String TABLE_NAME = "MEMBERDETAIL";
        try {
            System.out.println("Checking if table " + TABLE_NAME + " exists");

            stmt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            System.out.println("Initializing . . .");
            if (tables.next()) {
                System.out.println("Table " + TABLE_NAME + " already exists!");
            } else {
                stmt.execute("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                        + " id int(10) unsigned auto_increment not null unique,\n"
                        + " firstname varchar(200),\n"
                        + " lastname varchar(200),\n"
                        + " age varchar(200),\n"
                        + " mobile varchar(20),\n"
                        + " idno varchar(200),\n"
                        + " ticketno varchar(200) primary key,\n"
                        + " origin varchar(200),\n"
                        + " destination varchar(200),\n"
                        + " price varchar(200),\n"
                        + " discount varchar(200),\n"
                        + " date varchar(200),\n"
                        + " days varchar(100) "
                        + " )");
                System.out.println("TABLE " + TABLE_NAME + " CREATED SUCCESSFULLY");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " - - - - >> SetupmembersDatabase");
        } finally {

        }
    }

    public static String getMd5(String input) {
        try {

            // Static getInstance method is called with hashing MD5 
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest 
            //  of an input digest() return array of byte 
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation 
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value 
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } // For specifying wrong message digest algorithms 
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

 



    public boolean execAtion(String qu) {
        try {
            stmt = conn.createStatement();
            stmt.execute(qu);
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "error:" + ex.getMessage(), "error occurred", JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception at execQuery:dataHandler 2" + ex.getLocalizedMessage());
            return false;
        } finally {
        }
    }


}
