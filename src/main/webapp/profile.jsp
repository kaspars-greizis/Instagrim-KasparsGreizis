<%-- 
    Document   : profile
    Created on : 24-Oct-2014, 21:32:57
    Author     : TheFractal
--%>

<%@page import="uk.ac.dundee.computing.aec.instagrim.servlets.Profile"%>
<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Instagrim</title>
        <link rel="stylesheet" type="text/css" href="Styles.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <header>
            <h1><a href="/Instagrim">Insta Grim</a> </h1>
            <h2>Your world in Black and White</h2>
        </header>
        <nav>
            <ul class="nav">
                <li><a href="/Instagrim">Home</a></li>
                <li class="nav"><a href="/Instagrim/upload.jsp">Upload</a></li>
                <%
                    LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
                    if (lg != null) {
                        String UserName = lg.getUsername();
                        if (lg.getlogedin()) {%>
                <li><a href="/Instagrim/Images/<%=lg.getUsername()%>">Your Images</a></li>
                <%}else{}%>
                <li class="nav"><a href="/Instagrim/Images/majed">Sample Images</a></li>
                <li class="nav"><a href="/Instagrim/Logout">Log out</a></li>
            </ul>
        </nav>
        <article>
            <h1>Your Profile</h1>
            <p>Username:&nbsp;
                <%=UserName%>&nbsp;</br> 
                <%
                    String first_name = (String)session.getAttribute("first_name");
                    String last_name=(String)session.getAttribute("last_name");
                    String email=(String)session.getAttribute("email");%>
                    
                    First Name: <%=first_name%> </br> 
                    Last Name: <%=last_name%> </br> 
                    Email: <%=email%><%
                    //String login=(String)request.getAttribute("login");
                    //String error=("No result,");
                    String login=(String)session.getAttribute("login");%>
                    
                    <%}%>
            </p>

        </article>
        <footer>
            <ul>
                <li class="footer"><a href="/Instagrim">Home</a></li>
                <li>&COPY; Andy C</li>
            </ul>
        </footer>
    </body>
</html>