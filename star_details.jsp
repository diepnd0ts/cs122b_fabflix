<!DOCTYPE html>
<html>
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
        a:hover{
          color:red;
        }
        a {
          color:white;
          text-decoration: underline;
        }
        #inner_div{
          margin-left: 80px;
        }
        #results_div{
          margin-left: 20px;
        }
        label{
                color: white;
        }
      
        submitLink:focus {
                outline: none;
        }
        .submitLink {
                background-color: transparent;
                text-decoration: underline;
                border: none;
                color: white;
                cursor: pointer;
                padding: 0px;
        }
      
    .submitLink:hover{
        color: red;
    }
        h1{
                color: white;
        }
        h3{
                padding-left: 20px;
                color: #c40000;
        }
        div{
          outline: 1px;
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
  </style>
</HEAD>
<%
	String username = (String)session.getAttribute("username");
        if (username != null) {%>
                <p style="color: white">Welcome <%=username%></p>
        <% } else {
                request.getRequestDispatcher("../index.jsp").include(request,response);
        }
%>
<BODY style="background: black">
<%@ include file="top_bar.jsp" %>

<div class="container">

<header>
   <h1 style="margin-left: 20px">Star Details</h1>
</header>
<%@ page import="java.util.ArrayList"%>
<%
	ArrayList<String> starDetails = new ArrayList<String>();
	starDetails = (ArrayList)request.getAttribute("getInfo");
	String fullName = starDetails.get(1) + " " + starDetails.get(2);
%>  
<div style="margin: 20px">

    <img src="<%=starDetails.get(4)%>" width ="150" height="200"; style="float: left">

    <div id="inner_div" style="float: left;">
        <p style="color: white">Name: </p>
        <p style="color: white">Date of Birth: </p> 
        <p style="color: white">Star ID: </p>
        <p style="color: white">Starred In: </p>
    </div>

    <div id="results_div" style="float:left">
        <p style="color: white"><%=fullName%></p>
        <p style="color: white"><%=starDetails.get(3)%></p> 
        <p style="color: white"><%=starDetails.get(0)%></p>
	
        	<%String[] movies = starDetails.get(5).split(" , ");
		  String[] movieID = starDetails.get(6).split(" , ");
                  for (int j=0; j<movies.length; j++){%>
			<form style="display: inline;" action="/Fabflix/servlet/MovieStarDetails" method="get">
			<input type="hidden" value="<%=movieID[j]%>" name="movie_id">
                  	<input type="submit" class="info submitLink" value="<%=movies[j]%>" name="movie">
                  	</form>
			<%if (j < movies.length-1){%>

                       		<p style="color:white; display:inline">, </p>
                        <%}%>
				<br>
		<%}%>


    </div>

</div>



</BODY>
</html>

