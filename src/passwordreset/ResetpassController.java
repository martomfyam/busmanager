/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package passwordreset;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author MARTIN
 */
public class ResetpassController implements Initializable {

    @FXML
    private JFXPasswordField newpass;
    @FXML
    private JFXPasswordField newconfirm;
    @FXML
    private JFXPasswordField ceopass;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    public String ceopassword, ceopasswordd, newpassword, newconfirmed;

    @FXML
    private void submit(MouseEvent event) {
        ceopassword = ceopass.getText();
        newpassword = newpass.getText();
        newconfirmed = newconfirm.getText();

        System.out.print("The value of ceo password is: " + ceopassword);
        ceopasswordd = getoldpassword();

        // boolean flag =( !(verifystring (oldpassword)) || !(verifystring (newpassword)) || !(verifystring (newconfirmed)));
        boolean flag = "".equals(ceopassword) || "".equals(newpassword) || "".equals(newconfirm);

        if (flag) {
            JOptionPane.showMessageDialog(null, "Please ensure you enter all fields", "password changing error!", JOptionPane.ERROR_MESSAGE);

        } else if (!getMd5(ceopassword).equals(ceopasswordd)) {
            JOptionPane.showMessageDialog(null, "wrong old password submitted!!", "wrong password!", JOptionPane.ERROR_MESSAGE);

        } else if (!newpassword.equals(newconfirmed)) {

            JOptionPane.showMessageDialog(null, "New password doesn't match", "wrong password!", JOptionPane.ERROR_MESSAGE);

        } else {

            System.out.println("main stage");
            //main
            String position2 = getMd5("admin");
            newpassword = getMd5(newpassword);
            changepassword(position2, newpassword);

            JOptionPane.showMessageDialog(null, " password reset successfully");
            closeStage();
            loadloginpage();

        }
    }

    @FXML
    private void cancel(MouseEvent event) {
        closeStage();
        loadloginpage();
    }

    void loadloginpage() {

        try {

            Parent parent = FXMLLoader.load(getClass().getResource("/login/login.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.getIcons().add(new Image("/ticket/icons/bus.jpg"));
            stage.setTitle("LOGIN");
            stage.setScene(new Scene(parent));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeStage() {
        ((Stage) newpass.getScene().getWindow()).close();
    }

    public boolean verifystring(String n) {

        n = n.trim();
        if (n == null || n.equals("")) {
            return false;
        }
        if (!n.matches("[a-zA-Z]*")) {
            return false;
        }
        return true;
    }

    public String getoldpassword() {

        String DATABASE = "busmanager"; //to be changed to busmanager

        //String url = "jdbc:mysql://localhost:3306/";
        String userPass = "?user=root&password="; //mysql?zeroDateTimeBehavior=convertToNull";
        String username = "root";
        String password = "";

        // String fname = null, lname = null, origin1 = null, dest = null, date1 = null, days1 = null, phone1 = null, idno1 = null, dateissue ="";
        // String price = null, discfinal ="";
        String ceopass1 = "";

        //connecting to the database to retrive the last record
        Connection connection = null;

        try {

            // Load the MySQL JDBC driver
            String driverName = "com.mysql.jdbc.Driver";

            Class.forName(driverName);
            String serverName = "localhost";
            String url = "jdbc:mysql://" + serverName + "/" + DATABASE;
            connection = DriverManager.getConnection(url, username, password);

            System.out.println("Successfully Connected to the database!");

        } catch (ClassNotFoundException e) {

            System.out.println("Could not find the database driver " + e.getMessage());

        } catch (SQLException e) {

            System.out.println("Could not connect to the database " + e.getMessage());

        }

        try {

            // Get a result set containing all data from test_table
            Statement statement = connection.createStatement();

            ResultSet results = statement.executeQuery("SELECT * FROM logindetails");

            // For each row of the result set ...
            String position = "ceo";
            while (results.next()) {

                String usernamed = results.getString("username");
                System.out.println("username is: " + usernamed);

                if (usernamed.equals(getMd5(position))) {

                    ceopass1 = results.getString("password");

                    break;
                }

            }

            System.out.println("password is: " + ceopass1);

        } catch (SQLException e) {

            System.out.println("Could not retrieve data from the database " + e.getMessage());

        }
        return ceopass1;
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

    public void changepassword(String position2, String newpassw) {

        String DATABASE = "busmanager"; //to be changed to busmanager

        //String url = "jdbc:mysql://localhost:3306/";
        String userPass = "?user=root&password="; //mysql?zeroDateTimeBehavior=convertToNull";
        String username = "root";
        String password = "";

        Connection conn = null;

        try {

            // Load the MySQL JDBC driver
            String driverName = "com.mysql.jdbc.Driver";

            Class.forName(driverName);
            String serverName = "localhost";
            String url = "jdbc:mysql://" + serverName + "/" + DATABASE;
            conn = DriverManager.getConnection(url, username, password);

            System.out.println("Successfully Connected to the database!");

        } catch (ClassNotFoundException e) {

            System.out.println("Could not find the database driver " + e.getMessage());

        } catch (SQLException e) {

            System.out.println("Could not connect to the database " + e.getMessage());

        }

        try {

            System.out.println("value of position before change " + position2);
            System.out.println("value of password before change " + newpassw);

            // create the java mysql update preparedstatement
            String query = "update logindetails set password = ? where username = ? ";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, newpassw);
            preparedStmt.setString(2, position2);

            // execute the java preparedstatement
            preparedStmt.executeUpdate();

            System.out.println("done in method");

        } catch (SQLException e) {

            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());

        }

    }

}
