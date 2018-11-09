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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;   
import java.time.Period;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author MARTIN
 */
public class FXMLDocumentController extends customerdetails implements Initializable {
    
    DatabaseHandler handler;
    
    ObservableList<String> originlist = FXCollections.observableArrayList("Mosocho","Oyugis","Kisumu","Kisii","Kemera","Nyamira","Kericho");
    ObservableList<String> destinationlist = FXCollections.observableArrayList("Mosocho","Oyugis","Kisumu","Kisii","Kemera","Nyamira","Kericho");
    
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
         
    }    

      public   String fname,lname,origin1,dest,date1,days1,phone1,idno1, currentdate ;
      public   double price,discfinal;
      public   int ticketnumber;
         public String myname="mfyam2" ;
         
         customerdetails m = new customerdetails();
    
    String DATABASE = "busmanager"; //to be changed to busmanager
   
    String db_url = "jdbc:mysql://localhost:3306/";
    String userPass = "?user=root&password="; //mysql?zeroDateTimeBehavior=convertToNull";
    String username = "root";
    String password = "";
   
    private static Connection conn = null;
    private static Statement stmt = null;
   
    
    @FXML
    public void submit(MouseEvent event) {
        
         
        
        //capturing the data from user input
       // myname = "mfyam2";
         fname = firstn.getText();
         
         m.setfname1(fname);
        String x= m.getfname1();
       System.out.println("fname is: "+x);
       
         lname = secondn.getText();
        origin1 = (String) origin.getValue(); 
         dest = (String) destination.getValue();
        LocalDate date2 =  age.getValue();
         date1 = date2.toString();
         days1 = days.getText();
         phone1 = phone.getText();
         idno1 = idno.getText();
        
       
 
      System.out.println("origin is: "+origin1);
      System.out.println("date1 is: "+date1);
   
 
   
   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
   LocalDateTime now = LocalDateTime.now();  
   //System.out.println(dtf.format(now)); 
    currentdate = dtf.format(now);
   System.out.println("current time is "+currentdate);
   LocalDate today = LocalDate.now();
   
            //calculate years
        Period p = Period.between(date2 , today);
        int age2 = p.getYears();
        
        System.out.println("age is "+p.getYears());
        
        //check if selected route is possible and getting their respective 
         price=routechecker(origin1 , dest);
        double disc = daysbonus( price, age2, days1);
        double disc2 = agebonus(price, age2, days1);
         discfinal=disc+disc2;
        price=price-discfinal;
        
        System.out.println("days bonus: "+disc);
         System.out.println("age bonus: "+disc2);
          System.out.println("total bonus: "+discfinal);
           System.out.println("total price: "+price);
           
         ticketnumber=36;     //ticket number
         
         System.out.println("value of ticketno is: "+ticketnumber);
           
        if(price==0.0){
             System.out.println("total price1 is empty");          
        }
        
        
        Boolean flag  = fname.isEmpty()||lname.isEmpty()||days1.isEmpty()||origin1.isEmpty()||dest.isEmpty() || price==0.0;
        if(flag){
            
        //    JOptionPane.showMessageDialog(null," cann't process a ticket , please enter in all fields");
          JOptionPane.showMessageDialog(null, "cann't process a ticket , please enter in all fields","Ticket processing error!",JOptionPane.ERROR_MESSAGE);
            return;
        }  else {
        
//        if(isInEditMode){
//            handleUpdateMember();
//            return;
//        }
//        
         //   DatabaseHandler.createConnection();
        
         String str = "INSERT INTO memberdetail (firstname,lastname,mobile,idno,ticketno,date,days,origin,destination,age,discount,price)" + " VALUES('"+fname+"','"+lname+"','"+phone1+"','"+idno1+"','"+ticketnumber+"','"+currentdate+"','"+days1+"','"+origin1+"','"+dest+"','"+age2+"','"+discfinal+"','"+price+"')";
//         String str = "INSERT INTO memberdetail VALUES ("
//                + "'" +d+ "'," 
//                + "'" + fname+ "',"
//                + "'" + lname+ "'," 
//                 + "'" + phone1+ "',"
//                 + "'" + idno1+ "',"
//                + "'" + days1+ "'"
//                +")";
        System.out.println(str);
        
       Connection conn = DatabaseHandler.getconn();
    
//           try {
//           Class.forName("com.mysql.jdbc.Driver").newInstance();
//           conn = DriverManager.getConnection(db_url+DATABASE,username,password);
//            
//            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e){
//                JOptionPane.showMessageDialog(null, "Cant Load 1 Database","Database error",JOptionPane.ERROR_MESSAGE);
//                System.exit(0);
//            }
//        
//        try{
//            
//        Statement stmt=conn.createStatement();
//        stmt.execute("INSERT INTO memberdetail (firstname,lastname,mobile,idno,days)" + " VALUES('"+fname+"','"+lname+"','"+phone1+"','"+idno1+"','"+days1+"') ");
//         
//           System.out.println(stmt);
//        }catch (SQLException e){
//                JOptionPane.showMessageDialog(null, "Cant Load 2 Database","Database error",JOptionPane.ERROR_MESSAGE);
//                System.exit(0);
//            }
        
        if(handler.execAtion(str)){
            
            JOptionPane.showMessageDialog(null," records added successfully");
             loadMainWindow();
            
        } else{
            
       JOptionPane.showMessageDialog(null, "Error occurred. Could not save","Ticket processing error!",JOptionPane.ERROR_MESSAGE);
           
        }
    

        
        
        
        } //else after if flag 
        
      
        
        
       
        
    }//end of submit method

    @FXML
    public void cancel(MouseEvent event) {
        System.exit(0);
    }
    public double routechecker (String origin , String dest){
        
        String route ="" , route2 ="";
        double price = 0.0;
        
        if(origin.equals("Mosocho") || origin.equals("Oyugis") || origin.equals("Kisumu")){
            
            route = "zone2";
        }
         if(origin.equals("Kemera") || origin.equals("Nyamira") || origin.equals("Kericho")){
            
            route = "zone3";
        }
         if(origin.equals("Kisii")){
            
            route = "zone1";
        }
        
         
         if(dest.equals("Mosocho") || dest.equals("Oyugis") || dest.equals("Kisumu")){
            
            route2 = "zone2";
        }
         if(dest.equals("Kemera") || dest.equals("Nyamira") || dest.equals("Kericho")){
            
            route2 = "zone3";
        }
         if(dest.equals("Kisii")){
            
            route2 = "zone1";
        }
         

         
         if (origin.equals(dest)){
              JOptionPane.showMessageDialog(null,"you selected the same city for both origin and destination","ERROR!", JOptionPane.ERROR_MESSAGE);
         } else if (route.equals(route2)){
              
             JOptionPane.showMessageDialog(null,"you selected cities of the same zone. NB:normal charges apply ","ERROR!", JOptionPane.INFORMATION_MESSAGE);
             
             if((origin.equals("Mosocho") && dest.equals("Oyugis") || origin.equals("Mosocho") && dest.equals("Kisumu") || origin.equals("Kisumu") && dest.equals("Oyugis")) ||
                 (origin.equals("Oyugis") && dest.equals("Mosocho") || origin.equals("Kisumu") && dest.equals("Mosocho") || origin.equals("Oyugis") && dest.equals("Kisumu"))) {
                 price=200;
               } else if(((origin.equals("Kemera") && dest.equals("Nyamira") || origin.equals("Kemera") && dest.equals("Kericho") || origin.equals("Kericho") && dest.equals("Nyamira")) ||
                         (origin.equals("Nyamira") && dest.equals("Kemera") || origin.equals("Kericho") && dest.equals("Kemera") || origin.equals("Nyamira") && dest.equals("Kericho")))){
                   price=250;
               }
          
             
         }
         
        if((route.equals("zone2") && route2.equals("zone3")) || (route2.equals("zone2") && route.equals("zone3"))){
            JOptionPane.showMessageDialog(null,"you have to pass via zone 1. NB: normal charges apply!","NOTE!!", JOptionPane.INFORMATION_MESSAGE);
            price = 450;
        }
        if ((route.equals("zone2") && route2.equals("zone1")) || (route.equals("zone1") && route2.equals("zone2"))) {
            price = 200;
        }
         if ((route.equals("zone3") && route2.equals("zone1")) || (route.equals("zone1") && route2.equals("zone3"))) {
            price = 250;
        }
        
        System.out.println("final price is: "+price);
        return price;
    }

    public double daysbonus(double price,int age ,String days2){
        
        int days1 = Integer.parseInt(days2); 
        double disc1=0;
        
        if(days1>=60){
            disc1=0.4*price;    //40% discount
         //   price=price-disc1;
        } else if(days1>7){
            disc1=0.1*price;        //10% discount
         //   price=price-disc1; 
        }
        
        
        return disc1;
    }
    
     public double agebonus(double price,int age ,String days2){
         double disc=0;
    //     int days1 = Integer.parseInt(days2); 
         if((age>=0 && age <=10) || (age>50)){
         disc=0.4*price;
     }
         
         
         return disc;
     }

     
             void loadMainWindow() {
            
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/ticket/ticket.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
        //    stage.setTitle("Bus Manager");
            stage.setScene(new Scene(parent));
            stage.show();
     
            
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
     
     
     
      public void setfname(String n){
          fname=n;
      }
             
     public String getfname(){
       //  fname="martin2";
     return fname; //initialrise the variable on top of the page
     }
     public String getlname(){
         System.out.println("value in lnme function is: "+lname);
         return lname;
     }
     public String getorigin1(){
         return origin1;
     }
     public String getdest(){
         return dest;
     }
     public String getdate1(){
         return date1;
     }
     public String getdays1(){
         return days1;
     }
     public String getphone1(){
         return phone1;
     }
     public String getidno1(){
         return idno1;
     }
     public double getprice(){
         return price;
     }
     public double getdiscfinal(){
         return discfinal;
     }
     public String getdateissue(){
         return currentdate;
     }
     public int getticketno(){
         return ticketnumber;
     }
     
     
     
}
