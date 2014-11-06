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
import java.io.PrintWriter;
import static java.lang.System.out;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uk.ac.dundee.computing.aec.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.aec.instagrim.lib.Convertors;
import uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn;
import uk.ac.dundee.computing.aec.instagrim.stores.UserProfile;
import uk.ac.dundee.computing.aec.instagrim.models.User;

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
    UserProfile p;
    
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
                                session.setAttribute("login", value);
                                //session.setAttribute("username", p.getFirstName());
                                //System.out.println(p.getFirstName());
                                SetProfile(request,response);
                            }else{
                                value="fail: could not log in";
                                session.setAttribute("login", value);
                            }
                        }else{
                            value="fail: lg=null";
                            session.setAttribute("login", value);
                        }        
        ///Original down from here
        //setUser(p,request,response);
        //System.out.println("test");
        //RequestDispatcher rd = request.getRequestDispatcher("/profile.jsp");
        //String value = "othervariable";
        //HttpSession session=request.getSession();
        //session.setAttribute("login", value);
        //session.setAttribute("first_name", first_name);
        rd.forward(request, response);
    }
    
    private void testMethod(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        request.setAttribute("test","test1");
        RequestDispatcher rd = request.getRequestDispatcher("/profile.jsp");        
        rd.forward(request, response);
    }
        
//    

    private void SetProfile(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        HttpSession session = request.getSession();
        //String first_name=HttpSession.getAttribute(login);
        //UserProfile p = new UserProfile();
        Session csession = cluster.connect("instagrim");
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
//                session.setAttribute("first_name", first_name);
//                System.out.println(first_name);                
//                session.setAttribute("last_name", last_name);
//                System.out.println(last_name);
                //username = UserName;
            }
                session.setAttribute("first_name", first_name);
                System.out.println(first_name);  
                session.setAttribute("last_name", last_name);
                System.out.println(last_name);
        }
        //p.setUser(UserName, first_name, last_name);
        //return first_name;
        RequestDispatcher rd = request.getRequestDispatcher("/profile.jsp");        
        rd.forward(request, response);
        csession.close();        
        //jnjreturn p;
    }

    
                
        
        
    }












//private void setUser(String username,HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
//        //HttpSession session = request.getSession();
//        //String first_name=HttpSession.getAttribute(login);
//        LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
//            if (lg != null) {
//                UserName = lg.getUsername();
//                if (lg.getlogedin()) {
//                   Session session = cluster.connect("instagrim");
//                    ResultSet rs = null;        
//                    PreparedStatement ps = session.prepare("select login, first_name, last_name from userprofiles where login ="+username); //just a test, change user
//                    BoundStatement boundStatement = new BoundStatement(ps);
//                    rs = session.execute( boundStatement.bind(UserName) );
//                    if (rs.isExhausted()){
//                        System.out.println("No First Name returned");
//                        
//                        for (Row row : rs){
//                            first_name = row.getString("first_name");
//                            last_name = row.getString("last_name");
//                            //username = UserName;
//                        }
//                        session.close();
//                        //UserProfile p = new UserProfile();
//                        p.setUser(UserName, first_name, last_name);
//                        //return "No First Name found";
//                    }else{}                        
//                }
//                
//            }else{}
//                
//        //return first_name;
//        RequestDispatcher rd = request.getRequestDispatcher("/profile.jsp");        
//        rd.forward(request, response);
//        
//    }}
    
//    private void displayProfile(UserProfile p, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        //PicModel tm = new PicModel();
//        //tm.setCluster(cluster);
//        //java.util.LinkedList<Pic> lsPics = tm.getPicsForUser(User);
//        String FirstName = p.getFirstName();
//        String LastName = p.getLastName();
//        RequestDispatcher rd = request.getRequestDispatcher("/profile.jsp");
//        request.setAttribute("FirstName", FirstName);
//        //request.getSession().setAttribute("LastName", LastName);
//        request.setAttribute("test", "test");
//        rd.forward(request, response);
//
//    }
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
////            LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
////            if (lg != null) {
////                UserName = lg.getUsername();
////                if (lg.getlogedin()) {
//                    UserProfile p = new UserProfile();
//                    setUser(p,request,response);
//                    displayProfile(p,request,response);
//                //}
//                //else{
//                    //return;
//                    try (PrintWriter out = response.getWriter()) {            
//                        out.println("<!DOCTYPE html>");
//                        out.println("<html>");
//                        out.println("<head>");
//                        out.println("<title>Something went wrong</title>");            
//                        out.println("</head>");
//                        out.println("<body>");
//                        out.println("<h1>Servlet Profile at " + request.getContextPath() + "</h1>");
//                        out.println("</body>");
//                        out.println("</html>");
//                    }
//                //}
//            //}
//        //response.setContentType("text/html;charset=UTF-8");
//        
//    }
    
//    private void getLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        //HttpSession session = request.getSession();        
//        //session.setAttribute("login","test");
//        //String login = null;
//        //request.setAttribute("Pics", login);
//        //request.getSession().setAttribute("login", "test");
//        //tm.setCluster(cluster);
//        //java.util.LinkedList<Pic> lsPics = tm.getPicsForUser(User);
//        //RequestDispatcher rd = request.getRequestDispatcher("/profile.jsp");        
//        //rd.forward(request, response);
//
//    }
    
    
    /*
    
//request.getRequestDispatcher("/WEB-INF/hello.jsp").forward(request, response);
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    //@Override
    //protected void doPost(HttpServletRequest request, HttpServletResponse response)
            //throws ServletException, IOException {
        //processRequest(request, response);
    //}

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    //@Override
    //public String getServletInfo() {
        //return "Short description";
    //}// </editor-fold>

//}
//processRequest(request, response);
        //response.setContentType("text/html");
        //HttpSession session = request.getSession();
        //String login= (String) session.getAttribute("login");
        //String login= "test";
        //getLogin(request,response);
        //System.out.println(first_name);
        //if (login==null){login="fail";}
        //request.setAttribute("login", login);        
        //request.getRequestDispatcher("/profile.jsp").forward(request, response);
        //getServletContext().getRequestDispatcher("/profile.jsp").forward(request, response);
        //response.sendRedirect("profile.jsp?login=login");
        //RequestDispatcher rd = request.getRequestDispatcher("/profile.jsp");
        //request.getSession().setAttribute("login", login);
        //rd.forward(request, response);
    