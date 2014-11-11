/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.servlets;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uk.ac.dundee.computing.aec.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn;
import uk.ac.dundee.computing.aec.instagrim.stores.UserProfile;

/**
 * This servlet handles current profile handling - querying the database and setting 
 * session variables
 * @author Kaspars Greizis
 */
@WebServlet(name="Profile", urlPatterns = {"/profile"})
//@WebServlet
public class Profile extends HttpServlet {
    private Cluster cluster;
    private HttpSession session;
    private String UserName="";
    private String first_name="null";
    private String last_name="null";
    private String email="null";
    private UserProfile p;
    
    public Profile(){
        super();
    }
    
    public void init(ServletConfig config) throws ServletException {
        cluster = CassandraHosts.getCluster();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String value;
        UserName="null";
        first_name="null";
        last_name="null";
        email="null";
        System.out.println("test");
        RequestDispatcher rd = request.getRequestDispatcher("/profile.jsp");
        HttpSession session=request.getSession();
        LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
        if (lg != null) {  
            if (lg.getlogedin()) {
                UserName = lg.getUsername();
                SetProfile(request,response);
                value = "success";
                System.out.println(value);                
            }else{
                value="fail: could not log in";
                session.setAttribute("login", value);
                System.out.println(value);
            }
        }else{
            value="fail: lg=null";
            session.setAttribute("login", value);
            System.out.println(value);
        }
    }
    
    /**
     * Queries the database and sets current user data in session variables
    */
    private void SetProfile(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        HttpSession session = request.getSession();
        Session csession = cluster.connect("instagrimKG");
        ResultSet rs = null;        
        PreparedStatement ps = csession.prepare("select login, first_name, last_name, email from userprofiles where login='"+UserName+"'"); //just a test, change user
        BoundStatement boundStatement = new BoundStatement(ps);
        rs = csession.execute( 
                boundStatement.bind() );
        if (rs.isExhausted()){
            System.out.println("No First Name returned");            
        }else{
        for (Row row : rs){
                first_name = row.getString("first_name");
                last_name = row.getString("last_name");
                email = row.getString("email");
            }
                session.setAttribute("first_name", first_name);
                System.out.println(first_name);  
                session.setAttribute("last_name", last_name);
                System.out.println(last_name);
                session.setAttribute("email", email);
                System.out.println(email);
        }
        RequestDispatcher rd = request.getRequestDispatcher("/profile.jsp");        
        rd.forward(request, response);
        csession.close();
    }
}

