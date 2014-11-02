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
                        if (lg.getlogedin()) {
                            //Profile p = new Profile;%>
                <li><a href="/Instagrim/Images/<%=lg.getUsername()%>">Your Images</a></li>
                <%}else{}%>
                <li class="nav"><a href="/Instagrim/Images/majed">Sample Images</a></li>
            </ul>
        </nav>
        <article>
            <p>User: ${test} </br>Value:&nbsp;
                <%=UserName%>&nbsp; 
                <%
                    Profile p = new Profile();
                    //String FirstName = (String)session.getAttribute("FirstName");
                    String test = (String)session.getAttribute("login");
                    System.out.println(test+"////////////////////////////////////////////////////");%>
                    Name:<%=test%> </br> 
                    Test:<%=test%> </br> <%
                    //FirstName = request.getAttributes("FirstName");
                    //Profile p = new Profile()//=p.getSUUID();
                    //HttpSession session = request.getSession();
                    //out.write((String) session.getAttribute("login"));                
                    //out.write(request.getAttribute("login").toString());
                    //String login=("string");
                    //out.write("lolol");
                    //String user = request.getRemoteUser();
                    //out.print( request.getRemoteUser());}                    
                    //String login=(String)request.getAttribute("login");
                    String error=("No result,");
                    String login=(String)request.getAttribute("login");
                    %>Result: <%
                    if(login!=null){%>
                    <%=login%><%                
                    }else{%>
                    <%=error+" you should sit in the corner and cry now "%>
                    <%}}%>
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