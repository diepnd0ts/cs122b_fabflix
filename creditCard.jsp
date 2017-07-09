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
                height: 350px;
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

<body style="background:black">
<%@page import="java.util.*,
	java.text.*"%>
<%	
	String query = (String)request.getAttribute("query");
	//String id = (String)session.getAttribute("id");
	if (query != null) {%>
		<p style="color:white"><%=query%></p> <%
	}
/*	Map<String, Integer> movieCart = (Map)session.getAttribute("movieCart");
	Set<String> movieKeySet = movieCart.keySet();

	Date dNow = new Date();
	SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd");

	ArrayList<String> sortQueries = new ArrayList<String>();
	for (String m : movieKeySet) {
		for (int i=0; i < movieCart.get(m); i++) {
			String sortQuery = "INSERT into sales (customer_id, movie_id, sales_date) values('" + id + "', '" + m.split(" = ")[0] + "', '" + ft.format(dNow) + "')";
			sortQueries.add(sortQuery);
		}	
	}*/
	String queryRequest = (String)request.getAttribute("queryRequest");
	
	if (queryRequest != null) {
		//request.setAttribute("sortQueries",sortQueries);
		request.setAttribute("query", query);
		request.getRequestDispatcher("/servlet/Checkout").forward(request,response);
	}
		
%>  
    <%@ include file="top_bar.jsp" %> 
    <div class="movie">
        <h2 style="color:white">Customer Credit Card Information:</h2><br>
<%
        String error = (String)request.getAttribute("error");
        if (error != null) {%>
                <p style="color:red"><%=error%></p>
<%      }
%>
        <form action="/Fabflix/servlet/Checkout" method="post">
        <div style="margin-left:100px; background: #dddddb; height: 275px; width: 850px;">
            <br>
            <p style="margin-left:20px">Credit Card Number: </p>
            <input style="margin-left:20px" type="text" size="50" maxlength="20" name="cc_id">

            <p style="margin-left:20px">Expiration Date (YYYY/MM/DD): </p>
             <input style="margin-left:20px" type="text" size="4" maxlength="4" name="exp_year">
            <input type="text" size="2" maxlength="2" name="exp_month">
            <input type="text" size="2" maxlength="2" name="exp_day">

            <p style="margin-left:20px">Cardholder's First Name: </p>
            <input style="margin-left:20px" type="text" size="30" maxlength="50" name="card_firstName">
            <p style="margin-left:20px">Cardholder's Last Name: </p>
            <input style="margin-left:20px" type="text" size="30" maxlength="50" name="card_lastName">

        </div>
    </div>
    <br>
    <br>
    <center>
        <input type="submit" value="Confirm" name="confirm">
    </center>
    </form>
</body>
    
</HTML>

