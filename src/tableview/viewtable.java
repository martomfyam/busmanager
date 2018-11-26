/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tableview;

/**
 *
 * @author MARTIN
 */
public class viewtable {  
    
    String id,firstname,lastname,from,to,days,ticketno;

    public viewtable(String id, String firstname, String lastname, String from, String to, String days, String ticketno) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.from = from;
        this.to = to;
        this.days = days;
        this.ticketno = ticketno;
    }

  

    public String getDays() {
        return days;
    }

    public String getFirstname() { 
        return firstname;
    }

    public String getFrom() {
        return from;
    }

    public String getId() {
        return id;
    }

    public String getLastname() {
        return lastname;
    }

    public String getTicketno() {
        return ticketno;
    }

    public String getTo() {
        return to;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setTicketno(String ticketno) {
        this.ticketno = ticketno;
    }

    public void setTo(String to) {
        this.to = to;
    }



    
}
