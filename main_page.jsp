<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<style type="text/css">
	#header{
                height: 80px;
                width: 100%;
                background: #c40000;
        }

        #header h1{
                margin: 0px;
                float: left;
                text-shadow: 4px 4px black;
        }
        label{
                color: white;
        }
	 .navbutton{
                float: right;
                font-size: 24px;
                background-color: transparent;
                border: none;
                color: white;
                cursor: pointer;
                padding: 0px;
                margin: 22px;

        }
        .navbutton:hover{
               color:black;
        }

	.submitLink {
  		background-color: transparent;
  		text-decoration: underline;
  		border: none;
  		color: white;
  		cursor: pointer;
	}
	.submitLink:hover {
  		color: red;
	}
	h1{
		color: white;
	}
	h3{
		padding-left: 20px;
		color: #c40000;
	}
  </style>
</HEAD>

<%@ page import="javax.servlet.http.*,
		java.util.*"%>
<%
if (session != null) {
	
	String username = (String)session.getAttribute("username");
	String id = (String)session.getAttribute("id");
	//ArrayList<String> movieCart = (ArrayList)session.getAttribute("movieCart");
	Map<String, Integer> movieCart = (Map)session.getAttribute("movieCart");
	if (movieCart == null) {
		movieCart = new HashMap<String, Integer>();
	}
	session.setAttribute("movieCart",movieCart);
	//session.invalidate()
	
	%> <p style="color: white">Welcome <%=username%></p> <%
//	String logout = request.getParameter("logout");
//	if (logout != null)
//		session.invalidate();
	String insertCount = (String)request.getAttribute("insertCount");
	if (insertCount != null) { 
		%> <p style="color: white">You have purchased <%=insertCount%> movie(s)!</p> <%
		movieCart = new HashMap<String, Integer>();
        	session.setAttribute("movieCart", movieCart);
	}

}

else {
	request.getRequestDispatcher("../index.jsp").include(request, response);
}%>
<BODY style="background: black">

<%@ include file="top_bar.jsp" %>

<H1 ALIGN="CENTER">Welcome to FabFlix!</H1>
<center>

<%--****************QUICK SEARCH**********************--%>

<%@ include file="autocomplete_search.jsp" %>

<%--**************ADVANCED SEARCH**********************--%>
<FORM ACTION="/Fabflix/servlet/AdvancedSearch">
	<input type="submit" class="submitLink" value="Advanced Search" name="advancedSearch">
</FORM>

</center>
<H3>Browse By Movie Title</H3><br><br>
<%--*************************BROWSE BY MOVIE TITLE*********************--%>
<form action="/Fabflix/servlet/SearchResult"
        METHOD="get">
        <div class="container">
                <%
                        String[] titles = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
                        for (int i = 0; i < titles.length; i++) { %>
                                <input type="submit" class=submitLink value="<%=titles[i]%>" name="title">
                <% } %> 
        </div>
</form><br>
<H3> Browse By Movie Genre</H3><br><br>
<%--**********************BROWSE BY GENRE******************************--%>
<form action="/Fabflix/servlet/SearchResult"
        METHOD="get">
        <div class="container">
                <%
                        String[] genres = {"Action","Adventure","Animation","Comedy","Crime","Crime/Gangster","Documentary","Drama","Family","Fantasy","Foreign","Horror","Indie","James Bond","Musical","Mystery","Romance","Sci-Fi","Thriller","War"};
                        for (int i = 0; i < genres.length; i++) { %>
                                <input type="submit" class=submitLink value="<%=genres[i]%>" name="genre">
                <% } %>
        </div>
</form>

</BODY>
</HTML>
