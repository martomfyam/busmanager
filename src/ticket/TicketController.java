/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ticket;

import bus.manager.customerdetails;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import database.DatabaseHandler;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author MARTIN
 */
public class TicketController extends customerdetails implements Initializable {

    @FXML
    private JFXTextField ticketno;
    @FXML
    private JFXTextField firstname;
    @FXML
    private JFXTextField lastname;
    @FXML
    private JFXTextField mobileno;
    @FXML
    private JFXTextField idno;
    @FXML
    private JFXTextField from;
    @FXML
    private JFXTextField to;
    @FXML
    private JFXTextField farepaid;
    @FXML
    private JFXTextField discountoffered;
    @FXML
    private JFXTextField daystotravel;
    @FXML
    private JFXTextField dateissued;
    @FXML
    private JFXTextField servedby;
    @FXML
    private JFXButton print;
    @FXML
    private ImageView image2;
    @FXML
    private ImageView image1;

    DatabaseHandler handler;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handler = DatabaseHandler.getInstance();
        try {
            // TODO
            TicketController();
        } catch (SQLException ex) {
            Logger.getLogger(TicketController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //  FXMLDocumentController n = new FXMLDocumentController();

    public void TicketController() throws SQLException {

        Image image = new Image("/ticket/icons/bus.jpg");
        image1.setImage(image);
        Image image3 = new Image("/ticket/icons/bus2.png");
        image2.setImage(image3);

        String DATABASE = "busmanager"; //to be changed to busmanager

        //String url = "jdbc:mysql://localhost:3306/";
     //   String userPass = "?user=root&password="; //mysql?zeroDateTimeBehavior=convertToNull";
        String username = "root";
        String password = "";

        String fname = null, lname = null, origin1 = null, dest = null, date1 = null, days1 = null, phone1 = null, idno1 = null, dateissue = "";
        String price = null, discfinal = "";
        String ticketno2 = "";

        //connecting to the database to retrive the last record
        Connection connection = null;

        try {

            // Load the MySQL JDBC driver
            String driverName = "com.mysql.jdbc.Driver";

            Class.forName(driverName);

            // Create a connection to the database
            String serverName = "localhost";

            // String schema = "busmanager";
            String url = "jdbc:mysql://" + serverName + "/" + DATABASE;

            //  String username = "username";
            //  String password = "password";
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

            ResultSet results = statement.executeQuery("SELECT * FROM memberdetail");

            // For each row of the result set ...
            while (results.next()) {

                // Get the data from the current row using the column index - column data are in the VARCHAR format
                String data = results.getString(1);

                System.out.println("Fetching data by column index for row " + results.getRow() + " : " + data);

                // Get the data from the current row using the column name - column data are in the VARCHAR format
                data = results.getString("origin");

                ticketno2 = results.getString("ticketno");
                fname = results.getString("firstname");
                // System.out.println("firstname is in ticket: " + fname);
                // fname="martin";
                lname = results.getString("lastname");
                origin1 = results.getString("origin");
                dest = results.getString("destination");
                date1 = results.getString("date");
                days1 = results.getString("days");
                phone1 = results.getString("mobile");
                idno1 = results.getString("idno");
                price = results.getString("price");
                discfinal = results.getString("discount");
                dateissue = results.getString("date");

                //  System.out.println("Fetching data by column name for row " + results.getRow() + " : " + data);
            }

            System.out.println("firstname is: " + fname);
            System.out.println("lastnamename is: " + lname);
            System.out.println("date issued is: " + date1);

        } catch (SQLException e) {

            System.out.println("Could not retrieve data from the database " + e.getMessage());

        }

        // displaying the variables on the ticket
        ticketno.setText(ticketno2);
        firstname.setText(fname);
        lastname.setText(lname);
        mobileno.setText(phone1);
        idno.setText(idno1);
        from.setText(origin1);
        to.setText(dest);
        farepaid.setText(price);
        discountoffered.setText(discfinal);
        daystotravel.setText(days1);
        // busroute
        dateissued.setText(date1);
        servedby.setText("mfyam");

        ticketno.setEditable(false);
        firstname.setEditable(false);
        lastname.setEditable(false);
        mobileno.setEditable(false);
        idno.setEditable(false);
        from.setEditable(false);
        to.setEditable(false);
        farepaid.setEditable(false);
        discountoffered.setEditable(false);
        daystotravel.setEditable(false);
        // busroute
        dateissued.setEditable(false);
        servedby.setEditable(false);

    }

    @FXML
    private void newticket(MouseEvent event) {

        closeStage();
        loadMainWindow();

    }

    void loadMainWindow() {

        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/bus/manager/FXMLDocument.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.getIcons().add(new Image("/ticket/icons/bus.jpg"));
            stage.setTitle("MAIN MENU");
            stage.setScene(new Scene(parent));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeStage() {
        ((Stage) ticketno.getScene().getWindow()).close();
    }

}
