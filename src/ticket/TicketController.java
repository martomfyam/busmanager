/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ticket;

import bus.manager.FXMLDocumentController;
import bus.manager.customerdetails;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    private JFXTextField busroute;
    @FXML
    private JFXTextField dateissued;
    @FXML
    private JFXTextField servedby;
    @FXML
    private JFXButton print;
    @FXML
    private Label label;
    @FXML
    private ImageView image2;
    @FXML
    private ImageView image1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
TicketController();
    }    
  //  FXMLDocumentController n = new FXMLDocumentController();

    public void TicketController() {
        
        Image image = new Image("/ticket/icons/bus.jpg");       
        image1.setImage(image);
        Image image3 = new Image("/ticket/icons/bus2.png");       
        image2.setImage(image3);
        /*
        String fname, lname, origin1, dest, date1, days1, phone1, idno1, dateissue;
        double price, discfinal;
        int ticketno2;
        fname = getfname();
        System.out.println("firstname is in ticket: " + fname);
        // fname="martin";
        lname = getlname();
        origin1 = getorigin1();
        dest = getorigin1();
        date1 = getdate1();
        days1 = getdays1();
        phone1 = getphone1();
        idno1 = getidno1();
        price = getprice();
        discfinal = getdiscfinal();
        dateissue = getdateissue();
        ticketno2 = getticketno();
 */
       String fisname = getfname1();
       System.out.println("firstname in ticket: " + fisname);
       
       /* 
        String ticketno1 = Integer.toString(ticketnumber);//int to string
        ticketno.setText(ticketno1);
        firstname.setText(fname);
        System.out.println("firstname in ticket: " + fname);
        lastname.setText(lname);
       // mobileno.setText(phone1);
        mobileno.setText(myname);  
        idno.setText(idno1);
        from.setText(origin1);
        to.setText(dest);
        String price1 =Double.toString(price); // double to string
        farepaid.setText(price1);
        String discfinal1 =Double.toString(discfinal);
        discountoffered.setText(discfinal1);        
        daystotravel.setText(days1);
       // busroute
        dateissued.setText(currentdate);
        servedby.setText("mfyam");       
                
                
                */
                
        
    }

    
}
