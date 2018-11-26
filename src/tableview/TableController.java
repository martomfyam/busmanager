/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tableview;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author MARTIN
 */
public class TableController implements Initializable {
    ObservableList<viewtable> list = FXCollections.observableArrayList();

    @FXML
    private TableView<viewtable> table;
    @FXML
    private TableColumn<viewtable, String> idcol;
    @FXML
    private TableColumn<viewtable, String> firstnamecol;
    @FXML
    private TableColumn<viewtable, String> lastnamecol;
    @FXML
    private TableColumn<viewtable, String> fromcol;
    @FXML
    private TableColumn<viewtable, String> tocol;
    @FXML
    private TableColumn<viewtable, String> dayscol;
    @FXML
    private TableColumn<viewtable, String> ticketnocol;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        loaddata();
  
        
    }
        public void loaddata(){
           
        try {
            String DATABASE = "busmanager"; //to be changed to busmanager
            
            //String url = "jdbc:mysql://localhost:3306/";
            //   String userPass = "?user=root&password="; //mysql?zeroDateTimeBehavior=convertToNull";
            String username = "root";
            String password = "";
            
            //connecting to the database to retrive the last record
            Connection connection = null;
            
            try {
                
                // Load the MySQL JDBC driver
                String driverName = "com.mysql.jdbc.Driver";
               
                Class.forName(driverName);
                
                // Create a connection to the database
                String serverName = "localhost";
                
                
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
            
            
            
            ResultSet rs = connection.createStatement().executeQuery("select * from memberdetail");
            
            while (rs.next()) {
                
                
                list.add(new viewtable(rs.getString("id"), rs.getString("firstname"), rs.getString("lastname"),rs.getString("origin"),rs.getString("destination"), rs.getString("days"),rs.getString("ticketno") ));
            }
            
               } catch (SQLException ex) {

            Logger.getLogger(TableController.class.getName()).log(Level.SEVERE, null, ex);

        }
            
            
            idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
            firstnamecol.setCellValueFactory(new PropertyValueFactory<>("firstname"));
            lastnamecol.setCellValueFactory(new PropertyValueFactory<>("lastname"));
            fromcol.setCellValueFactory(new PropertyValueFactory<>("from"));
            tocol.setCellValueFactory(new PropertyValueFactory<>("to"));
            dayscol.setCellValueFactory(new PropertyValueFactory<>("days"));
            ticketnocol.setCellValueFactory(new PropertyValueFactory<>("ticketno"));
            
            
            table.setItems(list);
            // TODO
     
    }    

    @FXML
    private void back(MouseEvent event) {
        closeStage();
        loadDetailsForm();
    }
    
     void loadDetailsForm() {

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
        ((Stage) table.getScene().getWindow()).close();
    }
    
}
