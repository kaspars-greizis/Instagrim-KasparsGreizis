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
 *
 * @author TheFractal
 */
@WebServlet(name="Profile", urlPatterns = {"/profile"})
//@WebServlet
public class Profile extends HttpServlet {
    private Cluster cluster;
    private HttpSession session;
    private String UserName="";
    private String first_name="null";
    private String last_name="null";
    private UserProfile p;
    
    public Profile(){
        super();
    }
    public void init(ServletConfig config) throws ServletException {
        // TODO Auto-generated method stub
        cluster = CassandraHosts.getCluster();
        
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //UserProfile p = new UserProfile();
        /////New
        String value;
        System.out.println("test");
        RequestDispatcher rd = request.getRequestDispatcher("/profile.jsp");
        HttpSession session=request.getSession();
        LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
                        if (lg != null) {  
                            if (lg.getlogedin()) {
                                UserName = lg.getUsername();
                                value = "success";
                                //session.setAttribute("login", value);
                                //session.setAttribute("username", p.getFirstName());
                                System.out.println(value);
                                SetProfile(request,response);
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
        ///Original down from here
        
        //rd.forward(request, response);
    }
    

        
//    

    private void SetProfile(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        HttpSession session = request.getSession();
        //String first_name=HttpSession.getAttribute(login);
        Session csession = cluster.connect("instagrimKG");
        ResultSet rs = null;        
        PreparedStatement ps = csession.prepare("select login, first_name, last_name from userprofiles where login='"+UserName+"'"); //just a test, change user
        BoundStatement boundStatement = new BoundStatement(ps);
        rs = csession.execute( 
                boundStatement.bind() );
        if (rs.isExhausted()){
            System.out.println("No First Name returned");           
            
        }else{
        for (Row row : rs){
                first_name = row.getString("first_name");
                last_name = row.getString("last_name");
            }
                session.setAttribute("first_name", first_name);
                System.out.println(first_name);  
                session.setAttribute("last_name", last_name);
                System.out.println(last_name);
        }
        //p.setUser(UserName, first_name, last_name);
        RequestDispatcher rd = request.getRequestDispatcher("/profile.jsp");        
        rd.forward(request, response);
        csession.close();        
        //return p;
    }
}

