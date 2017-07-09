<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
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
    
        .movie{
                clear: both;
                height: 180px;
                position: relative;
        }
    img{
        max-height: 180px;
        max-width: 140px;
        height: auto;
        width: auto;
        margin: auto;
        position: absolute;
        top: 0;
        bottom: 0;
    }
    .info{
        margin-left: 0px;
        padding: 0px;
        font-size: 11px;
    }
    
    .title{
        font-size: 16px;
    }
    .submitLink {
                background-color: transparent;
                text-decoration: underline;
                border: none;
                color: black;
                cursor: pointer;
                padding: 0px;
        }
      


</style>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
  <TITLE>FabFlix</TITLE>
</HEAD>
<%@ page import="java.util.*"%>
<%
	String username = (String)session.getAttribute("username");
	Map<String,Integer> movieCart = (Map)session.getAttribute("movieCart");
        String movie_id = (String)request.getAttribute("addMovie");
	String movie_title = (String)request.getAttribute("movieTitle");
	String movieCount = (String)request.getAttribute("numMovies");
	if (movieCount != null) { 
		int intMovieCount = Integer.parseInt(movieCount); 
		String movieKey = movie_id + " = " + movie_title;
		if (intMovieCount == 0)
			movieCart.remove(movieKey);
		else
			movieCart.put(movieKey, intMovieCount);
		session.setAttribute("movieCart",movieCart);
	}
        if (username != null) {%>
                <p style="color:white">Welcome <%=username%></p>
        <% }
%>
<body style="background:black">
<%@ include file="top_bar.jsp" %>    
   <h2 style="color:white">Add Movie(s) to Cart:</h2><br>
    <center>
    <div>
        <div style="margin-left:0px; background: #000000; height: 50px; width: 850px;">
            <p style="margin-top: 12px; float: left; color:white">Movie Title</p>
            <p style="margin-top: 12px; margin-right:100px; float: right; color:white">Quantity</p>
            <p style="margin-top: 14px; margin-right:50px; float: right; position:relative; color:white">Price</p>
        </div>
    </center>
<%------------------------------------Enter Amount to Add Movie in Cart--------------------%>
        <center>
	<div style="margin-left:0px; background: #dddddb; height: 50px; width: 850px;">
	    <form action="/Fabflix/servlet/AddToCart" method="get">
	    <input type="hidden" value="<%=movie_id%>" name="movie_id">
	    <input type="hidden" value="<%=movie_title%>" name="movie_title">
            <p style="margin-left: 20px; margin-top: 12px; float: left;" class="title submitLink"><%=movie_title%></p>
            <input style="margin-top: 12px; margin-right:20px; float: right;"type="submit" value="Update">
            <input style="margin-top: 12px; margin-right:20px; float: right; width:45px;"type="number" min="0"max="999" name="numMovies">
	    </form>
            <p style="margin-top: 14px; margin-right:50px; float: right; position:relative">15.99</p>            
        </div>
        </center>


    <h2 style="color:white">Current Cart:</h2><br>
    <center>
    <div style="margin-left:0px; background: #000000; height: 50px; width: 850px;">
            <p style="margin-top: 12px; float: left; color:white">Movie Title</p>
            <p style="margin-top: 12px; margin-right:100px; float: right; color:white">Quantity</p>
            <p style="margin-top: 14px; margin-right:50px; float: right; position:relative; color:white">Price</p>
    </div>
    </center>
<%----------------------------------------List of Movies in Cart--------------------------%>
	<%
		Set<String> movieKeySet = movieCart.keySet();
		for (String m : movieKeySet) {
			String title = m.split(" = ")[1];
	%>
	<center>
		<div style="margin-left:0px; background: #dddddb; height: 50px; width: 850px; clear:both;">
        	<p style="margin-left: 20px; margin-top: 12px; float: left;" class="title submitLink"><%=title%></p>
        	<p style="margin-top: 14px; margin-right:120px; float: right; position:relative"><%=movieCart.get(m)%></p>
        	<p style="margin-top: 14px; margin-right:70px; float: right; position:relative">$<%=movieCart.get(m) * 15.99%></p>
	<%}%>
    </div>
    </center>
    <br>
    <br>
    <center>
	<form action="../shoppingCart.jsp">
        <input type="submit" value="Go to Cart" name="cart">
	</form>
    </center>  
</body>
    
</HTML>

