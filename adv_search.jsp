<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Search - Fabflix</title>
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
    
 
</style>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
</head>
<BODY style="background: black">

<%@ include file="top_bar.jsp" %>

<h1 style="color: white" align="center">Search for movies</H1><br>
<% String error = (String)request.getAttribute("error");
   String query = (String)request.getAttribute("query");
if (error != null) { %>
   <center>     
   <p style="color: red"><%=error%></p>
   </center>
<%} %>
<p><%=query%></p>
<center>
<form action="/Fabflix/servlet/AdvancedSearch" method="get">
<div style="background: #dddddb; width: 500px; height: 300px;"><div style="background: #dddddb; width: 500px; height: 300px;">
    <p>Title:</p> <input id = "autoText" type="text" name="title" maxlength="100"size="30"><br />
    <p>Year:</p> <input type="number"style="width: 220px" name="year" max="9999" min="0"><br />
    <p>Director:</p> <input type="text" name="director" size="30" maxlength="100"><br />
    <p>Star's First Name:</p> <input type="text" name="first_name"size="30" maxlength="50"><br />
    <p>Star's Last Name:</p> <input type="text" name="last_name" size="30"maxlength="50"><br />
</div>
<br>
    <input type="submit" value="Search">

</form>
</center>
</body>
</html>

