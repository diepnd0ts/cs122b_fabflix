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
  </style>
</HEAD><%
	String username = (String)session.getAttribute("username");
	if (username != null) { %>
		<p style="color: white">Welcome <%=username%></p>
	<%} 
%>
<BODY style="background: black">
<%@ include file="top_bar.jsp" %>
<div class="container">

<header>
   <h1 style="margin-left: 20px">Movie Details</h1>
</header>
  
<div style="margin: 20px">

<%@ page import="java.util.ArrayList"%>
<%
	ArrayList<String> detailResults = new ArrayList<String>();
	detailResults = (ArrayList)request.getAttribute("getInfo");
%>
		<img src="<%=detailResults.get(4)%>" width ="150" height="200"; style="float: left">

		<div id="inner_div"; style="float: left;">
			  <p style="color: white">Title: </p>
			  <p style="color: white">Year: </p> 
			  <p style="color: white">Director: </p>
			  <p style="color: white">Movie ID: </p>
			  <p style="color: white">Stars: </p>
			  <p style="color: white">Genre: </p>
			  <p style="color: white">Trailer: </p>
			  <p style="color: white">Price: </p>
		</div>

		<div id="results_div"; style="float: left;">
			  <p style="color: white"><%=detailResults.get(1)%></p>
			  <p style="color: white"><%=detailResults.get(2)%></p> 
			  <p style="color: white"><%=detailResults.get(3)%></p>
			  <p style="color: white"><%=detailResults.get(0)%></p>
			  
			  <%String[] stars = detailResults.get(7).split(", ");
			    String[] starIDs = detailResults.get(8).split(", ");
			    for (int j=0; j<stars.length; j++){%>
			  	<form style="display: inline;"action="/Fabflix/servlet/MovieStarDetails" method="get">
				<input type="hidden" value="<%=starIDs[j]%>" name="star_id">
				<input type="submit" class="info submitLink" value="<%=stars[j]%>" name="star">
	         			<%if (j < stars.length-1){%>
                			<p style="color:white; display: inline">,</p>
                			<%}%>
        		  	</form>
			  <%}%>
			  <p></p>
			  <%if (detailResults.get(6).equals("null")){%>
				<p>&nbsp</p>
			  <%}
			   else{%>
			  	<p style="color: white"><%=detailResults.get(6)%></p>
			  <%}%>
			  <p style="color: white"><a href="<%=detailResults.get(5)%>">Click here</a> to watch Trailer</p>
			  <p style="color: white">$15.99</p>
			<p>
		</div>

</div>
<div style="clear: both;">
	<form action="/Fabflix/servlet/AddToCart" method="get">	
	<input type="hidden" value="<%=detailResults.get(0)%>" name="movie_id">
	<input type="hidden" value="<%=detailResults.get(1)%>" name="movie_title">
	<input type="submit" style="margin-left: 50px;" value="Add to Cart">
	</form>
</div>



</BODY>
</html>

