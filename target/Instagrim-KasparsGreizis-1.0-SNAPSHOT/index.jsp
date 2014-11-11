<%-- 
    Document   : index
    Created on : Sep 28, 2014, 7:01:44 PM
    Author     : Kaspars Greizis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Instagrim</title>
        <link rel="stylesheet" type="text/css" href="Styles.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <header>
            <h1><a href="/Instagrim">InstaGrim</a> </h1>
            <h2>Your world in Black and White</h2>
        </header>
        <nav>
            <ul class="nav">
                <li><a href="/Instagrim">Home</a></li>
                <li><a href="upload.jsp">Upload</a></li>
                    <%                        
                        LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
                        if (lg != null) {
                            String UserName = lg.getUsername();
                            if (lg.getlogedin()) {
                    %>

                <li><a href="/Instagrim/Images/<%=lg.getUsername()%>">Your Images</a></li>
                <li><a href="profile.jsp">Your Profile</a></li>
                <li><a href="/Instagrim/Logout">Log out</a></li>
                <%}}else{%>
                <li><a href="login.jsp">Login</a></li>
                <li><a href="register.jsp">Register</a></li>                
                <%}%>
            </ul>
        </nav>
                
        <footer>
            <ul>
                <li class="footer"><a href="/Instagrim">Home</a></li>
                <% 
                        /*if (lg != null) {
                            if (lg.getlogedin()) {
                              //session.invalidate();
                              }else{}*/%>
                <!--<a href="/Instagrim">Log out</a>-->
                                
                <li>&COPY; Andy C</li>
            </ul>
        </footer>
    </body>
</html>
