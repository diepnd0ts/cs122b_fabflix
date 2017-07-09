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
        p{
                color: white;
        }
        .movie{
                clear: both;
                height: 190px;
                outline: 1px solid grey;
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
                color: white;
                cursor: pointer;
                padding: 0px;
        }
      
    .submitLink:hover{
        color: red;
    }

.dropdown {
    position: relative;
    display: inline-block;
}

.dropdown-content {
    display: none;
    position: absolute;
    background-color: #c40000;
    overflow: auto;
    min-width: 400px;
    box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
    z-index: 1;
}

.dropdown-content p {
    color: black;
    text-decoration: none;
    display: block;
}

.dropdown:hover .dropdown-content {
    display: block;
}

</style>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
  <TITLE>FabFlix</TITLE>
</HEAD>

<%@ page import="java.util.ArrayList"%>
<body style="background:black">
<%--****************************HOME & CART******************************--%>   
<%@ include file="top_bar.jsp" %>
<%
//if (session != null) {
	String username = (String)session.getAttribute("username"); 
	if (username != null) {%>
		<p style="color: white">Welcome <%=username%></p>
<% 	}
	ArrayList<ArrayList<String>> results = new ArrayList<ArrayList<String>>();
        results = (ArrayList) request.getAttribute("movieResult");
	String query = (String)request.getAttribute("query");
	String pageIndex = (String)request.getAttribute("pageIndex");
	int showPage = Integer.parseInt(pageIndex) + 1;
	String pageNum = Integer.toString(showPage);

	String limitValue = (String)request.getAttribute("limitValue");
//}
//else {
//	request.getRequestDispatcher("../index.jsp").include(request, response);
//}
%>
<%--************************SORT BY*****************************************--%>

<form action="/Fabflix/servlet/SortBy" method="get">
    <div style="background: #dddddb; width: 100%; height: 25px; clear:both;">
        <p style="color:black; float:left">Sort By:
	    <input type="hidden" value="<%=query%>" name="query">
	    <input type="hidden" value="<%=pageIndex%>" name="pageIndex">
	    <input type="hidden" value="<%=limitValue%>" name="limitValue">
	    <input type="submit" value="Title (A-Z)" name="titleSort">
            <input type="submit" value="Title (Z-A)" name="reverseTitleSort">
            <input type="submit" value="Year (Ascending)" name="yearSort">
            <input type="submit" value="Year (Descending)" name="reverseYearSort">
	 </p>
</form>
<%--******************************PAGINATION*********************--%>
	<form action ="/Fabflix/servlet/Pagination" method="get">
        <p style="color:black; float:right">Items Per Page:
		<input type="hidden" value="<%=query%>" name="query">
		<input type="hidden" value="0" name="pageIndex">
             <input type="submit" value="10" name="limitValue">
            <input type="submit" value="25" name="limitValue">
            <input type="submit" value="50" name="limitValue">
            <input type="submit" value="100" name="limitValue">

 	</p>
	</div>
	</form>

	<form action ="/Fabflix/servlet/Pagination" method="get">
	<div>
	<p align="center" style="color:white">
		<input type="hidden" value="<%=query%>" name="query">
		<input type="hidden" value="<%=pageIndex%>" name="pageIndex">
		Page: <%=pageNum%>
		<input type="hidden" value="<%=limitValue%>" name="limitValue">
		<input type="submit" class="info submitLink" value="Prev" name="NextPrev">
		<input type="submit" class="info submitLink" value="Next" name="NextPrev">
 </p>
	</div>
	</form>
<%--*******************************GENERATING MOVIE RESULTS**********************--%>
<script language="javascript" type="text/javascript">
function mouseOver(id, title, title_details) {
	var ajaxRequest;
        try {
                ajaxRequest = new XMLHttpRequest();
        } catch(e) {
                try {
                        ajaxRequest = new ActiveXObject("Msxml2.XMLHTTP");
                } catch (e) {
                        try {
                                ajaxRequest = new ActiveXObject("Microsoft.XMLHTTP");
                        } catch(e) {
                                alert("Your browser broke!");
                                return false;
                        }
                }
        }
	ajaxRequest.onreadystatechange = function() {
		if (ajaxRequest.readyState == 4){
				document.getElementById(title_details).innerHTML = ajaxRequest.responseText;
		}
	}
	var moviePopup = "?movie_id=" + id;
	ajaxRequest.open("GET", "/Fabflix/servlet/AutopopupMovie" + moviePopup, true);
	ajaxRequest.send(null);
}

</script>

	<%
	for (int i = 0; i < results.size(); i++) { %>
    <div class="movie">
        <img style="float:left" src="<%=results.get(i).get(4)%>"/>
        <br>
        <div style="margin-left:200px; float:left;">
	    <form action="/Fabflix/servlet/MovieStarDetails" method="get">
		<input type="hidden" value="<%=results.get(i).get(0)%>" name="movie_id">

		<%--CREATE DROPDOWN HERE--%>
		<%--@ include file="movie_result_dropdown.jsp"--%>
		<%String id = results.get(i).get(0);
		  String title = results.get(i).get(1); 
		  String title_details = results.get(i).get(1) + " _details";
		%>

		<div class="dropdown">
            		<input type="submit"onmouseover="mouseOver('<%=id%>', '<%=title%>', '<%=title_details%>')" class="title submitLink" value="<%=title%>" name="movie">
		</form>
		<form action="/Fabflix/servlet/AddToCart" method="get">
			<input type="hidden" value="<%=username%>" name="username">
        		<input type="hidden" value="<%=results.get(i).get(0)%>" name="movie_id">
        		<input type="hidden" value="<%=results.get(i).get(1)%>" name="movie_title">
			<div id="<%=title%>" class="dropdown-content"> 
				<p id="<%=title_details%>"></p>
			</div>
		</form>
		</div>
	<%----------------------------------------------------------------%>
            <p class=info>ID Number: <%=results.get(i).get(0)%> </p>
            <p class=info>Year: <%=results.get(i).get(2)%> </p>
            <p class=info>Director: <%=results.get(i).get(3)%> </p>
            <p class=info>Genres: <%=results.get(i).get(6)%></p>
	    <p style="display: inline" class=info>Stars:</p> 
	<%String[] stars = results.get(i).get(7).split(", ");
	  String[] starIDs = results.get(i).get(8).split(", ");%>
	<%for (int j=0; j<stars.length; j++){%>
	   		<form style="display: inline;" action="/Fabflix/servlet/MovieStarDetails" method="get">
			<input type="hidden" value="<%=starIDs[j]%>" name="star_id">
            		<input type="submit" class="info submitLink" value="<%=stars[j]%>" name="star">
		<%if (j < stars.length-1){%>
		<p style="color:white; display: inline">,</p>
		<%}%>
		</form>
	<%}%>
        </div>
	<form action="/Fabflix/servlet/AddToCart" method="get">
	<input type="hidden" value="<%=username%>" name="username">
	<input type="hidden" value="<%=results.get(i).get(0)%>" name="movie_id">
	<input type="hidden" value="<%=results.get(i).get(1)%>" name="movie_title">
        <input style="float: right; margin-right: 20px;" type="submit" value="Add to Cart" name="addToCart">
	</form>
    </div>

<%}%>
</body>
    
</HTML>
