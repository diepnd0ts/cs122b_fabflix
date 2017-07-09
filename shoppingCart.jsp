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
      
    .submitLink:hover{
        color: red;
    }


</style>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
  <TITLE>FabFlix</TITLE>
</HEAD>
<%@ page import="java.util.*"%>
<%
	String username = (String)session.getAttribute("username");
	Map<String, Integer> movieCart = (Map)session.getAttribute("movieCart");
	if (username != null) {%>
		<p style="color:white">Welcome <%=username%></p>
	<%} 

	//Checking if shoppingCart redirected itself to itself
	String shoppingCartButton = request.getParameter("movieCount");
	if (shoppingCartButton != null) {
		String movieKey = request.getParameter("key");
		int movieCount = Integer.parseInt(shoppingCartButton);
		if (movieCount == 0)
			movieCart.remove(movieKey);
		else
			movieCart.put(movieKey, movieCount);
	}
	else {
		shoppingCartButton = request.getParameter("emptyCart");
		if (shoppingCartButton != null) {
			movieCart = new HashMap<String, Integer>();
		}
	}
	session.setAttribute("movieCart",movieCart);
%>
<body style="background:black">
<%@ include file="top_bar.jsp" %>    
        
    </div>
        <h2 style="color:white">Shopping Cart:</h2><br>
        <center>
	<div style="margin-left:0px; background: #000000; height: 50px; width: 850px;">
            <p style="margin-top: 12px; float: left; color:white">Movie Title</p>
            <p style="margin-top: 12px; margin-right:180px; float: right; color:white">Quantity</p>
            <p style="margin-top: 14px; margin-right:32px; float: right; position:relative; color:white">Price</p>
        </div>
	</center>
<%---------------------------------------------Showing List of Movies-----------------------------------------------%>
	<%
		Set<String> movieKeySet = movieCart.keySet();

		//String noCheckout = request.getParameter("noCheckout");
                //if (movieCart.size() == 0)
                //        noCheckout = "emptycart";
		int num =0;
		for (String m : movieKeySet) {
                        String title = m.split(" = ")[1];
			num += movieCart.get(m);
	%>
	<center>
        <div style="clear:both; margin-left:0px; background: #dddddb; height: 50px; width: 850px;">
            <input style="margin-left: 20px; margin-top: 12px; float: left;"type="submit" class="title submitLink" value="<%=title%>">
	    <form action="/Fabflix/servlet/AddToCart" method="get">
	    	<input type="hidden" value="<%=m%>" name="key">
            	<input style="margin-top: 12px; margin-right:20px; float: right;"type="submit" value="Update">
	    	<input style="margin-top: 12px; margin-right:20px; float: right; width:45px;"type="number" min="0"max="999" name="movieCount">
	    </form>
            <p style="margin-top: 14px; margin-right:50px; float: right; position:relative"><%=movieCart.get(m)%></p>
            <p style="margin-top: 14px; margin-right:50px; float: right; position:relative">$<%=movieCart.get(m) * 15.99%></p>

       
        <%}%>    
            
        </div>
        </center>
        <br>
   	<center> 
        <p style="clear:both; color:white;"> Total: $<%=num*15.99%></p>
	</center>
    <br>
    <br>
    <center>
	<form action="/Fabflix/servlet/AddToCart">
        	<input type="submit" value="Empty Cart" name="emptyCart">
	</form>
	<form action="/Fabflix/servlet/Checkout" method="get">
		<input type="hidden" value="<%=movieCart.size()%>" name="cartSize">
        	<input type="submit" value="Checkout" name="checkout">
	</form>
    </center>  
</body>
    
</HTML>

