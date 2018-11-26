/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bus.manager;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import database.DatabaseHandler;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.ImageIcon;
import login.LoginController;

/**
 *
 * @author MARTIN
 */
public class FXMLDocumentController extends customerdetails implements Initializable {

    DatabaseHandler handler;

    ObservableList<String> originlist = FXCollections.observableArrayList("Mosocho", "Oyugis", "Kisumu", "Kisii", "Kemera", "Nyamira", "Kericho");
    ObservableList<String> destinationlist = FXCollections.observableArrayList("Mosocho", "Oyugis", "Kisumu", "Kisii", "Kemera", "Nyamira", "Kericho");

    @FXML
    private JFXTextField firstn;
    @FXML
    private JFXTextField secondn;
    @FXML
    private JFXDatePicker age;
    @FXML
    private JFXComboBox origin;
    @FXML
    private JFXComboBox destination;
    @FXML
    private JFXTextField days;
    @FXML
    private JFXButton submit;
    @FXML
    private JFXButton cancel;
    @FXML
    private JFXTextField phone;
    @FXML
    private JFXTextField idno;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        handler = DatabaseHandler.getInstance();

        origin.setItems(originlist);
        origin.setValue(originlist.get(0).toString());

        destination.setItems(destinationlist);
        destination.setValue(destinationlist.get(0).toString());

        getticketnumber();

        if (ticketnumber == 0) {
            ticketnumber = 7690;
        }

    }

    public String fname, lname, origin1, dest, date1, days1, phone1, idno1, currentdate;
    public double price, discfinal;
    public int ticketnumber;
     boolean phonevalidation=false;
    //  public String myname="mfyam2" ;

    String DATABASE = "busmanager"; //to be changed to busmanager

    String db_url = "jdbc:mysql://localhost:3306/";
    String userPass = "?user=root&password="; //mysql?zeroDateTimeBehavior=convertToNull";
    String username = "root";
    String password = "";

    private static Connection conn = null;
    private static Statement stmt = null;

    @FXML
    public void submit(MouseEvent event) {

        //   customerdetails m = new customerdetails();
        //capturing the data from user input
        // myname = "mfyam2";
        //  m.setfname1(fname);
        //   String x= m.getfname1();
        //   System.out.println("fname is: "+x);
        fname = firstn.getText();
        lname = secondn.getText();
        origin1 = (String) origin.getValue();
        dest = (String) destination.getValue();
        LocalDate date2 = age.getValue();
        date1 = date2.toString();
        days1 = days.getText();     
        phone1 = phone.getText();
        idno1 = idno.getText();
        
        boolean idvalidate = stringisint(idno1);
          boolean daysvalidate = stringisint(days1);
        if (idvalidate){
             JOptionPane.showMessageDialog(null, "Please enter a valid ID number ", "Phone input error!", JOptionPane.ERROR_MESSAGE);
        } else if (daysvalidate)
                {
             JOptionPane.showMessageDialog(null, "Please enter a valid Number of days", "Days hone input error!", JOptionPane.ERROR_MESSAGE);       
                }
        else{
        int idno3 = Integer.parseInt(idno1);
        int days = Integer.parseInt(days1);
     
        phone1 = validatephoneno (phone1 );
        System.out.println("phonenumber is "+phone1);

        System.out.println("origin is: " + origin1);
        System.out.println("date1 is: " + date1);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        //System.out.println(dtf.format(now)); 
        currentdate = dtf.format(now);
        System.out.println("current time is " + currentdate);
        LocalDate today = LocalDate.now();

        //calculate years
        Period p = Period.between(date2, today);
        int age2 = p.getYears();

        System.out.println("age is " + p.getYears());

        //check if selected route is possible and getting their respective 
        price = routechecker(origin1, dest, days);
        double disc = daysbonus(price, age2, days1);
        double disc2 = agebonus(price, age2, days1);
        discfinal = disc + disc2;
        price = price - discfinal;

        System.out.println("days bonus: " + disc);
        System.out.println("age bonus: " + disc2);
        System.out.println("total bonus: " + discfinal);
        System.out.println("total price: " + price);

        // ticketnumber=38;     //ticket number
        System.out.println("value of ticketno is: " + ticketnumber);

        if (price == 0.0) {
            System.out.println("total price1 is empty");
        }
        Boolean validate = (!(verifystring(fname)) || !(verifystring(lname)));

        Boolean flag = fname.isEmpty() || lname.isEmpty() || days1.isEmpty() || origin1.isEmpty() || dest.isEmpty() || price == 0.0 ||  days < 1;
        if (flag) {

            //    JOptionPane.showMessageDialog(null," cann't process a ticket , please enter in all fields");
            JOptionPane.showMessageDialog(null, "cann't process a ticket , please enter in all fields", "Ticket processing error!", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (validate) {

            JOptionPane.showMessageDialog(null, "Please enter first or last name properly", "Name input error!", JOptionPane.ERROR_MESSAGE);
        }else if(phonevalidation){
            
            JOptionPane.showMessageDialog(null, "Please enter a valid phonenumber ", "Phone input error!", JOptionPane.ERROR_MESSAGE);
            
        }else if( idno3<=10000000 || idno3>=99999999 ){
             JOptionPane.showMessageDialog(null, "Please enter a valid ID number ", "Phone input error!", JOptionPane.ERROR_MESSAGE);
           
        }else if( age2 < 0 ){
             JOptionPane.showMessageDialog(null, "Please select a valid Date of Birth ", "Phone input error!", JOptionPane.ERROR_MESSAGE);
        }else if( existingrecord(fname , idno1 ,dest , origin1 ) ){
                JOptionPane.showMessageDialog(null, "records not added due to duplicate entities ", "records input error!", JOptionPane.ERROR_MESSAGE);   
          //not to allow two id cards register two or more tickets...only for dependants   
        } else {
            ticketnumber = ticketnumber + 1;
//        if(isInEditMode){
//            handleUpdateMember();
//            return;
//        }
//        
            //   DatabaseHandler.createConnection();

            String str = "INSERT INTO memberdetail (firstname,lastname,mobile,idno,ticketno,date,days,origin,destination,age,discount,price)" + " VALUES('" + fname + "','" + lname + "','" + phone1 + "','" + idno1 + "','" + ticketnumber + "','" + currentdate + "','" + days1 + "','" + origin1 + "','" + dest + "','" + age2 + "','" + discfinal + "','" + price + "')";

            System.out.println(str);

            Connection conn = DatabaseHandler.getconn();


            if (handler.execAtion(str)) {

                JOptionPane.showMessageDialog(null, " records added successfully");
                closeStage();
                loadticket();

            } else {

                JOptionPane.showMessageDialog(null, "Error occurred. Could not save", "Ticket processing error!", JOptionPane.ERROR_MESSAGE);

            }

        } //else after if flag 
        }
    }//end of submit method

    @FXML
    public void cancel(MouseEvent event) {
        System.exit(0);
    }

    public double routechecker(String origin, String dest, int days) {

        String route = "", route2 = "";
        double price = 0.0;

        if (origin.equals("Mosocho") || origin.equals("Oyugis") || origin.equals("Kisumu")) {

            route = "zone2";
        }
        if (origin.equals("Kemera") || origin.equals("Nyamira") || origin.equals("Kericho")) {

            route = "zone3";
        }
        if (origin.equals("Kisii")) {

            route = "zone1";
        }

        if (dest.equals("Mosocho") || dest.equals("Oyugis") || dest.equals("Kisumu")) {

            route2 = "zone2";
        }
        if (dest.equals("Kemera") || dest.equals("Nyamira") || dest.equals("Kericho")) {

            route2 = "zone3";
        }
        if (dest.equals("Kisii")) {

            route2 = "zone1";
        }

        if (origin.equals(dest)) {
            JOptionPane.showMessageDialog(null, "You selected the same TOWN for both origin and destination", "ERROR!", JOptionPane.ERROR_MESSAGE);
        } else if (route.equals(route2)) {

            JOptionPane.showMessageDialog(null, "You selected TOWN of the same zone. NB:Normal charges apply ", "ERROR!", JOptionPane.INFORMATION_MESSAGE);

            if ((origin.equals("Mosocho") && dest.equals("Oyugis") || origin.equals("Mosocho") && dest.equals("Kisumu") || origin.equals("Kisumu") && dest.equals("Oyugis"))
                    || (origin.equals("Oyugis") && dest.equals("Mosocho") || origin.equals("Kisumu") && dest.equals("Mosocho") || origin.equals("Oyugis") && dest.equals("Kisumu"))) {
                price = 200;
            } else if (((origin.equals("Kemera") && dest.equals("Nyamira") || origin.equals("Kemera") && dest.equals("Kericho") || origin.equals("Kericho") && dest.equals("Nyamira"))
                    || (origin.equals("Nyamira") && dest.equals("Kemera") || origin.equals("Kericho") && dest.equals("Kemera") || origin.equals("Nyamira") && dest.equals("Kericho")))) {
                price = 250;

            }

        }

        if ((route.equals("zone2") && route2.equals("zone3")) || (route2.equals("zone2") && route.equals("zone3"))) {
            JOptionPane.showMessageDialog(null, "you have to pass via zone 1. NB: normal charges apply!", "NOTE!!", JOptionPane.INFORMATION_MESSAGE);
            price = 450;
        }
        if ((route.equals("zone2") && route2.equals("zone1")) || (route.equals("zone1") && route2.equals("zone2"))) {
            price = 200;
        }
        if ((route.equals("zone3") && route2.equals("zone1")) || (route.equals("zone1") && route2.equals("zone3"))) {
            price = 250;
        }
        double totalPrice = price * days;
        System.out.println("final price is: " + totalPrice);

        return totalPrice;
    }

    public double daysbonus(double price, int age, String days2) {
        //double totaldisc;
        int days1 = Integer.parseInt(days2);
        double disc1 = 0;

        if (days1 >= 60) {
            disc1 = 0.4 * price;    //40% discount
            //   price=price-disc1;
        } else if (days1 > 7) {
            disc1 = 0.1 * price;        //10% discount
            //   price=price-disc1; 

        }

        return disc1;
    }

    public double agebonus(double price, int age, String days2) {
        double disc = 0;
        //     int days1 = Integer.parseInt(days2); 
        if ((age >= 0 && age <= 10) || (age > 50)) {
            disc = 0.4 * price;
        }

        return disc;
    }

    void loadticket() {

        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/ticket/ticket.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.getIcons().add(new Image("/ticket/icons/bus.jpg"));
            stage.setTitle("TICKET");
            stage.setScene(new Scene(parent));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getticketnumber() {

        String DATABASE = "busmanager"; //to be changed to busmanager

        //String url = "jdbc:mysql://localhost:3306/";
        String userPass = "?user=root&password="; //mysql?zeroDateTimeBehavior=convertToNull";
        String username = "root";
        String password = "";

        // String fname = null, lname = null, origin1 = null, dest = null, date1 = null, days1 = null, phone1 = null, idno1 = null, dateissue ="";
        // String price = null, discfinal ="";
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

             
                ticketno2 = results.getString("ticketno");
                ticketnumber = Integer.parseInt(ticketno2);

                //  System.out.println("Fetching data by column name for row " + results.getRow() + " : " + data);
            }

            System.out.println("ticketno is: " + ticketno2);

        } catch (SQLException e) {

            System.out.println("Could not retrieve data from the database " + e.getMessage());

        }

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

    public void closeStage() {
        ((Stage) submit.getScene().getWindow()).close();
    }

    @FXML
    private void exit2(MouseEvent event) {
        closeStage();
        loadloginpage();
    }

    @FXML
    private void changepass2(MouseEvent event) {
        closeStage();
        loadchangepasswindow();
    }

    void loadchangepasswindow() {

        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/changepassword/changepass.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.getIcons().add(new Image("/ticket/icons/bus.jpg"));
            stage.setTitle("CHANGE PASSWORD");
            stage.setScene(new Scene(parent));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
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

            public String validatephoneno (String inputPhoneNumber){
              
                String validPhoneNumber = null;
    
                        Pattern pattern = Pattern.compile("^(?:254|\\+254|0)?(7(?:(?:[12][0-9])|(?:0[0-8])|(?:9[0-9]))[0-9]{6})$");
                        Matcher matcher = pattern.matcher(inputPhoneNumber);
                        
                        if (matcher.matches()) {
                            validPhoneNumber = "+254" + matcher.group(1);
                            phonevalidation=false;
                        } else {
                            
                            System.out.println("invalid phonenumber");
                            phonevalidation=true;
                        }
                  
        return validPhoneNumber;
            }
            
            
            
            public boolean stringisint (String s){
                
                try {
                    Integer.parseInt(s);
                    return false;
                } catch (NumberFormatException ex)
                {
                    return true;
                }
            }

    @FXML
    private void customerslist(MouseEvent event) {
        closeStage();
        loadcustomerlist();
    }
            
          void loadcustomerlist() {

        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/tableview/table.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.getIcons().add(new Image("/ticket/icons/bus.jpg"));
            stage.setTitle("Customer's List");
            stage.setScene(new Scene(parent));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    
          public boolean existingrecord(String name , String idnumber,String to1,String from1){
              String iddatabase=null;
              String name1=null;
              String ticket=null;
             boolean choice=false;
             String days=null;
             String to,from;
                    String DATABASE = "busmanager"; //to be changed to busmanager

        //String url = "jdbc:mysql://localhost:3306/";
        String userPass = "?user=root&password="; //mysql?zeroDateTimeBehavior=convertToNull";
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
System.out.println("value of new id is: "+idnumber);
             String   iddatabasechecker = results.getString("idno");
          
             System.out.println("value of database 1 id is: "+iddatabasechecker);
             if(idnumber.equals(iddatabasechecker)){
               System.out.println("value of database 2 id is: "+iddatabasechecker);  
                 iddatabase = results.getString("idno");
                 name1=results.getString("firstname");
                 ticket=results.getString("ticketno");
                 days=results.getString("days");
                 from = results.getString("origin");
                 to = results.getString("destination");
                
                 // the check as to be of the same route to be rejected
                
               if (from.equals(from1) && to.equals(to1)){
	int input = JOptionPane.showConfirmDialog(null, "Dear "+name+" you already have a ticket  (ticket no:"+ticket+") of the same route valid for "+days+" days. If you are paying a ticket for a dependant press 'YES' else 'NO'  ", "Confirm ",
			JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (input==0){
           choice=false; 
           idno1=idnumber+"D";
        }else{
            choice=true;
        }
        break;
             }       
                 
             }
        
                //  System.out.println("Fetching data by column name for row " + results.getRow() + " : " + data);
            }
           

        } catch (SQLException e) {

            System.out.println("Could not retrieve data from the database " + e.getMessage());

        }
              
           return choice;   
          }
          
    
    
    
}
