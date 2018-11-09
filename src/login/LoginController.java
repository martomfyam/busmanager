/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import bus.manager.BusManager;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author MARTIN
 */
public class LoginController implements Initializable {

    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXButton login;
    @FXML
    private JFXButton cancel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void login(MouseEvent event) {
        String user = username.getText();
        String pass = password.getText();
        String userd = "";
        String passd="";
        
        
      String DATABASE = "busmanager"; //to be changed to LIBRARYSYS
    
    String db_url = "jdbc:mysql://localhost:3306/";
    String userPass = "?user=root&password="; //mysql?zeroDateTimeBehavior=convertToNull";
    String username = "root";
    String password = "";
   
   
    
        
        
          try
    {
      // create our mysql database connection
//      String myDriver = "org.gjt.mm.mysql.Driver";
//      String myUrl = "jdbc:mysql://localhost/test";
//      
//      Class.forName(myDriver);
//      Connection conn = DriverManager.getConnection(myUrl, "root", "");
      Class.forName("com.mysql.jdbc.Driver").newInstance();
      Connection conn = DriverManager.getConnection(db_url+DATABASE,username,password);
      
      // our SQL SELECT query. 
      // if you only need a few columns, specify them by name instead of using "*"
      String query = "SELECT * FROM logindetails";

      // create the java statement
      Statement st = conn.createStatement();
      
      // execute the query, and get a java resultset
      ResultSet rs = st.executeQuery(query);
      
      // iterate through the java resultset
      while (rs.next())
      {
        int id = rs.getInt("id");
         userd = rs.getString("username");
         passd = rs.getString("password");
        
        
        // print the results
       // System.out.format("%s, %s, %s\n", id, username, password);
        System.out.println(id+" "+userd+" "+passd);
      }
      st.close();
    }
    catch (Exception e)
    {
        System.err.println("Got an exception! ");
        System.err.println(e.getMessage());
        JOptionPane.showMessageDialog(null, "can't fetch login details from the database","database select error!",JOptionPane.ERROR_MESSAGE);

    }
 
           if(user.equals(userd)&&pass.equals(passd)){
//            closeStage();
//            loadMainWindow();
//          JOptionPane.showMessageDialog(null, "succesfully logged in","login",JOptionPane.INFORMATION_MESSAGE);
//         bus.manager.BusManager bb= new BusManager();
//         bb.setVisible(true);
          
           
          loadMainWindow();
          
        } else {
           JOptionPane.showMessageDialog(null, "wrong username or password","wrong credentials error!",JOptionPane.ERROR_MESSAGE);
           
        }
    

        
        
    }

    @FXML
    private void cancel(MouseEvent event) {
        System.exit(0);
    }
    
    
        void loadMainWindow() {
            
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/bus/manager/FXMLDocument.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
        //    stage.setTitle("Bus Manager");
            stage.setScene(new Scene(parent));
            stage.show();
     
            
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
    
    
}
