<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
  <TITLE>FabFlix</TITLE>
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
	%> <p style="color: white">Welcome <%=username%></p> <%
}

else {
	request.getRequestDispatcher("../index.jsp").include(request, response);
}
%>

<BODY style="background: black">
<div id="header">
        <h1 style="padding-top: 20px; color: white; margin-left:20px">FabFlix</h1>
	 <p style="float: right">
	<form action = "../index.jsp">
	    <input type="submit" class="navbutton" value="Logout" name="logout">
	</form>
	</p>
</div>
<H1 ALIGN="CENTER">Welcome to FabFlix Dashboard!</H1>
<center>

<%
	String error = (String)request.getAttribute("message");
	if (error != null) {
		%><p style="color: red"><%=error%></p><%
	}
%>

<h3 style="color:white" align="left" >Insert a Star</h3>
<center>
<div style="background: grey; height: 300px; width: 300px">
<form action="/Fabflix/servlet/Dashboard" method="get">
	<p style="color: black">First Name*:</p><input type="text" name="starFirstName">
	<p style="color: black">Last Name*:</p><input type="text" name="starLastName">
	<p style="color: black">Date of Birth (YYYY/MM/DD):</p><input type="text" name="starDOB">
	<p style="color: black">Star URL:</p><input type="text" name="starURL">
	<br>
	<br>
	<input type="submit" value="Insert New Star" name="addStar">
</form>
</div>
</center>
<h3 style="color:white" align="left" >Add Movie/Movie Details</h3>
<center>
<div style="background: grey; height: 500px; width: 300px">
<form action="/Fabflix/servlet/Dashboard" method="get">
        <p style="color: black">Title*:</p><input type="text" name="movieTitle">
        <p style="color: black">Year*:</p><input type="text" name="movieYear">
        <p style="color: black">Director*:</p><input type="text" name="movieDirector">
        <p style="color: black">Banner URL:</p><input type="text" name="movieBanner">
    	<p style="color: black">Trailer URL:</p><input type="text" name="movieTrailer">
        <p style="color: black">Star First Name*:</p><input type="text" name="starFirstName">
        <p style="color: black">Star Last Name*:</p><input type="text" name="starLastName">
        <p style="color: black">Genre*:</p><input type="text" name="genre">
        <br>
        <br>
        <input type="submit" value="Submit" name="addMovie">
</form>
</div>
</center>
<h3 style="color:white" align="left">Display Meta Data</h3>
<form action="/Fabflix/servlet/Dashboard" method="get">
	<input type="submit" value="Display" name="getMeta">
</form>

<%
        ArrayList<ArrayList<String>> metaData = (ArrayList)request.getAttribute("meta_data");
        if (metaData != null) {
		for (int i = 0; i < metaData.size(); i++) {%>
			<div style="outline: 1px solid white; height:300px; width: 180px; float: left"><%
			for (int j =0; j < metaData.get(i).size(); j++){%>
				<p style=" color: white"><%=metaData.get(i).get(j)%></p><%
			}%></div><%
		}
        }
%>

</BODY>
</HTML>
