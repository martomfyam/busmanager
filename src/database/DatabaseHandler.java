/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;


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
    
    public DatabaseHandler()
    {
      createDatabase();
      createConnection();
      setupLoginTable();
      setupMemberTable();
      //logindefault();
     // setupIssueTable();
    }

    public static DatabaseHandler getInstance()
    {
        if(handler == null){
           handler = new DatabaseHandler();
        }
        return handler;
    }
    
    public static Connection getconn(){
        return conn;
    }
    
    public void createConnection(){
       try {
           Class.forName("com.mysql.jdbc.Driver").newInstance();
           conn = DriverManager.getConnection(db_url+DATABASE,username,password);
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e){
                JOptionPane.showMessageDialog(null, "Cant Load Database","Database error",JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
    }
    void createDatabase(){
        try {
            //System.out.println("ATTEMPTING CREATING DATABASE " + DATABASE);
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(db_url+userPass);
            stmt= conn.createStatement();
            stmt.execute("CREATE DATABASE IF NOT EXISTS " + DATABASE );
         } catch(ClassNotFoundException | SQLException e){
             System.out.println("UNABLE TO CREATE DATABASE");
             e.printStackTrace();
         }
    }
    
    void setupLoginTable() {
        String TABLE_NAME = "LOGINDETAILS"; //to be changed to BOOK
         try{
            System.out.println("Checking if table " +TABLE_NAME + " exists");
            
            stmt= conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null,null,TABLE_NAME.toUpperCase(),null);
            System.out.println("Initializing . . .");
            if (tables.next()){
                System.out.println("Table " +TABLE_NAME + " already exists!");
             } 
            else {
                stmt.execute("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                + " id  int(10) primary key ,\n"
                + " username varchar(200),\n"
                + " password varchar(200) "
                + " )" );
               System.out.println("TABLE " + TABLE_NAME + " CREATED SUCCESSFULLY");
               
               // add default login details.
               
             }
  
         } catch (SQLException e){
            System.err.println(e.getMessage() + " - - - - >> SetupDatabase");
        } finally {
            
        }
    } 
    
    void logindefault(){
    String username1="admin";
    String password1="admin";
    
     String str = "INSERT INTO logindetails (username,password)" + " VALUES('"+username1+"','"+password1+"')";
     
      if(handler.execAtion(str)){
            
            System.out.println("default login added successfuly");
            
        } else{
            
           System.out.println("default login not added");
        }
     
    }
    
    
    void setupMemberTable() {
        String TABLE_NAME = "MEMBERDETAIL"; 
         try{
            System.out.println("Checking if table " +TABLE_NAME + " exists");
            
            stmt= conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null,null,TABLE_NAME.toUpperCase(),null);
            System.out.println("Initializing . . .");
            if (tables.next()){
                System.out.println("Table " +TABLE_NAME + " already exists!");
             } 
            else {
                stmt.execute("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                + " id int(10) ,\n"
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
         } catch (SQLException e){
            System.err.println(e.getMessage() + " - - - - >> SetupmembersDatabase");
        } finally {
            
        }
    } 
    
    void setupIssueTable() {
        String TABLE_NAME = "ISSUE"; 
         try{
            System.out.println("Checking if table " +TABLE_NAME + " exists");
            
            stmt= conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null,null,TABLE_NAME.toUpperCase(),null);
            System.out.println("Initializing . . .");
            if (tables.next()){
                System.out.println("Table " +TABLE_NAME + " already exists!");
             } 
            else {
                stmt.execute("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                + " bookID varchar(200) primary key,\n"
                + " memberID varchar(200),\n"
                + " issueTime timestamp default CURRENT_TIMESTAMP,\n"
                + " renew_count integer default 0,\n"
                + " FOREIGN KEY (bookID) REFERENCES BOOK(id),\n"
                + " FOREIGN KEY (memberID) REFERENCES MEMBER(id)"
                + " )" );
               System.out.println("TABLE " + TABLE_NAME + " CREATED SUCCESSFULLY");
             }
         } catch (SQLException e){
            System.err.println(e.getMessage() + " - - - - >> SetupDatabase");
        } finally {
            
        }
    } 
    
    public ResultSet execQuery(String query){
        ResultSet result;
        try{
            stmt = conn.createStatement();
            result = stmt.executeQuery(query);
           } catch (SQLException ex){
            System.out.println("exception at execQuery:datahandler 1 " + ex.getLocalizedMessage());
            return null;
           } finally {
        } 
        return result;
    }
    
    public boolean execAtion(String qu){
        try{
            stmt = conn.createStatement();
            stmt.execute(qu);
            return true;
        } catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "error:" + ex.getMessage(),"error occurred",JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception at execQuery:dataHandler 2" + ex.getLocalizedMessage());
            return false;
        } finally {
        }
    }
    
//    public boolean deletebook(Book book){
//        try {
//            String deleteStatment = "DELETE FROM BOOK WHERE ID = ?";
//            PreparedStatement stmt  = conn.prepareStatement(deleteStatment);
//            stmt.setString(1, book.getId());
//            int res = stmt.executeUpdate();
//            if (res == 1){
//            return true;
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
//        }
//            return false;
//    }
    
//    public boolean isBookAlreadyIssued(Book book){
//        try {
//            String checkstmt = "SELECT COUNT(*) FROM ISSUE WHERE bookID = ?";
//            PreparedStatement stmt = conn.prepareStatement(checkstmt);
//            stmt.setString(1 , book.getId());
//            ResultSet rs = stmt.executeQuery();
//            if(rs.next()){
//                int count = rs.getInt(1);
//                System.out.println(count);
//                return (count>0);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return false;
//    }
    
//    public boolean updateBook(Book book){
//        try {
//            String update = "UPDATE BOOK SET title = ?, author = ?, publisher = ? WHERE id = ?";
//            PreparedStatement stmt =conn.prepareStatement(update);
//            stmt.setString(1, book.getTitle());
//            stmt.setString(2, book.getAuthor());
//            stmt.setString(3, book.getPublisher());
//            stmt.setString(4, book.getId());
//            int res = stmt.executeUpdate();
//            return (res>0);
//        } catch (SQLException ex) {
//            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return false;
//    }
    
//    public boolean updateMember(Member member){
//        try {
//            String update = "UPDATE MEMBER SET name = ?, mobile = ?, email = ? WHERE id = ?";
//            PreparedStatement stmt =conn.prepareStatement(update);
//            stmt.setString(1, member.getName());
//            stmt.setString(2, member.getMobile());
//            stmt.setString(3, member.getEmail());
//            stmt.setString(4, member.getId());
//            int res = stmt.executeUpdate();
//            return (res>0);
//        } catch (SQLException ex) {
//            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return false;
//    }
    
//    public boolean deletemember(Member member){
//        try {
//            String deleteStatment = "DELETE FROM MEMBER WHERE id = ?";
//            PreparedStatement stmt  = conn.prepareStatement(deleteStatment);
//            stmt.setString(1, member.getId());
//            int res = stmt.executeUpdate();
//            if (res == 1){
//            return true;
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
//        }
//            return false;
//    }
//    
//    public boolean isMemberIssuedWithBook(Member member){
//        try {
//            String checkstmt = "SELECT COUNT(*) FROM ISSUE WHERE memberID = ?";
//            PreparedStatement stmt = conn.prepareStatement(checkstmt);
//            stmt.setString(1 , member.getId());
//            ResultSet rs = stmt.executeQuery();
//            if(rs.next()){
//                int count = rs.getInt(1);
//                System.out.println(count);
//                return (count>0);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return false;
//    }
    
    public ObservableList<PieChart.Data> getBookGraphStatistics(){
        ObservableList<PieChart.Data> data =FXCollections.observableArrayList();
        
        try{
            String qu1 = "SELECT COUNT(*) FROM BOOK";
            String qu2 = "SELECT COUNT(*) FROM ISSUE";
            ResultSet rs = execQuery(qu1);
            if (rs.next()) {
                    int count = rs.getInt(1);
                    data.add(new PieChart.Data("Total Books(" + count + ")" , count));
                }
                rs = execQuery(qu2);
                if (rs.next()) {
                    int count = rs.getInt(1);
                    data.add(new PieChart.Data("Issued Books(" + count + ")", count));
                }
            }catch(Exception e){
                e.printStackTrace();
        }
        return data;
    }

    public ObservableList<PieChart.Data> getMemberGraphStatistics(){
        ObservableList<PieChart.Data> data =FXCollections.observableArrayList();
        
        try{
            String qu1 = "SELECT COUNT(*) FROM MEMBER";
            String qu2 = "SELECT COUNT(DISTINCT memberID) FROM ISSUE";
            ResultSet rs = execQuery(qu1);
            if (rs.next()) {
                    int count = rs.getInt(1);
                    data.add(new PieChart.Data("Total Members(" + count + ")" , count));
                }
                rs = execQuery(qu2);
                if (rs.next()) {
                    int count = rs.getInt(1);
                    data.add(new PieChart.Data("Members With Books(" + count + ")", count));
                }
            }catch(Exception e){
                e.printStackTrace();
        }
        return data;
    }
}

